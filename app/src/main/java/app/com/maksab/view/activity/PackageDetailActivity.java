package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CouponCodeResponse;
import app.com.maksab.api.dao.PackagesResponse;
import app.com.maksab.databinding.ActivityFreePackagePlanBinding;
import app.com.maksab.util.Helper;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.Utility;
import app.com.maksab.view.adapter.SubPackagesAdapter;
import app.com.maksab.view.viewmodel.CouponCodeModel;
import com.androidquery.AQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageDetailActivity extends AppCompatActivity {
	private ActivityFreePackagePlanBinding binder;

	public static PackagesResponse.PackagePlan packagePlan = null;
	public static String currency = "", amount = "";
	String couponCodeId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_package_plan);

		initUI();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	private void initUI() {
		binder = DataBindingUtil.setContentView(this, R.layout.activity_free_package_plan);
		binder.setActivity(this);
	}

	private void updateUI() {
		if (packagePlan == null) {
			return;
		}

		binder.setModel(packagePlan);

		if (packagePlan.isFree()) {
			binder.afterAmount.setText("Free");
		}

		int width = (PreferenceConnector.readInteger(this, PreferenceConnector.DEVICE_WIDTH,150)/2);
		ViewGroup.LayoutParams params = binder.headerView.getLayoutParams();
		params.height = width - 12;
		// params.width = width;
		binder.headerView.setLayoutParams(params);


		if (packagePlan.getPlanStatus().equalsIgnoreCase("1")){
			binder.subscribed.setVisibility(View.VISIBLE);
		} else {
			binder.subscribed.setVisibility(View.GONE);
		}

		if (packagePlan.getPlanImg().equals(""))
			binder.image.setBackgroundColor(Color.parseColor(packagePlan.planColor));
		else{
			AQuery aQuery = new AQuery(binder.image);
			aQuery.id(binder.image).image(packagePlan.getPlanImg(), true, true, 300, R.drawable.logo_small);
		}

		binder.beforeAmount.setPaintFlags(binder.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

		if (packagePlan.getFacilitysArrayList() != null && packagePlan.getFacilitysArrayList().size() != 0) {
			binder.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
			binder.recyclerView.setAdapter(new SubPackagesAdapter(this, packagePlan.getFacilitysArrayList(), null, packagePlan.planColor));
		}
	}

	private void showPaymentActivity() {
		Intent intent = new Intent(PackageDetailActivity.this, PaymentActivity.class);
		intent.putExtra(PaymentActivity.planId, packagePlan.planId);
		intent.putExtra(PaymentActivity.planAmount, amount);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivityForResult(intent, 1);
	}

	public void applyCouponCode() {
		if (packagePlan.isFreeAmount()) {
			return;
		}

		String couponCode = binder.couponCodeView.getText().toString();
		if (couponCode.isEmpty()) {
			binder.couponCodeView.setError(getText(R.string.error_coupon_code));
			return;
		}

		String planAmount = packagePlan.afterAmount;
		planAmount = planAmount.replaceAll("[^0-9]", "");

		CouponCodeModel couponCodeModel = new CouponCodeModel();
		couponCodeModel.setLanguage(Utility.getLanguage(this));
		couponCodeModel.setUserId(Utility.getUserId(this));
		couponCodeModel.setCityId(Utility.getCityId(this));
		couponCodeModel.setCouponCode(couponCode);
		couponCodeModel.setPlanId(packagePlan.getPlanId());
		couponCodeModel.setPlanAmount(planAmount);
		Api api = APIClient.getClient().create(Api.class);
		final Call<CouponCodeResponse> responseCall = api.checkCouponCode(couponCodeModel);

		Helper.showProgress(this);
		responseCall.enqueue(new Callback<CouponCodeResponse>() {
			@Override
			public void onResponse(Call<CouponCodeResponse> call, Response<CouponCodeResponse> response) {
				Helper.hideProgress();
				if (!isDestroyed()) {
					handleCouponCodeResponse(response.body());
				}
			}

			@Override
			public void onFailure(Call<CouponCodeResponse> call, Throwable t) {
				Helper.hideProgress();
				if (!isDestroyed()) {
					Utility.showToast(PackageDetailActivity.this, t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}

	private void handleCouponCodeResponse(CouponCodeResponse couponCodeResponse) {
		if (!couponCodeResponse.getResponseCode().equals(Api.SUCCESS)) {
			Utility.showToast(PackageDetailActivity.this, couponCodeResponse.getMessage());
			return;
		}

		if (couponCodeResponse.getPaystatus().equalsIgnoreCase("0")) {
			Utility.showToast(PackageDetailActivity.this, couponCodeResponse.getMessage());
			finish();
		} else {
			couponCodeId = couponCodeResponse.getCouponCodeId();
			amount = couponCodeResponse.getDiscountedPrice();
			binder.afterAmount.setText(currency + " " + amount);
		}
	}

	public void proceedToCheckout() {
		showPaymentActivity();
	}
}