package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.MobileResponse;
import app.com.maksab.api.dao.OTPRegisterResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityMobileVerificationBinding;
import app.com.maksab.util.*;
import app.com.maksab.view.viewmodel.EmailModel;
import app.com.maksab.view.viewmodel.MobileModel;
import app.com.maksab.view.viewmodel.ResendOTPModel;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationActivity extends AppCompatActivity {
    private ActivityMobileVerificationBinding activityBinding;
    private String fireballToken;
    private String pseudoUniqueID;
    private String sMobile;
    private String sCountryCode;
    private boolean resentOtp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_verification);
        activityBinding.setActivity(this);
        MobileModel mobileModel = new MobileModel();
        activityBinding.setModel(mobileModel);

        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            fireballToken = bundle.getString("token");
            getMobileNumber(bundle.getString("email"));
        }
        pseudoUniqueID = Build.SERIAL;
        Log.e("pseudoUniqueID", pseudoUniqueID + "");

        startTimer();

    }


    /**
     * Validate Login form
     *
     * @param mobileModel MobileModel
     * @return true if valid else false
     */
    private boolean validate(MobileModel mobileModel) {
        Extension extension = Extension.getInstance();
        if (isEmpty(mobileModel.getOtp())) {
            activityBinding.edtOTP.setError(getText(R.string.hint_otp));
            return false;
        } else if (!extension.executeStrategy(MobileVerificationActivity.this, mobileModel.getOtp(), ValidationTemplate
                .INTERNET)) {
            Utility.setNoInternetPopup(MobileVerificationActivity.this);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Login button click method
     *
     * @param mobileModel MobileModel
     */

    public void onClickSendOTP(MobileModel mobileModel) {
        if (validate(mobileModel)) {
            ProgressDialog.getInstance().showProgressDialog(MobileVerificationActivity.this);
            mobileModel.setLanguage(Utility.getLanguage(MobileVerificationActivity.this));
            mobileModel.setOtp(mobileModel.getOtp());
            mobileModel.setIEMINumber(pseudoUniqueID);
            mobileModel.setAndroidDeviceToken(fireballToken);
            mobileModel.setMobile(sMobile);
            Api api = APIClient.getClient().create(Api.class);
            final Call<OTPRegisterResponse> loginResponseCall = api.checkOtp(mobileModel);
            loginResponseCall.enqueue(new Callback<OTPRegisterResponse>() {
                @Override
                public void onResponse(Call<OTPRegisterResponse> call, Response<OTPRegisterResponse> response) {
                    ProgressDialog.getInstance().dismissDialog();
                    handleLoginResponse(response.body());
                }

                @Override
                public void onFailure(Call<OTPRegisterResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    loginResponseCall.cancel();
                    Utility.showToast(MobileVerificationActivity.this, t.getMessage());
                }
            });
        }
    }


    /**
     * Handle login response
     *
     * @param userRegistrationResponse SuccessfulResponse
     */
    private void handleLoginResponse(OTPRegisterResponse userRegistrationResponse) {
        if (userRegistrationResponse != null) {
            if (userRegistrationResponse.getResponseCode().equals(Api.SUCCESS)) {
                saveDetails(userRegistrationResponse);
                startActivity(new Intent(MobileVerificationActivity.this, HomeActivity.class));
                finish();
            } else {
                Utility.showToast(MobileVerificationActivity.this, userRegistrationResponse.getMessage());
            }
        }
    }

    /**
     * Save registered user details
     *
     * @param userRegistrationResponse Registration model
     */
    private void saveDetails(OTPRegisterResponse userRegistrationResponse) {

        PreferenceConnector.writeBoolean(MobileVerificationActivity.this, PreferenceConnector.IS_LOGIN, true);
        String userData = new Gson().toJson(userRegistrationResponse);
        PreferenceConnector.writeString(MobileVerificationActivity.this, PreferenceConnector.USER_ID,
                userRegistrationResponse.getUserId());
        PreferenceConnector.writeString(MobileVerificationActivity.this, PreferenceConnector.USER_DATA, userData);
        PreferenceConnector.writeString(MobileVerificationActivity.this, PreferenceConnector.USER_NAME,
                userRegistrationResponse.getUserName());
        PreferenceConnector.writeString(MobileVerificationActivity.this, PreferenceConnector.IS_MEMBER,
                userRegistrationResponse.getIsMember());
        PreferenceConnector.writeString(MobileVerificationActivity.this, PreferenceConnector.MAX_DIVICE_COUNT,
                userRegistrationResponse.getMaxDeviceCount());
       /* PreferenceConnector.writeString(MobileVerificationActivity.this, PreferenceConnector.USER_PIC,
                userRegistrationResponse.getProfilePic());*/
        //PreferenceConnector.writeString(UserRegistrationActivity.this, PreferenceConnector.LOGIN_TYPE, Constant
        // .USER_LOGIN);
    }

    /**
     * Check string empty or not 027393
     *
     * @param str string to check
     * @return true if empty else false 108397
     */

    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }


    public void getMobileNumber(String sEmail) {
        ProgressDialog.getInstance().showProgressDialog(MobileVerificationActivity.this);
        EmailModel emailModel = new EmailModel();
        emailModel.setLanguage(Utility.getLanguage(MobileVerificationActivity.this));
        emailModel.setEmail(sEmail);

        Api api = APIClient.getClient().create(Api.class);
        final Call<MobileResponse> loginResponseCall = api.getMobileNumber(emailModel);
        loginResponseCall.enqueue(new Callback<MobileResponse>() {
            @Override
            public void onResponse(Call<MobileResponse> call, Response<MobileResponse> response) {
                ProgressDialog.getInstance().dismissDialog();
                MobileResponse mobileResponse = response.body();
                if (mobileResponse != null) {
                    // Utility.showToast(MobileVerificationActivity.this, mobileResponse.getMessage()); R9DY4IC
                    if (mobileResponse.getResponseCode().equals(Api.SUCCESS)) {
                        sMobile = mobileResponse.getMobile();
                        String numbers = sMobile.substring(sMobile.length() - 3);
                        activityBinding.number.setText(getString(R.string.verification_code_msg) + " " + "XXXXXXX" + numbers);
                    }
                }
            }

            @Override
            public void onFailure(Call<MobileResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                loginResponseCall.cancel();
                Utility.showToast(MobileVerificationActivity.this, t.getMessage());
            }
        });
    }

    public void onClickResendOTP() {
        if (resentOtp) {
            Extension extension = Extension.getInstance();
            if (!extension.executeStrategy(MobileVerificationActivity.this, "", ValidationTemplate
                    .INTERNET)) {
                Utility.setNoInternetPopup(MobileVerificationActivity.this);
            } else {
                ProgressDialog.getInstance().showProgressDialog(MobileVerificationActivity.this);
                ResendOTPModel resendOTPModel = new ResendOTPModel();
                resendOTPModel.setLanguage(Utility.getLanguage(MobileVerificationActivity.this));
                resendOTPModel.setCountryCode(Utility.getCountryCode(MobileVerificationActivity.this));
                resendOTPModel.setMobile(sMobile);
                Api api = APIClient.getClient().create(Api.class);
                final Call<SuccessfulResponse> loginResponseCall = api.reSendOTP(resendOTPModel);
                loginResponseCall.enqueue(new Callback<SuccessfulResponse>() {
                    @Override
                    public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse> response) {
                        ProgressDialog.getInstance().dismissDialog();
                        SuccessfulResponse mobileResponse = response.body();
                        if (mobileResponse != null) {
                            // Utility.showToast(MobileVerificationActivity.this, mobileResponse.getMessage());
                            if (mobileResponse.getResponseCode().equals(Api.SUCCESS)) {
                                resentOtp = false;
                                activityBinding.resendCode.setBackground(ContextCompat.getDrawable(MobileVerificationActivity.this, R.drawable.button_grey_corner));
                                startTimer();
                            } else {
                                Utility.showToast(MobileVerificationActivity.this, mobileResponse.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                        ProgressDialog.getInstance().dismissDialog();
                        loginResponseCall.cancel();
                        Utility.showToast(MobileVerificationActivity.this, t.getMessage());
                    }
                });
            }
        }
    }

    public void startTimer() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                activityBinding.timer.setText("in: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                resentOtp = true;
                //binder.resendCode.setBackground(R.drawable.button_primary);
                activityBinding.resendCode.setBackground(ContextCompat.getDrawable(MobileVerificationActivity.this, R.drawable.button_primary));
                activityBinding.timer.setText("");
            }
        }.start();
    }
}
