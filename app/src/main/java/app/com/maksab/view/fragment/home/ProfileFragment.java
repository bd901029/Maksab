package app.com.maksab.view.fragment.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.io.ByteArrayOutputStream;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.GetOrderAmount;
import app.com.maksab.api.dao.ProfileResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.DialogChangePasswordBinding;
import app.com.maksab.databinding.FragmentProfileBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.activity.BecomePartnerActivity;
import app.com.maksab.view.activity.CountryLanguageActivity;
import app.com.maksab.view.activity.FamilyMemberActivity;
import app.com.maksab.view.activity.GiftHistoryActivity;
import app.com.maksab.view.activity.HomeActivity;
import app.com.maksab.view.activity.LoginActivity;
import app.com.maksab.view.activity.OfferDetailsActivity;
import app.com.maksab.view.activity.PastPurchaseActivity;
import app.com.maksab.view.activity.PointsRewardActivity;
import app.com.maksab.view.activity.ProfileEditActivity;
import app.com.maksab.view.activity.SplashActivity;
import app.com.maksab.view.activity.SubscriptionActivity;
import app.com.maksab.view.activity.UpcomingPurchaseActivity;
import app.com.maksab.view.adapter.ImageViewPagerAdapter;
import app.com.maksab.view.fragment.ProfileEditFragment;
import app.com.maksab.view.viewmodel.ChangePasswordModel;
import app.com.maksab.view.viewmodel.GetStoreListModel;
import app.com.maksab.view.viewmodel.GiftModel;
import app.com.maksab.view.viewmodel.LoginModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {

	public ProfileFragment() {
		// Required empty public constructor
	}

	private FragmentProfileBinding fragmentBinding;
	private final static String STORE_TYPE_ID = "store_type_id";
	private String storeTypeId = "";
	private ProfileResponse profileResponse;
	String title[] = new String[5];
	boolean isFirst = true;
	private DialogChangePasswordBinding dialogImageBinding;
	private Dialog dialog;
	private ChangePasswordModel changePasswordModel = new ChangePasswordModel();

	public static ProfileFragment newInstance(String storeTypeId) {
		Bundle args = new Bundle();
		ProfileFragment fragment = new ProfileFragment();
		args.putString(STORE_TYPE_ID, storeTypeId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			storeTypeId = bundle.getString(STORE_TYPE_ID);
		} else {
			Utility.showToast(getActivity(), getString(R.string.wrong));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_profile, container, false);
		return fragmentBinding.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		fragmentBinding.setFragment(this);

		if (Utility.getIsMember(getActivity()).equalsIgnoreCase("1")){
			fragmentBinding.llFamilyMember.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(storeTypeId)) {
			GetStoreListModel getStoreListModel = new GetStoreListModel();
			getStoreListModel.setStoreTypeId(storeTypeId);
		} else {
			getActivity().onBackPressed();
		}
		AQuery aQuery = new AQuery(fragmentBinding.countryFlag);
		aQuery.id(fragmentBinding.countryFlag).image(Utility.getCountryFlag(getActivity()), true, true, 50, R.drawable
				.logo_small);

		title[0] = getString(R.string.my_upcoming_purchase);
		title[1] = getString(R.string.my_past_purchase);
		title[2] = getString(R.string.points_rewards);
		title[3] = getString(R.string.subscription);
		title[4] = getString(R.string.gift_history);

		Extension extension = Extension.getInstance();
		if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(getActivity());
		}else getProfile();
	}

	public void getProfile() {
		ProgressDialog.getInstance().showProgressDialog(getActivity());
		UserIdModel userIdModel = new UserIdModel();
		userIdModel.setUserId(Utility.getUserId(getActivity()));
		userIdModel.setLanguage(Utility.getLanguage(getActivity()));
		Api api = APIClient.getClient().create(Api.class);
		final Call<ProfileResponse> responseCall = api.getProfile(userIdModel);
		responseCall.enqueue(new Callback<ProfileResponse>() {
			@Override
			public void onResponse(Call<ProfileResponse> call, retrofit2.Response<ProfileResponse>
					response) {
				if (getActivity() != null && isVisible()) {
					profileResponse = response.body();
					handleResponse();
					Extension extension = Extension.getInstance();
					if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
						Utility.setNoInternetPopup(getActivity());
					}else getOrderAmount();
				}
			}

			@Override
			public void onFailure(Call<ProfileResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				if (getActivity() != null && isVisible()) {
					Utility.showToast(getActivity(), t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}

	private void handleResponse() {
		if (profileResponse != null) {
			if (profileResponse.getResponseCode() != null && profileResponse.getResponseCode().equals(Api.SUCCESS)) {
				fragmentBinding.setModel(profileResponse);
				if (profileResponse.getEmailVerify().equalsIgnoreCase("1")) {
					fragmentBinding.emailVerified.setText(getString(R.string.verified));
					fragmentBinding.emailVerified.setTextColor(getResources().getColor((R.color.green_full)));

				} else {
					fragmentBinding.emailVerified.setTextColor(getResources().getColor((R.color.red_full)));
					fragmentBinding.emailVerified.setText(getString(R.string.verify));
				}

				if (profileResponse.getMobileVerify().equalsIgnoreCase("1")) {
					fragmentBinding.mobileVerified.setText(getString(R.string.verified));
					fragmentBinding.mobileVerified.setTextColor(getResources().getColor((R.color.green_full)));

				} else {
					fragmentBinding.mobileVerified.setTextColor(getResources().getColor((R.color.red_full)));
					fragmentBinding.mobileVerified.setText(getString(R.string.verify));
				}
			}
		}
	}

	public void onClickEditProfile() {
		((HomeActivity) getActivity()).addFragment(ProfileEditFragment.newInstance(profileResponse),
				"ProfileEditFragment", false);
		//  startActivity(new Intent(getActivity(), ProfileEditActivity.class));
	}


	public Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	private void getOrderAmount() {
		UserIdModel model = new UserIdModel();
		model.setUserId(Utility.getUserId(getActivity()));
		model.setLanguage(Utility.getLanguage(getActivity()));
		Api api = APIClient.getClient().create(Api.class);
		final Call<GetOrderAmount> responseCall = api.getOrderAmount(model);
		responseCall.enqueue(new Callback<GetOrderAmount>() {
			@Override
			public void onResponse(Call<GetOrderAmount> call, retrofit2.Response<GetOrderAmount> response) {
				ProgressDialog.getInstance().dismissDialog();
				GetOrderAmount amount = response.body();
				fragmentBinding.setModel2(amount);
			}

			@Override
			public void onFailure(Call<GetOrderAmount> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				Utility.showToast(getActivity(), t + "");
			}
		});
	}

	public void onClickPurchases() {
		LinearLayout mLinearLayout = fragmentBinding.llPurchaseDetails;
		if (isFirst) {
			addLayout(mLinearLayout);
			isFirst = false;
		} else {
			mLinearLayout.removeAllViews();
			isFirst = true;
		}
	}

	private void addLayout(LinearLayout mLinearLayout) {
		mLinearLayout.removeAllViews();
		for (int i = 0; i < title.length; i++) {
			View layout2 = LayoutInflater.from(getActivity()).inflate(R.layout.copy_row_navigation_item, mLinearLayout,
					false);
			TextView title_copy = (TextView) layout2.findViewById(R.id.title_copy);
			LinearLayout copy_ll = layout2.findViewById(R.id.copy_ll);
			title_copy.setText(title[i]);
			final int finalI = i;
			copy_ll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (title[finalI].equals(getString(R.string.my_upcoming_purchase))) {
						Intent intent = new Intent(getActivity(), UpcomingPurchaseActivity.class);
						intent.putExtra("position", finalI);
						startActivity(intent);
					} else if (title[finalI].equals(getString(R.string.my_past_purchase))) {
						Intent intent = new Intent(getActivity(), PastPurchaseActivity.class);
						intent.putExtra("position", finalI);
						startActivity(intent);
					} else if (title[finalI].equals(getString(R.string.points_rewards))) {
						Intent intent = new Intent(getActivity(), PointsRewardActivity.class);
						startActivity(intent);
					} else if (title[finalI].equals(getString(R.string.subscription))) {
						Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
						startActivity(intent);
					} else if (title[finalI].equals(getString(R.string.gift_history))) {
						Intent intent = new Intent(getActivity(), GiftHistoryActivity.class);
						startActivity(intent);
					} else {
					}
				}
			});
			mLinearLayout.addView(layout2);
		}
	}

	public void onClickCountry() {
		Intent intent = new Intent(getActivity(), CountryLanguageActivity.class);
		intent.putExtra(Constant.FROM_ACTIVITY, Constant.FROM_ACTIVITY_HOME);
		startActivity(intent);
		getActivity().finish();
	}

	public void onClickFamilyMember() {
		startActivity(new Intent(getActivity(), FamilyMemberActivity.class));
	}

	/**
	 * Show logout confirmation diagog
	 */
	public void onClickLogout() {
		Utility.setDialog(getActivity(), getString(R.string.alert), getString(R.string.logout_message),
				getString(R.string.no), getString(R.string.yes), new DialogListener() {
					@Override
					public void onNegative(DialogInterface dialog) {
						dialog.dismiss();
					}

					@Override
					public void onPositive(DialogInterface dialog) {
						dialog.dismiss();
						doLogout();
					}
				});
	}

	/**
	 * Logout user and open splash screen
	 */
	private void doLogout() {
        /*PreferenceConnector.clear(getActivity());
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();*/
		PreferenceConnector.writeBoolean(getActivity(), PreferenceConnector.IS_LOGIN, false);
		Intent intent = new Intent(getActivity(), SplashActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		//getActivity().finish();
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
	 * Validate Login form
	 * @param changePasswordModel ChangePasswordModel
	 * @return true if valid else false
	 */
	private boolean validate(ChangePasswordModel changePasswordModel) {
		app.com.maksab.util.Extension extension = app.com.maksab.util.Extension.getInstance();
		if (isEmpty(changePasswordModel.getOldPassword()) || isEmpty(changePasswordModel.getNewPassword())
				|| isEmpty(changePasswordModel.getNewPassword())) {
			if (changePasswordModel.getOldPassword().isEmpty()) {
				dialogImageBinding.oldPWInputLayout.setError(getText(R.string.hint_old_password));
			}
			if (changePasswordModel.getNewPassword().isEmpty()) {
				dialogImageBinding.newPWInputLayout.setError(getText(R.string.hint_new_password));
			}
			if (changePasswordModel.getConfirmPassword().isEmpty()) {
				dialogImageBinding.confirmNPWInputLayout.setError(getText(R.string.hint_confirm_password));
			}
			return false;
		}
		else if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(getActivity());
			return false;
		} else {
			return true;
		}
	}

	public void onClickChangePasswordDialog() {
		dialog = new Dialog(getActivity());
		dialogImageBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
				R.layout.dialog_change_password, null, false);
		dialog.setContentView(dialogImageBinding.getRoot());
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT);

		dialogImageBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		dialogImageBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changePasswordModel.setOldPassword(dialogImageBinding.oldPW.getText().toString());
				changePasswordModel.setNewPassword(dialogImageBinding.newPW.getText().toString());
				changePasswordModel.setConfirmPassword(dialogImageBinding.confirmPW.getText().toString());
				sendChangePassword();
			}
		});
		dialog.show();
	}

	public void sendChangePassword() {
		if (validate(changePasswordModel)) {
			ProgressDialog.getInstance().showProgressDialog(getActivity());
			changePasswordModel.setUserId(Utility.getUserId(getActivity()));
			changePasswordModel.setLanguage(Utility.getLanguage(getActivity()));
			Api api = APIClient.getClient().create(Api.class);
			final Call<SuccessfulResponse> responseCall = api.changePassword(changePasswordModel);
			responseCall.enqueue(new Callback<SuccessfulResponse>() {
				@Override
				public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
						response) {
					SuccessfulResponse  successfulResponse = response.body();
					ProgressDialog.getInstance().dismissDialog();
					if (getActivity() != null && isVisible()) {
						if (successfulResponse != null) {
							Utility.showToast(getActivity(), successfulResponse.getMessage());
							if (successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
								dialog.dismiss();
							}
						}
					}
				}

				@Override
				public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
					ProgressDialog.getInstance().dismissDialog();
					if (getActivity() != null && isVisible()) {
						Utility.showToast(getActivity(), t + "");
						Log.e("", "onFailure: " + t.getLocalizedMessage());
					}
				}
			});
		}
	}

}

