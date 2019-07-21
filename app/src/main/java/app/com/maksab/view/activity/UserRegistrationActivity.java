package app.com.maksab.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.CompoundButton;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.LoginResponse;
import app.com.maksab.api.dao.RegistrationResponse;
import app.com.maksab.databinding.ActivityUserRegistratoinBinding;
import app.com.maksab.databinding.DialogRecyclerViewBinding;
import app.com.maksab.engine.country.CountryManager;
import app.com.maksab.util.*;
import app.com.maksab.view.viewmodel.UserRegistrationModel;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity extends AppCompatActivity {
	private ActivityUserRegistratoinBinding binder;
	private Dialog dialogCountry;
	private DialogRecyclerViewBinding recyclerViewBinding;
	private String sCountryCode = "";
	private String sCityID = "";
	private String sCountryID = "";
	private String fireballToken;
	private boolean bTC = false;
	private boolean bPP = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binder = DataBindingUtil.setContentView(this, R.layout.activity_user_registratoin);
		binder.setModel(new UserRegistrationModel());
		binder.setActivity(this);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		//getCountryList();
		fireballToken = FirebaseInstanceId.getInstance().getToken();
		//onClickCountry();
		binder.tc.setText((Html.fromHtml(getString(R.string.temrs_of_service))));

		String cityName = CountryManager.sharedInstance().convertCityName(Utility.getCityName(UserRegistrationActivity.this));
		binder.tvCountryName.setText(cityName);

		binder.tvCountryCode.setText(Utility.getCountryCode(UserRegistrationActivity.this));

		binder.switchTC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					//do stuff when Switch is ON
					bTC = true;
				} else {
					//do stuff when Switch if OFF
					bTC = false;
				}
			}
		});

		binder.switchPP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					//do stuff when Switch is ON
					bPP = true;
				} else {
					//do stuff when Switch if OFF
					bPP = false;
				}
			}
		});
	}

	/**
	 * Validate registration form
	 * @param userRegistrationModel Registration model
	 * @return true if valid else false
	 */
	private boolean validate(UserRegistrationModel userRegistrationModel) {
		Extension extension = Extension.getInstance();
		if (isEmpty(userRegistrationModel.getUsername()) || isEmpty(userRegistrationModel.getEmail()) ||
				isEmpty(userRegistrationModel.getPassword()) || isEmpty(userRegistrationModel.getMobile())) {
			if (userRegistrationModel.getUsername().isEmpty()) {
				binder.nameInputLayout.setError(getText(R.string.error_f_name));
			}
			if (userRegistrationModel.getEmail().isEmpty()) {
				binder.emailInputLayout.setError(getText(R.string.error_email));
			}
			if (userRegistrationModel.getMobile().isEmpty()) {
				binder.mobile.setError(getText(R.string.error_phone));
			}
			if (userRegistrationModel.getPassword().isEmpty()) {
				binder.passwordInputLayout.setError(getText(R.string.error_pass));
			}
			return false;
		} /*else if (isEmpty(userRegistrationModel.getUserEmail()) && isEmpty(userRegistrationModel.getPhoneNumber())) {
            Utility.showToast(UserRegistrationActivity.this, getString(R.string.required_email_or_phone));
            return false;
        }*/ else if (!extension.executeStrategy(UserRegistrationActivity.this, userRegistrationModel.getEmail(),
				ValidationTemplate.EMAIL)) {
			Utility.showToast(UserRegistrationActivity.this, getString(R.string.invalid_email));
			return false;
		} else if (!extension.executeStrategy(UserRegistrationActivity.this, "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(UserRegistrationActivity.this);
			return false;
		}else if(!bTC){
			Utility.showToast(UserRegistrationActivity.this, getString(R.string.terms_condition));
			return false;
		}/*else if(!bPP){
            Utility.showToast(UserRegistrationActivity.this, getString(R.string.privacy_policy));
            return false;
        }*//*else if(!userRegistrationModel.getPassword().equals(userRegistrationModel.getConfirmPassword())){
            Utility.showToast(UserRegistrationActivity.this, getString(R.string.password_matching));
            return false;
        }*/ else {
			return true;
		}
	}

	/**
	 * Call on @SignUp button click
	 * @param userRegistrationModel Registration model
	 */
	public void onClickSignUp(final UserRegistrationModel userRegistrationModel) {
		if (validate(userRegistrationModel)) {
			// Log.e("SERIAL",Build.SERIAL);
			userRegistrationModel.setMobile(userRegistrationModel.getMobile());
			ProgressDialog.getInstance().showProgressDialog(UserRegistrationActivity.this);
			userRegistrationModel.setLanguage(Utility.getLanguage(UserRegistrationActivity.this));
			userRegistrationModel.setCountryCode(Utility.getCountryCode(UserRegistrationActivity.this));
			userRegistrationModel.setCityId(Utility.getCity(UserRegistrationActivity.this));
			userRegistrationModel.setFcmToken(fireballToken);
			// userRegistrationModel.setIEMINumber(Build.SERIAL);
			Api api = APIClient.getClient().create(Api.class);
			final Call<RegistrationResponse> registrationCall = api.registerUser(userRegistrationModel);
			registrationCall.enqueue(new Callback<RegistrationResponse>() {
				@Override
				public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
					ProgressDialog.getInstance().dismissDialog();
					handleResponse(response.body());
				}

				@Override
				public void onFailure(Call<RegistrationResponse> call, Throwable t) {
					ProgressDialog.getInstance().dismissDialog();
					registrationCall.cancel();
					Utility.showToast(UserRegistrationActivity.this, t.getMessage());
				}
			});
		}
	}

	/**
	 * Handle registration response
	 * @param registrationResponse Registration model
	 */
	private void handleResponse(RegistrationResponse registrationResponse) {
		if (registrationResponse != null) {
			if (registrationResponse.getStatus() != null){
				if (registrationResponse.getStatus().equals("0")){
					Intent intent = new Intent(UserRegistrationActivity.this, MobileVerificationActivity.class);
					intent.putExtra("token",fireballToken);
					// intent.putExtra("mobile",binder.mobile.getText().toString());
					intent.putExtra("email", binder.emailEdt.getText().toString());
					//intent.putExtra("country_code",binder.emailEdt.getText().toString());

					startActivity(intent);
					finish();
				}else
					Utility.showToast(UserRegistrationActivity.this, registrationResponse.getErrorMessage());
			}
			Utility.showToast(UserRegistrationActivity.this, registrationResponse.getMessage());

            /*if (userRegistrationResponse.getResponseCode().equals(Api.SUCCESS)) {
                //saveDetails(userRegistrationResponse);
                Intent intent = new Intent(UserRegistrationActivity.this, MobileVerificationActivity.class);
                intent.putExtra("token",fireballToken);
                startActivity(intent);
                //startActivity(new Intent(UserRegistrationActivity.this, MobileVerificationActivity.class));
                //finish();
                // bioDialog();
            }else
                Utility.showToast(UserRegistrationActivity.this, userRegistrationResponse.getMessage());*/
		}
	}

	/**
	 * Save registered user details
	 * @param userRegistrationResponse Registration model
	 */
	private void saveDetails(LoginResponse userRegistrationResponse) {
		String userData = new Gson().toJson(userRegistrationResponse);
		PreferenceConnector.writeString(UserRegistrationActivity.this, PreferenceConnector.USER_ID,
				userRegistrationResponse.getUserId());
		PreferenceConnector.writeString(UserRegistrationActivity.this, PreferenceConnector.USER_DATA, userData);
		PreferenceConnector.writeString(UserRegistrationActivity.this, PreferenceConnector.USER_NAME,
				userRegistrationResponse.getUserName());
		PreferenceConnector.writeString(UserRegistrationActivity.this, PreferenceConnector.USER_PIC,
				userRegistrationResponse.getProfilePic());
		//PreferenceConnector.writeString(UserRegistrationActivity.this, PreferenceConnector.LOGIN_TYPE, Constant
		// .USER_LOGIN);
	}

	/**
	 * Check string empty or not
	 *
	 * @param str string to check
	 * @return true if empty else false
	 */
	private boolean isEmpty(String str) {
		return TextUtils.isEmpty(str);
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		onClickSignIn();
	}

	public void onClickSignIn() {
		startActivity(new Intent(UserRegistrationActivity.this, LoginActivity.class));
		finish();
	}

	public void onClickCountry(){
		Intent intent = new Intent(UserRegistrationActivity.this, CountryLanguageActivity.class);
		intent.putExtra(Constant.FROM_ACTIVITY, Constant.FROM_ACTIVITY_SIGN_UP);
		startActivityForResult(intent,101);
	}

	public void onClickTC(){
		startActivity(new Intent(UserRegistrationActivity.this, TCActivity.class));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 101){
			if(resultCode == Activity.RESULT_OK){
				sCityID = data.getStringExtra("city_id");
				sCountryID = data.getStringExtra("country_id");
				binder.tvCountryCode.setText(data.getStringExtra("country_code"));

				String cityName = data.getStringExtra("city_name");
				cityName = CountryManager.sharedInstance().convertCityName(cityName);
				binder.tvCountryName.setText(cityName);
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//Write your code if there's no result
			}



		}
	}
}