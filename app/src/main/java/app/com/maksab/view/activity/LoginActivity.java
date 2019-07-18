package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.LoginResponse;
import app.com.maksab.databinding.ActivityLoginBinding;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.viewmodel.LoginModel;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding activityBinding;
    private String fireballToken;

    @Override
    protected void onStart() {
        super.onStart();
        if (isAlreadyLogin()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Utility.saveDeviceHeightWidth(this);
        fireballToken = FirebaseInstanceId.getInstance().getToken();
        LoginModel loginModel = new LoginModel();
       // loginModel.setEmail("user@gmail.com");
        //loginModel.setPassword("123456");
        activityBinding.setModel(loginModel);
    }

    private boolean isAlreadyLogin() {
        return PreferenceConnector.readBoolean(LoginActivity.this, PreferenceConnector.IS_LOGIN, false);
    }

    /**
     * Validate Login form
     * @param loginModel LoginModel
     * @return true if valid else false
     */
    private boolean validate(LoginModel loginModel) {
        app.com.maksab.util.Extension extension = app.com.maksab.util.Extension.getInstance();
        if (isEmpty(loginModel.getEmail()) || isEmpty(loginModel.getPassword())) {
            if (loginModel.getEmail().isEmpty()) {
                activityBinding.emailInputLayout.setError(getText(R.string.error_email_or_mobile));
            }
            if (loginModel.getPassword().isEmpty()) {
                activityBinding.passwordInputLayout.setError(getText(R.string.error_pass));
            }
            Utility.showToast(LoginActivity.this, getString(R.string.email_pass));
            return false;
        } else if (!extension.executeStrategy(LoginActivity.this, loginModel.getEmail(), ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(LoginActivity.this);
            return false;
        } else {
            return true;
        }
    }

    public void onForgotBtnClick(){
        startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
    }

    /**
     * Login button click method
     * @param loginModel LoginModel
     */
    public void onLoginButtonClick(LoginModel loginModel) {
        if (validate(loginModel)) {
            loginModel.setLanguage("en");
            loginModel.setFcmToken(fireballToken);
            loginModel.setIEMINumber(Build.SERIAL);
           app.com.maksab.util.ProgressDialog.getInstance().showProgressDialog(LoginActivity.this);
            Api api = APIClient.getClient().create(Api.class);
            final Call<LoginResponse> loginResponseCall = api.loginUser(loginModel);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    app.com.maksab.util.ProgressDialog.getInstance().dismissDialog();
                    handleLoginResponse(response.body());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    app.com.maksab.util.ProgressDialog.getInstance().dismissDialog();
                    loginResponseCall.cancel();
                    Utility.showToast(LoginActivity.this, t.getMessage());
                }
            });
        }
    }

    /**
     * Handle login response
     * @param loginResponse LoginResponse
     */
    private void handleLoginResponse(LoginResponse loginResponse) {
        if (loginResponse != null) {
            if (loginResponse.getStatus() != null){
                if (loginResponse.getStatus().equals("0")){
                    Intent intent = new Intent(LoginActivity.this, MobileVerificationActivity.class);
                    intent.putExtra("token",fireballToken);
                    intent.putExtra("email",activityBinding.emailEdt.getText().toString());
                    startActivity(intent);
                    finish();
                }else
                    Utility.showToast(LoginActivity.this, loginResponse.getErrorMessage());
            }else if (loginResponse.getResponseCode().equals(Api.SUCCESS)) {
                saveDetails(loginResponse);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }else Utility.showToast(LoginActivity.this, loginResponse.getMessage());
        }
    }

    /**
     * Save registered user details
     * @param loginResponse LoginResponse model
     */
    private void saveDetails(LoginResponse loginResponse) {
        String userData = new Gson().toJson(loginResponse);
        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.USER_ID, loginResponse.getUserId());
        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.USER_DATA, userData);
        PreferenceConnector.writeBoolean(LoginActivity.this, PreferenceConnector.IS_LOGIN, true);
        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.CITY, loginResponse.getCityId());
        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.USER_NAME, loginResponse.getUserName());
        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.USER_PIC,
                loginResponse.getProfilePic());
        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.IS_MEMBER,
                loginResponse.getIsMember());
        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.MAX_DIVICE_COUNT,
                loginResponse.getMaxDeviceCount());
    }

    /**
     * SignUp Button click method
     */
    public void onRegisterButtonClick() {
        startActivity(new Intent(LoginActivity.this, UserRegistrationActivity.class));
        finish();
    }

    /**
     * Check string empty or not
     * @param str string to check
     * @return true if empty else false
     */
    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /*private String getImeiNumber() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //getDeviceId() is Deprecated so for android O we can use getImei() method
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permission)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            imei = getImeiNumber();
            getClientPhoneNumber();
            androidid=getAndroidId();
            Toast.makeText(this,permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imei = getImeiNumber();
                    getClientPhoneNumber();
                    androidid=getAndroidId();
                } else {
                    Toast.makeText(LoginActivity.this, "You have Denied the Permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void getClientPhoneNumber() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                subInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            }
            //check whether the phone is of Multi sim or Not
            if (subInfoList.size() > 1)
            {
                isMultiSimEnabled = true;
            }
            for (SubscriptionInfo subscriptionInfo : subInfoList)
            //add all sim number into arraylist
            {
                numbers.add(subscriptionInfo.getNumber());
            }
            Log.e(TAG,"Sim 1:- "+numbers.get(0));
            Log.e(TAG,"Sim 2:- "+ numbers.get(1));
        }catch (Exception e)
        {
            Log.d(TAG,e.toString());
        }
    }

    private String getAndroidId() {
        androidid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("TAG",Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ALLOWED_GEOLOCATION_ORIGINS));
        Log.e("TAG",Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD));
        return androidid;
    }*/
}
