package app.com.maksab.submitcreditcardflow;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CouponCodeResponse;
import app.com.maksab.databinding.ActivitySubmitCreditCardBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.activity.ActivityPaymentWebview;
import app.com.maksab.view.viewmodel.CouponCodeModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitCreditCardActivity extends AppCompatActivity {

    private boolean showingGray = true;
    private AnimatorSet inSet;
    private AnimatorSet outSet;
    private ActivitySubmitCreditCardBinding activitySubmitCreditCardBinding;
    private Card card;
    public static String planId = "PLAN_ID";
    public static String planAmount = "PLAN_AMOUNT";
    public static String cardNumber = "CARD_NUMBER";
    public static String expiredDate = "EXPIRED_DATE";
    public static String cardHolder = "CARD_HOLDER";
    public static String cvvCode = "CVV";
    public static String couponCodeId = "COUPON_CODE_ID";
    private String sCurrency = "", sAmount = "", sPayTotalCurrency = "";
    private String sPlanId = "", sPayTotal = "", sCardNumber = "", sExpiredDate = "", sCardHolder = "", sCvvCode = "", sCouponCodeId = "";



    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySubmitCreditCardBinding = DataBindingUtil.setContentView(this, R.layout.activity_submit_credit_card);
        //activitySubmitCreditCardBinding.setActivity(this);
        card = new Card();
        setSupportActionBar(activitySubmitCreditCardBinding.toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sPlanId = bundle.getString(planId);//bundle.getString(planId);
            sPayTotalCurrency = bundle.getString(planAmount);
            activitySubmitCreditCardBinding.payTotal.setText(sPayTotalCurrency);
            sCurrency = sPayTotalCurrency.replaceAll("[^A-Za-z]+", "");
            sAmount = sPayTotalCurrency.replaceAll("[^0-9]", "");
        } else {
            Utility.showToast(this, getString(R.string.wrong));
        }

        View.OnClickListener onHelpClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubmitCreditCardActivity.this, "The CVV Number (\"Card Verification Value\") is a 3 or 4 digit number on your credit and debit cards", Toast.LENGTH_LONG).show();
            }
        };

        activitySubmitCreditCardBinding.iconHelpGray.setOnClickListener(onHelpClickListener);
        activitySubmitCreditCardBinding.iconHelpBlue.setOnClickListener(onHelpClickListener);
        activitySubmitCreditCardBinding.inputEditCardNumber.addTextChangedListener(new TextWatcher() {
            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    flipToBlue();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 16) {
                    return;
                }
                lock = true;
                for (int i = 4; i < s.length(); i += 5) {
                    if (s.toString().charAt(i) != ' ') {
                        s.insert(i, " ");
                    }
                }
                lock = false;
            }
        });

        activitySubmitCreditCardBinding.inputEditExpiredDate.addTextChangedListener(new TextWatcher() {
            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 4) {
                    return;
                }
                lock = true;
                if (s.length() > 2 && s.toString().charAt(2) != '/') {
                    s.insert(2, "/");
                }
                lock = false;
            }
        });


        activitySubmitCreditCardBinding.applyCouponCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyCouponCode();
            }
        });

        activitySubmitCreditCardBinding.labelSecureSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setCardNumber(activitySubmitCreditCardBinding.inputEditCardNumber.getText().toString());
                card.setExpiredDate(activitySubmitCreditCardBinding.inputEditExpiredDate.getText().toString());
                card.setCardHolder(activitySubmitCreditCardBinding.inputEditCardHolder.getText().toString());
                card.setCvvCode(activitySubmitCreditCardBinding.inputEditCvvCode.getText().toString());
                if (validate()) {

                    if (sPayTotal.equalsIgnoreCase("")) {
                        sPayTotal = sAmount;
                    }
                    Intent intent = new Intent(SubmitCreditCardActivity.this, ActivityPaymentWebview.class);
                    intent.putExtra(couponCodeId, sCouponCodeId);
                    intent.putExtra(cardNumber, card.getCardNumber().replaceAll("\\s+", ""));
                    intent.putExtra(expiredDate, card.getExpiredDate());
                    intent.putExtra(cardHolder, card.getCardHolder());
                    intent.putExtra(cvvCode, card.getCvvCode());
                    intent.putExtra(planId, sPlanId);
                    intent.putExtra(planAmount, sPayTotal);
                    startActivity(intent);
                }
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        PagerAdapter adapter = new MyPagerAdapter();
        activitySubmitCreditCardBinding.viewPager.setAdapter(adapter);
        activitySubmitCreditCardBinding.viewPager.setClipToPadding(false);
        activitySubmitCreditCardBinding.viewPager.setPadding(width / 4, 0, width / 4, 0);
        activitySubmitCreditCardBinding.viewPager.setPageMargin(width / 14);
        activitySubmitCreditCardBinding.viewPager.setPagingEnabled(false);
        activitySubmitCreditCardBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        updateProgressBar(25);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
                        return;
                    case 1:
                        updateProgressBar(50);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.requestFocus();
                        return;
                    case 2:
                        updateProgressBar(75);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.requestFocus();
                        return;
                    case 3:
                        updateProgressBar(100);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCvvCode.requestFocus();
                        return;
                    case 4:
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                    handled = true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //submit();
                    hideKeyboard(activitySubmitCreditCardBinding.inputEditCvvCode);

                    handled = true;
                }
                return handled;
            }
        };

        activitySubmitCreditCardBinding.inputEditCardNumber.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditExpiredDate.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCardHolder.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCvvCode.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.input_layout_card_number;
                    break;
                case 1:
                    resId = R.id.input_layout_expired_date;
                    break;
                case 2:
                    resId = R.id.input_layout_card_holder;
                    break;
                case 3:
                    resId = R.id.input_layout_cvv_code;
                    break;
                case 4:
                    resId = R.id.space;
                    break;

            }
            return findViewById(resId);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }


        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    private void updateProgressBar(int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(activitySubmitCreditCardBinding.progressHorizontal, "progress", progress);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    /*private void submit() {
        activitySubmitCreditCardBinding.viewPager.setCurrentItem(4);
        card.setCardNumber(activitySubmitCreditCardBinding.inputEditCardNumber.getText().toString());
        card.setExpiredDate(activitySubmitCreditCardBinding.inputEditExpiredDate.getText().toString());
        card.setCardHolder(activitySubmitCreditCardBinding.inputEditCardHolder.getText().toString());
        card.setCvvCode(activitySubmitCreditCardBinding.inputEditCvvCode.getText().toString());
        //Toast.makeText(SubmitCreditCardActivity.this, card.toString(), Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activitySubmitCreditCardBinding.inputLayoutCvvCode.setVisibility(View.INVISIBLE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
               // activitySubmitCreditCardBinding.labelSecureSubmission.setVisibility(View.VISIBLE);
                //activitySubmitCreditCardBinding.llCoupon.setVisibility(View.VISIBLE);
                hideKeyboard(activitySubmitCreditCardBinding.inputEditCvvCode);
              //  activitySubmitCreditCardBinding.progressCircle.setVisibility(View.VISIBLE);
            }
        }, 300);
    }*/

    private void reset() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        activitySubmitCreditCardBinding.inputLayoutCvvCode.setVisibility(View.VISIBLE);
        activitySubmitCreditCardBinding.progressCircle.setVisibility(View.GONE);
        //activitySubmitCreditCardBinding.labelSecureSubmission.setVisibility(View.GONE);
        flipToGray();
        activitySubmitCreditCardBinding.viewPager.setCurrentItem(0);
        activitySubmitCreditCardBinding.inputEditCardNumber.setText("");
        activitySubmitCreditCardBinding.inputEditExpiredDate.setText("");
        activitySubmitCreditCardBinding.inputEditCardHolder.setText("");
        activitySubmitCreditCardBinding.inputEditCvvCode.setText("");
        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
        showKeyboard(activitySubmitCreditCardBinding.inputEditCardNumber);
    }

    private void flipToGray() {
        if (!showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = true;
            activitySubmitCreditCardBinding.cardBlue.setCardElevation(0);
            activitySubmitCreditCardBinding.cardGray.setCardElevation(0);
            outSet.setTarget(activitySubmitCreditCardBinding.cardBlue);
            outSet.start();
            inSet.setTarget(activitySubmitCreditCardBinding.cardGray);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    activitySubmitCreditCardBinding.cardGray.setCardElevation(convertDpToPixel(12, SubmitCreditCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            inSet.start();
        }
    }

    private void flipToBlue() {
        if (showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = false;
            activitySubmitCreditCardBinding.cardGray.setCardElevation(0);
            activitySubmitCreditCardBinding.cardBlue.setCardElevation(0);
            outSet.setTarget(activitySubmitCreditCardBinding.cardGray);
            outSet.start();
            inSet.setTarget(activitySubmitCreditCardBinding.cardBlue);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    activitySubmitCreditCardBinding.cardBlue.setCardElevation(convertDpToPixel(12, SubmitCreditCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            inSet.start();
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                reset();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void applyCouponCode() {
        if (activitySubmitCreditCardBinding.etCoupon.getText().toString().isEmpty()) {
            activitySubmitCreditCardBinding.couponInputLayout.setError(getText(R.string.error_coupon_code));
        } else {
            ProgressDialog.getInstance().showProgressDialog(SubmitCreditCardActivity.this);
            CouponCodeModel couponCodeModel = new CouponCodeModel();
            couponCodeModel.setLanguage(Utility.getLanguage(SubmitCreditCardActivity.this));
            couponCodeModel.setUserId(Utility.getUserId(SubmitCreditCardActivity.this));
            couponCodeModel.setCityId(Utility.getCity(SubmitCreditCardActivity.this));
            couponCodeModel.setCouponCode(activitySubmitCreditCardBinding.etCoupon.getText().toString());
            couponCodeModel.setPlanId(sPlanId);
            couponCodeModel.setPlanAmount(sAmount);
            Api api = APIClient.getClient().create(Api.class);
            final Call<CouponCodeResponse> responseCall = api.checkCouponCode(couponCodeModel);
            responseCall.enqueue(new Callback<CouponCodeResponse>() {
                @Override
                public void onResponse(Call<CouponCodeResponse> call, Response<CouponCodeResponse> response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        handleCouponCodeResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<CouponCodeResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        Utility.showToast(SubmitCreditCardActivity.this, t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void handleCouponCodeResponse(CouponCodeResponse couponCodeResponse) {
        if (couponCodeResponse.getResponseCode().equals(Api.SUCCESS)) {

            if (couponCodeResponse.getPaystatus().equalsIgnoreCase("0")) {
                Utility.showToast(SubmitCreditCardActivity.this, couponCodeResponse.getMessage());
                finish();
            } else {
                sCouponCodeId = couponCodeResponse.getCouponCodeId();
                // String sDiscount = couponCodeResponse.getDiscountedPrice().replaceAll("[^0-9]", "");
                // sPayTotal = (Double.parseDouble(sAmount) - Double.parseDouble(sDiscount)) + "";
                sPayTotal = couponCodeResponse.getDiscountedPrice();
                activitySubmitCreditCardBinding.payTotal.setText(sCurrency + " " + sPayTotal);
            }
            // activitySubmitCreditCardBinding.llCoupon.setVisibility(View.GONE);
        } else {
            Utility.showToast(SubmitCreditCardActivity.this, couponCodeResponse.getMessage());
        }
    }

    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    private boolean validate() {
        if (isEmpty(card.getCardNumber()) || isEmpty(card.getCardHolder())
                || isEmpty(card.getExpiredDate()) || isEmpty(card.getCvvCode())) {
            if (card.getCardNumber().isEmpty()) {
                Utility.showToast(SubmitCreditCardActivity.this, getString(R.string.hint_card_number));
            }
            if (card.getCardHolder().isEmpty()) {
                Utility.showToast(SubmitCreditCardActivity.this, getString(R.string.hint_card_holder));
            }
            if (card.getExpiredDate().isEmpty()) {
                Utility.showToast(SubmitCreditCardActivity.this, getString(R.string.hint_expired_date));
            }
            if (card.getCvvCode().isEmpty()) {
                Utility.showToast(SubmitCreditCardActivity.this, getString(R.string.hint_cvv_code));
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
