package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityForgotBinding;
import app.com.maksab.util.Extension;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.viewmodel.ForgotModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotActivity extends AppCompatActivity {
    ActivityForgotBinding activityBinding;

    @Override
    protected void onStart() {
        super.onStart();
        if (isAlreadyLogin()) {
            startActivity(new Intent(ForgotActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot);
        activityBinding.setActivity(this);
        ForgotModel forgotModel = new ForgotModel();
        activityBinding.setModel(forgotModel);
    }

    private boolean isAlreadyLogin() {
        return PreferenceConnector.readBoolean(ForgotActivity.this, PreferenceConnector.IS_LOGIN, false);
    }

    /**
     * Validate Login form
     * @param forgotModel LoginModel
     * @return true if valid else false
     */
    private boolean validate(ForgotModel forgotModel) {
        Extension extension = Extension.getInstance();
        if (isEmpty(forgotModel.getEmail())) {
            if (forgotModel.getEmail().isEmpty()) {
                activityBinding.emailInputLayout.setError(getText(R.string.error_email));
            }
            return false;
        } else if (!extension.executeStrategy(ForgotActivity.this, forgotModel.getEmail(), ValidationTemplate.EMAIL)) {
            Utility.showToast(ForgotActivity.this, getString(R.string.invalid_email));
            return false;
        } else if (!extension.executeStrategy(ForgotActivity.this, forgotModel.getEmail(), ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(ForgotActivity.this);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Login button click method
     * @param forgotModel ForgotModel
     */
    public void onForgotButtonClick(ForgotModel forgotModel) {
        if (validate(forgotModel)) {
            ProgressDialog.getInstance().showProgressDialog(ForgotActivity.this);
            forgotModel.setLanguage(Utility.getLanguage(ForgotActivity.this));
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> loginResponseCall = api.forgotPassword(forgotModel);
            loginResponseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse> response) {
                    ProgressDialog.getInstance().dismissDialog();
                    handleLoginResponse(response.body());
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    loginResponseCall.cancel();
                    Utility.showToast(ForgotActivity.this, t.getMessage());
                }
            });
        }
    }

    /**
     * Handle login response
     * @param successfulResponse SuccessfulResponse
     */
    private void handleLoginResponse(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
            Utility.showToast(ForgotActivity.this, successfulResponse.getMessage());
            if (successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
                finish();
            }
        }
    }

    /**
     * Check string empty or not
     * @param str string to check
     * @return true if empty else false
     */
    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }
}
