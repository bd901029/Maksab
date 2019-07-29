package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityIntroBinding;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.TokenModel;
import com.google.firebase.iid.FirebaseInstanceId;
import retrofit2.Call;
import retrofit2.Callback;

public class IntroActivity extends AppCompatActivity {
	ActivityIntroBinding binder;
	private String fireballToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binder = DataBindingUtil.setContentView(this, R.layout.activity_intro);
		binder.setActivity(this);

		if (!PreferenceConnector.readBoolean(IntroActivity.this, PreferenceConnector.IS_FIRST_TIME, false)){
			fireballToken = FirebaseInstanceId.getInstance().getToken();
			sendToken();
		}
	}

	public void onClickLater(){
		startActivity(new Intent(IntroActivity.this, HomeActivity.class));
		finish();
	}

	public void onClickSignIn(){
		startActivity(new Intent(IntroActivity.this, LoginActivity.class));
		finish();
	}

	public void onClickRegister(){
		startActivity(new Intent(IntroActivity.this, UserRegistrationActivity.class));
		finish();
	}

	private void sendToken() {
		ProgressDialog.getInstance().showProgressDialog(IntroActivity.this);
		TokenModel tokenModel = new TokenModel();
		tokenModel.setAndroidDeviceToken(fireballToken);
		tokenModel.setLanguage(Utility.getLanguage(IntroActivity.this));
		//Build.SERIAL

		Api api = APIClient.getClient().create(Api.class);
		final Call<SuccessfulResponse> responseCall = api.setApiToken(tokenModel);
		responseCall.enqueue(new Callback<SuccessfulResponse>() {
			@Override
			public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse> response) {
				ProgressDialog.getInstance().dismissDialog();
				SuccessfulResponse myResponse = response.body();
				if (myResponse != null) {
					if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
						PreferenceConnector.writeBoolean(IntroActivity.this, PreferenceConnector.IS_FIRST_TIME, true);
					}else
						Utility.showToast(IntroActivity.this, myResponse.getMessage() + "");
				}
			}

			@Override
			public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				Utility.showToast(IntroActivity.this, t+"");
			}
		});
	}
}
