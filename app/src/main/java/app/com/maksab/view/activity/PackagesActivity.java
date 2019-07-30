package app.com.maksab.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.PackagesResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityPackagesBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.*;
import app.com.maksab.view.adapter.PackagesAdapter;
import app.com.maksab.view.viewmodel.FreePlanModel;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.ArrayList;

public class PackagesActivity extends AppCompatActivity {
	private ActivityPackagesBinding uiBinder;
	private String sPlanId = "";
	private String sPlanAmount = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiBinder = DataBindingUtil.setContentView(this, R.layout.activity_packages);
		uiBinder.setActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Extension extension = Extension.getInstance();
		if (!extension.executeStrategy(PackagesActivity.this, "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(PackagesActivity.this);
		} else {
			getFavoriteDeals();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if(resultCode == Activity.RESULT_OK){
				String result = data.getStringExtra("result");
				getFavoriteDeals();
			}
			
			if (resultCode == Activity.RESULT_CANCELED) {
				//Write your code if there's no result
			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public void getFavoriteDeals() {
		ProgressDialog.getInstance().showProgressDialog(PackagesActivity.this);
		UserCityIdModel userCityIdModel = new UserCityIdModel();
		userCityIdModel.setCityId(Utility.getCityId(PackagesActivity.this));
		userCityIdModel.setUserId(Utility.getUserId(PackagesActivity.this));
		userCityIdModel.setLanguage(Utility.getLanguage(PackagesActivity.this));
		Api api = APIClient.getClient().create(Api.class);
		final Call<PackagesResponse> responseCall = api.getPlanList(userCityIdModel);
		responseCall.enqueue(new Callback<PackagesResponse>() {
			@Override
			public void onResponse(Call<PackagesResponse> call, retrofit2.Response<PackagesResponse>
					response) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					handleStoreListResponse(response.body());
				}
			}

			@Override
			public void onFailure(Call<PackagesResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					Utility.showToast(PackagesActivity.this, t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}

	/**
	 * Handle category list response
	 * @param myResponse @CategoryListResponse object
	 */
	private void handleStoreListResponse(PackagesResponse myResponse) {
		try {
			if (myResponse != null) {
				if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
					if (myResponse.getPlansArrayList() != null && myResponse.getPlansArrayList().size() != 0) {
						updateList(myResponse.getPlansArrayList());
					}
				} else {
				}
			}
		} catch (Exception e) {
			Utility.showToast(PackagesActivity.this, getString(R.string.server_not_response));
			e.printStackTrace();
		}
	}

	/**
	 * Set recycler view Adapter
	 * @param packagePlans Category array list for adapter
	 */
	private void updateList(ArrayList<PackagesResponse.PackagePlan> packagePlans) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				if (PreferenceConnector.readBoolean(PackagesActivity.this, PreferenceConnector.IS_LOGIN, false)) {
					PackagesResponse.PackagePlan packagePlan = (PackagesResponse.PackagePlan) obj;
					sPlanId = packagePlan.planId;
					sPlanAmount = packagePlan.afterAmount;

					PackageDetailActivity.packagePlan = packagePlan;
					PackageDetailActivity.currency = sPlanAmount.replaceAll("[^A-Za-z]+", "");
					PackageDetailActivity.amount = sPlanAmount.replaceAll("[^0-9]", "");
					Intent intent = new Intent(PackagesActivity.this, PackageDetailActivity.class);
					startActivityForResult(intent, 1);
					return;
				} else {
					userGuestDialog();
				}
			}
		};
		uiBinder.recyclerView.setLayoutManager(new GridLayoutManager(PackagesActivity.this, 1));
		uiBinder.recyclerView.setAdapter(new PackagesAdapter(PackagesActivity.this, packagePlans, onItemClickListener));
	}

	/**Show logout confirmation diagog */
	private void showPopupLoverPackage() {
		Utility.setDialog(PackagesActivity.this, getString(R.string.alert), getString(R.string.already_have_higher_package),
				null, getString(R.string.ok), new DialogListener() {
					@Override
					public void onNegative(DialogInterface dialog) {
						dialog.dismiss();
					}

					@Override
					public void onPositive(DialogInterface dialog) {
						dialog.dismiss();
					}
				});
	}

	/** Show logout confirmation diagog */
	private void showPopupUpgradePackage() {
		Utility.setDialog(PackagesActivity.this, getString(R.string.alert), getString(R.string.you_want_to_upgrade),
				getString(R.string.no), getString(R.string.yes), new DialogListener() {
					@Override
					public void onNegative(DialogInterface dialog) {
						dialog.dismiss();
					}

					@Override
					public void onPositive(DialogInterface dialog) {
						dialog.dismiss();
						Intent intent = new Intent(PackagesActivity.this, PaymentActivity.class);
						intent.putExtra(PaymentActivity.planId, sPlanId);
						intent.putExtra(PaymentActivity.planAmount, sPlanAmount);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivityForResult(intent, 1);
						finish();
						// Intent i = new Intent(this, SecondActivity.class);
						//startActivityForResult(i, 1);
					}
				});
	}

	/**Show Guest Login Alert*/
	private void userGuestDialog() {
		Utility.setDialog(PackagesActivity.this, getString(R.string.alert), getString(R.string.guest_login_alert),
				getString(R.string.no), getString(R.string.yes), new DialogListener() {
					@Override
					public void onNegative(DialogInterface dialog) {
						dialog.dismiss();
					}

					@Override
					public void onPositive(DialogInterface dialog) {
						dialog.dismiss();
						Intent intent = new Intent(PackagesActivity.this, IntroActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
				});
	}

	public void freePackage(String planId) {
		ProgressDialog.getInstance().showProgressDialog(PackagesActivity.this);
		FreePlanModel freePlanModel = new FreePlanModel();
		freePlanModel.setCityId(Utility.getCityId(PackagesActivity.this));
		freePlanModel.setUserId(Utility.getUserId(PackagesActivity.this));
		freePlanModel.setLanguage(Utility.getLanguage(PackagesActivity.this));
		freePlanModel.setPlanId(planId);

		Api api = APIClient.getClient().create(Api.class);
		final Call<SuccessfulResponse> responseCall = api.freePlan(freePlanModel);
		responseCall.enqueue(new Callback<SuccessfulResponse>() {
			@Override
			public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse> response) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					//handleStoreListResponse(response.body());
					finish();
				}
			}

			@Override
			public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					Utility.showToast(PackagesActivity.this, t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}
}
