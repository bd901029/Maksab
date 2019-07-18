package app.com.maksab.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.LoginResponse;
import app.com.maksab.api.dao.NotificationFilterResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityBecomePartnerBinding;
import app.com.maksab.databinding.DialogNotificationFilterBinding;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Extension;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.BusinessTypeAdapter;
import app.com.maksab.view.adapter.NotificationFilterAdapter;
import app.com.maksab.view.viewmodel.BecomePartnerModel;
import app.com.maksab.view.viewmodel.LoginModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import app.com.maksab.view.viewmodel.UserRegistrationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BecomePartnerActivity extends AppCompatActivity {
    private ActivityBecomePartnerBinding mBinding;
    private Dialog dialogNotificationFilter;
    private DialogNotificationFilterBinding recyclerViewBinding;
    ArrayList<String> selectNotification = new ArrayList<String>();
    ArrayList<NotificationFilterResponse.CategoryList> notificationFilterArrayList;
    BecomePartnerModel becomePartnerModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_become_partner);
        mBinding.setActivity(this);
        becomePartnerModel = new BecomePartnerModel();
        mBinding.setModel(becomePartnerModel);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /**
     * Check string empty or not
     * @param str string to check
     * @return true if empty else false
     */
    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }


    /**
     * Validate registration form
     * @param becomePartnerModel Registration model
     * @return true if valid else false
     */
    private boolean validate(BecomePartnerModel becomePartnerModel) {
        Extension extension = Extension.getInstance();
        if (isEmpty(becomePartnerModel.getCompanyName()) || isEmpty(becomePartnerModel.getEmail()) ||
                isEmpty(becomePartnerModel.getName()) || isEmpty(becomePartnerModel.getPhoneNumber())|| isEmpty(becomePartnerModel
                .getWebsite())|| isEmpty(becomePartnerModel.getAddress())|| isEmpty(becomePartnerModel.getComments())) {
            if (becomePartnerModel.getCompanyName().isEmpty()) {
                mBinding.companyNameInputLayout.setError(getText(R.string.company_name));
            }
            if (becomePartnerModel.getEmail().isEmpty()) {
                mBinding.emailInputLayout.setError(getText(R.string.error_email));
            }
            if (becomePartnerModel.getName().isEmpty()) {
                mBinding.nameInputLayout.setError(getText(R.string.error_f_name));
            }
            if (becomePartnerModel.getPhoneNumber().isEmpty()) {
                mBinding.mobileInputLayout.setError(getText(R.string.error_phone));
            }
            if (becomePartnerModel.getWebsite().isEmpty()) {
                mBinding.linkInputLayout.setError(getText(R.string.website));
            }
            if (becomePartnerModel.getAddress().isEmpty()) {
                mBinding.addrressInputLayout.setError(getText(R.string.hint_address));
            }
            if (becomePartnerModel.getComments().isEmpty()) {
                mBinding.commentsInputLayout.setError(getText(R.string.comments));
            }
            if (becomePartnerModel.getBusinessType().isEmpty()) {
                Utility.showToast(BecomePartnerActivity.this, getString(R.string.select_business));
            }
            if (becomePartnerModel.getCompanyName().isEmpty()) {
                Utility.showToast(BecomePartnerActivity.this, getString(R.string.select_country));
            }
            return false;
        }else if (!extension.executeStrategy(BecomePartnerActivity.this, becomePartnerModel.getEmail(),
                ValidationTemplate.EMAIL)) {
            Utility.showToast(BecomePartnerActivity.this, getString(R.string.invalid_email));
            return false;
        } else if (!extension.executeStrategy(BecomePartnerActivity.this, "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(BecomePartnerActivity.this);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Call on @SignUp button click
     * @param becomePartnerModel Registration model
     */
    public void onClickBecomePartner(final BecomePartnerModel becomePartnerModel) {
        if (validate(becomePartnerModel)) {
            ProgressDialog.getInstance().showProgressDialog(BecomePartnerActivity.this);
            becomePartnerModel.setLanguage(Utility.getLanguage(BecomePartnerActivity.this));
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> registrationCall = api.becomePartner(becomePartnerModel);
            registrationCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse> response) {
                    ProgressDialog.getInstance().dismissDialog();
                    handleResponse(response.body());
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    registrationCall.cancel();
                    Utility.showToast(BecomePartnerActivity.this, t.getMessage());
                }
            });
        }
    }

    /**
     * Handle registration response
     * @param successfulResponse Registration model
     */
    private void handleResponse(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
            Utility.showToast(BecomePartnerActivity.this, successfulResponse.getMessage());
            if (successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                startActivity(new Intent(BecomePartnerActivity.this, HomeActivity.class));
                finish();
            }
        }
    }

    public void onClickNotificationFilter() {
        dialogNotificationFilter = new Dialog(BecomePartnerActivity.this);
        recyclerViewBinding = DataBindingUtil.inflate(LayoutInflater.from(BecomePartnerActivity.this),
                R.layout.dialog_notification_filter, null, false);
        dialogNotificationFilter.setContentView(recyclerViewBinding.getRoot());
        dialogNotificationFilter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerViewBinding.selectAll.setVisibility(View.GONE);
        recyclerViewBinding.llHeader.setVisibility(View.GONE);
        selectNotification.clear();
        notificationFilterArrayList = null;
        try {
            NotificationFilterList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerViewBinding.txCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNotificationFilter.dismiss();
            }
        });
        dialogNotificationFilter.show();
    }

    private void NotificationFilterList() {
        ProgressDialog.getInstance().showProgressDialog(BecomePartnerActivity.this);
        UserIdModel userIdModel = new UserIdModel();
        userIdModel.setLanguage(Utility.getLanguage(BecomePartnerActivity.this));
        userIdModel.setUserId(Utility.getUserId(BecomePartnerActivity.this));
        Api api = APIClient.getClient().create(Api.class);
        final Call<NotificationFilterResponse> responseCall = api.getUserCategory(userIdModel);
        responseCall.enqueue(new Callback<NotificationFilterResponse>() {
            @Override
            public void onResponse(Call<NotificationFilterResponse> call, Response<NotificationFilterResponse>
                    response) {
                recyclerViewBinding.progressBar.setVisibility(View.GONE);
                ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()) {
                    notificationFilterResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<NotificationFilterResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()) {
                    Utility.showToast(BecomePartnerActivity.this, t+"");
                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void notificationFilterResponse(NotificationFilterResponse myResponse) {
        try {
            if (myResponse != null) {
                if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    notificationFilterArrayList = myResponse.getCategoryList();
                    if (notificationFilterArrayList != null && notificationFilterArrayList.size() != 0) {
                        setRecyclerViewNotificationFilter();
                    }
                } else {
                    Utility.showToast(BecomePartnerActivity.this, myResponse.getMessage());
                }
            }
        } catch (Exception e) {
            Utility.showToast(BecomePartnerActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }


    private void setRecyclerViewNotificationFilter() {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                NotificationFilterResponse.CategoryList notificationList = (NotificationFilterResponse.CategoryList) obj;
                mBinding.tvSelectBusiness.setText(notificationList.getCategoryName());
                becomePartnerModel.setBusinessType(notificationList.getCategoryId());
                dialogNotificationFilter.dismiss();
            }
        };
        recyclerViewBinding.recyclerView.setLayoutManager(new GridLayoutManager(BecomePartnerActivity.this, 1));
        recyclerViewBinding.recyclerView.setAdapter(new BusinessTypeAdapter(BecomePartnerActivity.this, notificationFilterArrayList,
                onItemClickListener));
    }


    public void onClickCountryList(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(BecomePartnerActivity.this);
       // builder.setTitle("System Type");
        final String[] sPaymentTypeList  = getResources().getStringArray(R.array.country_list);
        builder.setItems(sPaymentTypeList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                 String s =  sPaymentTypeList[which];
                mBinding.tvCountryName.setText(s+"");
                becomePartnerModel.setCompanyName(s);
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}