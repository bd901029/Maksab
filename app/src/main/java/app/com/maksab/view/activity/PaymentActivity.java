package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CouponCodeResponse;
import app.com.maksab.databinding.ActivityPaymentBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.CouponCodeModel;
import app.com.maksab.view.viewmodel.PaymentModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private ActivityPaymentBinding mBinding;
    PaymentModel paymentModel = new PaymentModel();

    public static String planId = "PLAN_ID";
    public static String planAmount = "PLAN_AMOUNT";
    public static String cardNumber = "CARD_NUMBER";
    public static String expiredMonth = "EXPIRED_MONTH";
    public static String expiredYear = "EXPIRED_YEAR";
    public static String cardHolder = "CARD_HOLDER";
    public static String cvvCode = "CVV";
    public static String couponCodeId = "COUPON_CODE_ID";
    private String sCurrency = "", sAmount = "", sPayTotalCurrency = "";
    private String sPlanId = "", sPayTotal = "", sCardNumber = "", sExpiredDate = "", sCardHolder = "", sCvvCode = "", sCouponCodeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        mBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sPlanId = bundle.getString(planId);//bundle.getString(planId);
            paymentCompleteModel.setPlanId(sPlanId);
            sPayTotal = bundle.getString(planAmount);
            mBinding.payTotal.setText(sPayTotal);
            sCurrency = sPayTotal.replaceAll("[^A-Za-z]+", "");
            sAmount = sPayTotal.replaceAll("[^0-9]", "");
            sTotalAmount = sAmount;
        } else {
            Utility.showToast(this, getString(R.string.wrong));
        }*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sPlanId = bundle.getString(planId);//bundle.getString(planId);
            sPayTotalCurrency = bundle.getString(planAmount);
            mBinding.payTotal.setText(sPayTotalCurrency);
            sCurrency = sPayTotalCurrency.replaceAll("[^A-Za-z]+", "");
            sAmount = sPayTotalCurrency.replaceAll("[^0-9]", "");
        } else {
            Utility.showToast(this, getString(R.string.wrong));
        }

        mBinding.setModel(paymentModel);

        mBinding.etCardNumber.addTextChangedListener(new TextWatcher() {
            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
    }


    public void applyCouponCode() {
        if (mBinding.etCoupon.getText().toString().isEmpty()) {
            mBinding.etCoupon.setError(getText(R.string.error_coupon_code));
        } else {
            ProgressDialog.getInstance().showProgressDialog(PaymentActivity.this);
            CouponCodeModel couponCodeModel = new CouponCodeModel();
            couponCodeModel.setLanguage(Utility.getLanguage(PaymentActivity.this));
            couponCodeModel.setUserId(Utility.getUserId(PaymentActivity.this));
            couponCodeModel.setCityId(Utility.getCity(PaymentActivity.this));
            couponCodeModel.setCouponCode(mBinding.etCoupon.getText().toString());
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
                        Utility.showToast(PaymentActivity.this, t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void handleCouponCodeResponse(CouponCodeResponse couponCodeResponse) {
        if (couponCodeResponse.getResponseCode().equals(Api.SUCCESS)) {

            if (couponCodeResponse.getPaystatus().equalsIgnoreCase("0")) {
                Utility.showToast(PaymentActivity.this, couponCodeResponse.getMessage());
                finish();
            } else {
                sCouponCodeId = couponCodeResponse.getCouponCodeId();
                // String sDiscount = couponCodeResponse.getDiscountedPrice().replaceAll("[^0-9]", "");
                // sPayTotal = (Double.parseDouble(sAmount) - Double.parseDouble(sDiscount)) + "";
                sPayTotal = couponCodeResponse.getDiscountedPrice();
                mBinding.payTotal.setText(sCurrency + " " + sPayTotal);
            }
            // activitySubmitCreditCardBinding.llCoupon.setVisibility(View.GONE);
        } else {
            Utility.showToast(PaymentActivity.this, couponCodeResponse.getMessage());
        }
    }

    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    private boolean validate(PaymentModel paymentModel) {
        if (isEmpty(paymentModel.getCardNumber()) || isEmpty(paymentModel.getCardName())
                || isEmpty(paymentModel.getExpiryMonth())  || isEmpty(paymentModel.getExpiryYear())
                || isEmpty(paymentModel.getCvv())) {
            if (paymentModel.getCardNumber().isEmpty()) {
                Utility.showToast(PaymentActivity.this, getString(R.string.hint_card_number));
            }
            if (paymentModel.getCardName().isEmpty()) {
                Utility.showToast(PaymentActivity.this, getString(R.string.card_holder));
            }
            if (paymentModel.getExpiryMonth().isEmpty()) {
                Utility.showToast(PaymentActivity.this, getString(R.string.expiry_month));
            }
            if (paymentModel.getExpiryYear().isEmpty()) {
                Utility.showToast(PaymentActivity.this, getString(R.string.expiry_year));
            }
            if (paymentModel.getCvv().isEmpty()) {
                Utility.showToast(PaymentActivity.this, getString(R.string.hint_cvv_code));
            }
            return false;
        } else if ( paymentModel.getExpiryYear().length() < 2 ) {
            Utility.showToast(PaymentActivity.this, getString(R.string.error_year));
            return false;
        } else {
            return true;
        }
    }

    public void onPayment(PaymentModel paymentModel){
        if (validate(paymentModel)) {
            if (sPayTotal.equalsIgnoreCase("")) {
                sPayTotal = sAmount;
            }
           /* String expiryYear1 = "";

            try {
                 expiryYear1 = (paymentModel.getExpiryYear().charAt(2)+"")+(paymentModel.getExpiryYear().charAt(3)+"");
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            Intent intent = new Intent(PaymentActivity.this, ActivityPaymentWebview.class);
            intent.putExtra(couponCodeId, sCouponCodeId);
            intent.putExtra(cardNumber, paymentModel.getCardNumber().replaceAll("\\s+", ""));
            intent.putExtra(expiredMonth, paymentModel.getExpiryMonth());
            intent.putExtra(expiredYear, paymentModel.getExpiryYear());
            intent.putExtra(cardHolder, paymentModel.getCardName());
            intent.putExtra(cvvCode, paymentModel.getCvv());
            intent.putExtra(planId, sPlanId);
            intent.putExtra(planAmount, sPayTotal);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}