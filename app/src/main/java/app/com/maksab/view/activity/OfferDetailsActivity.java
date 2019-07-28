package app.com.maksab.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.*;
import app.com.maksab.databinding.*;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.*;
import app.com.maksab.view.adapter.*;
import app.com.maksab.view.viewmodel.*;
import com.androidquery.AQuery;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.ArrayList;

public class OfferDetailsActivity extends AppCompatActivity {
	private ActivityOfferDetailsBinding binder;
	private boolean redeemStatus = true;
	private boolean redemptionInstructionsStatus = true;
	private boolean packageStatus = true;
	private Dialog dialogRedeem;
	private DialogRedeemBinding dialogRedeemBinding;
	private DialogImageBinding dialogImageBinding;
	private DialogGiftBinding dialogGiftBinding;
	private ArrayList<String> sliderImagesDialog;
	private String offerName, brandImage, partnerName, redeemedMsg, redeemedStatus, sOfferId, shareOfferLink, callNumber = "";
	private ImageViewPagerAdapter imageViewPagerAdapter;
	private static final int PERMISSIONS_REQUEST_CODE = 11;
	ArrayList<OfferDetailsResponse.VenderLocation> facilityListArrayList;
	private boolean bLoadMore = true;

	private OfferDetailsResponse.OfferDetail offerDetails = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binder = DataBindingUtil.setContentView(this, R.layout.activity_offer_details);
		binder.setActivity(this);

		Bundle bundle = getIntent().getExtras();
		if (!bundle.isEmpty()) {
			Extension extension = Extension.getInstance();
			if (!extension.executeStrategy(OfferDetailsActivity.this, "", ValidationTemplate.INTERNET)) {
				Utility.setNoInternetPopup(OfferDetailsActivity.this);
			} else
				getFavoritePartner(bundle.getString(Constant.OFFER_ID));
		}

       /* DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;*/
		int width = PreferenceConnector.readInteger(OfferDetailsActivity.this,PreferenceConnector.DEVICE_WIDTH,350);
		ViewGroup.LayoutParams params = binder.elSlidesPager.getLayoutParams();
		params.height = width;
		params.width = width;
		binder.elSlidesPager.setLayoutParams(params);
	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", "Done");
		setResult(Activity.RESULT_OK, returnIntent);
		finish();
	}

	public void getFavoritePartner(String offerId) {
		ProgressDialog.getInstance().showProgressDialog(OfferDetailsActivity.this);
		UserCityOfferModel userCityOfferModel = new UserCityOfferModel();
		userCityOfferModel.setCityId(Utility.getCityId(OfferDetailsActivity.this));
		userCityOfferModel.setUserId(Utility.getUserId(getApplicationContext()));
		userCityOfferModel.setLanguage(Utility.getLanguage(getApplicationContext()));
		userCityOfferModel.setOfferId(offerId);
		Api api = APIClient.getClient().create(Api.class);
		final Call<OfferDetailsResponse> responseCall = api.getOfferDetails(userCityOfferModel);
		responseCall.enqueue(new Callback<OfferDetailsResponse>() {
			@Override
			public void onResponse(Call<OfferDetailsResponse> call, retrofit2.Response<OfferDetailsResponse> response) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					handleStoreListResponse(response.body());
				}
			}

			@Override
			public void onFailure(Call<OfferDetailsResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					Utility.showToast(OfferDetailsActivity.this, t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}

	private void handleStoreListResponse(OfferDetailsResponse myResponse) {
		try {
			if (myResponse == null) {
				return;
			}

			String responseCode = myResponse.getResponseCode();
			if (responseCode == null || !responseCode.equals(Api.SUCCESS)) {
				Utility.showToast(OfferDetailsActivity.this, getString(R.string.no_data_found));
				return;
			}

			offerDetails = myResponse.getOfferDetails();
			if (offerDetails != null) {
				Log.i("dragon", offerDetails.getInstructions());

				offerDetails.setDiscoundEnd(getResources().getString(R.string.redeem_offer) + offerDetails.getDiscoundEnd());
				offerDetails.setPartnerOffers(offerDetails.getPartnerOffers() + " " + getResources().getString(R.string.offers));
				offerDetails.setPartnerFavCount(offerDetails.getPartnerFavCount() + " " + getResources().getString(R.string.favorite));

				offerDetails.setDiscountRate(offerDetails.getDiscountRate() + "% " + getResources().getString(R.string.off));

				binder.setModel(offerDetails);

				binder.redemptionInstructions.setText
						(Html.fromHtml(offerDetails.getInstructions()));

				sOfferId = offerDetails.getOfferId();
				callNumber = offerDetails.getCallNumber();
				brandImage = offerDetails.getPartnerImg();
				offerName = offerDetails.getOfferName();
				partnerName = offerDetails.getPartnerName();

				binder.whatCustomerLike.setText(getResources().getString(R.string.what_customer_like) + " " + getResources().getString(R.string.about) + " " + offerDetails.getOfferName());
				redeemedStatus = offerDetails.getRedeemedStatus();
				redeemedMsg = offerDetails.getRedeemedMsg();
				shareOfferLink = offerDetails.getOfferLink();

				if (offerDetails.getSrtPackage().equals(""))
					binder.llSrtPackage.setVisibility(View.GONE);
				else
					binder.llSrtPackage.setVisibility(View.VISIBLE);

				if (offerDetails.getRedeemedMsg().equals("")) {
					binder.llRedemptionInstructions.setVisibility(View.GONE);
				} else {
					binder.llRedemptionInstructions.setVisibility(View.VISIBLE);
				}

				if (callNumber.equals(""))
					binder.llCall.setVisibility(View.GONE);
				else
					binder.llCall.setVisibility(View.VISIBLE);



				if (redeemedStatus.equalsIgnoreCase("1"))
					binder.btnRedeem.setBackgroundResource(R.color.colorPrimaryDark);
				else
					binder.btnRedeem.setBackgroundResource(R.color.gray_dark);

				binder.beforeAmount.setPaintFlags(binder.beforeAmount.getPaintFlags()
						| Paint.STRIKE_THRU_TEXT_FLAG);

				binder.otherOfferFor.setText(getResources().getString(R.string.other_offer_for) + " " + offerDetails.getPartnerName());
				if (offerDetails.getFavStatus().equalsIgnoreCase("1")) {
					binder.bookmark.setBackgroundResource(R.drawable.favorites_hover3x);
					binder.addWishlist.setText(getResources().getString(R.string.added_wishlist));
				} else {
					binder.bookmark.setBackgroundResource(R.drawable.favorites3x);
					binder.addWishlist.setText(getResources().getString(R.string.add_to_wishlist));
				}
				if (offerDetails.getPartnerFavStatus().equalsIgnoreCase("1"))
					binder.favoritesStatusPartner.setBackgroundResource(R.drawable.favorites_hover3x);
				else
					binder.favoritesStatusPartner.setBackgroundResource(R.drawable.favorites3x);

				/*Slider Images*/
				if (offerDetails.getImagesListArrayList() != null && offerDetails.getImagesListArrayList().size() != 0) {
					sliderImagesDialog = offerDetails.getImagesListArrayList();
					setSlider(offerDetails.getImagesListArrayList());
				}

				if (offerDetails.getFacilityListArrayList().size() != 0)
					updateFacilities(offerDetails.getFacilityListArrayList());
				else
					binder.llAvailableFacilities.setVisibility(View.GONE);

				if (offerDetails.getReviewsListArrayList().size() != 0)
					updateReviews(offerDetails.getReviewsListArrayList());
			}

			ArrayList<OfferDetailsResponse.VenderLocation> venderLocations = myResponse.getVenderLocationLists();
			if (venderLocations != null) {
				facilityListArrayList = myResponse.getVenderLocationLists();
			}

			onClickLoadMore();

			if (myResponse.getOtherOfferListArrayList() != null && myResponse.getOtherOfferListArrayList().size()
					!= 0) {
				//binder.llOtherOfferFor.setVisibility(View.VISIBLE);
				updateOtherOffer(myResponse.getOtherOfferListArrayList());
			}// else
			// binder.llOtherOfferFor.setVisibility(View.GONE);
		} catch (Exception e) {
			//  Utility.showToast(OfferDetailsActivity.this, getString(R.string.server_not_response));
			e.printStackTrace();
		}
	}

	private void updateFacilities(ArrayList<OfferDetailsResponse.FacilityList> facilityListArrayList) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				// CategoryHomeResponse.Category categoryList = (CategoryHomeResponse.Category) obj;
			}
		};
		binder.recyclerViewFacilitys.setLayoutManager(new LinearLayoutManager(OfferDetailsActivity.this,
				LinearLayoutManager.HORIZONTAL, false));
		binder.recyclerViewFacilitys.setAdapter(new FacilityAdapter(OfferDetailsActivity.this,
				facilityListArrayList, onItemClickListener));
	}

	private void updateReviews(ArrayList<OfferDetailsResponse.ReviewsList> reviewsListArrayList) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				// CategoryHomeResponse.Category categoryList = (CategoryHomeResponse.Category) obj;
			}
		};
		binder.recyclerViewReviews.setLayoutManager(new GridLayoutManager(OfferDetailsActivity.this, 1));
		binder.recyclerViewReviews.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
		binder.recyclerViewReviews.setAdapter(new ReviewAdapter(OfferDetailsActivity.this, reviewsListArrayList,
				onItemClickListener));
	}

	private void updateOtherOffer(ArrayList<OfferDetailsResponse.OtherOffer> otherOfferListArrayList) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				OfferDetailsResponse.OtherOffer offerList = (OfferDetailsResponse.OtherOffer) obj;
				Intent intent = new Intent(OfferDetailsActivity.this, OfferDetailsActivity.class);
				intent.putExtra(Constant.OFFER_ID, offerList.getOfferId());
				startActivity(intent);
				finish();
				// CategoryHomeResponse.Category categoryList = (CategoryHomeResponse.Category) obj;
			}
		};
		binder.recyclerViewOtherOffer.setLayoutManager(new LinearLayoutManager(OfferDetailsActivity.this,
				LinearLayoutManager

						.HORIZONTAL, false));
		binder.recyclerViewOtherOffer.setAdapter(new OtherOfferAdapter(OfferDetailsActivity.this,
				otherOfferListArrayList, onItemClickListener));
	}

	public void onClickRedeem() {
		if (redeemStatus) {
			redeemStatus = false;
			binder.llRedeem.setVisibility(View.GONE);
			binder.redeemVisibility.setImageResource(R.drawable.ic_add_white);
		} else {
			redeemStatus = true;
			binder.llRedeem.setVisibility(View.VISIBLE);
			binder.redeemVisibility.setImageResource(R.drawable.ic_remove_white);
		}
	}

	public void onClickPackage() {
		if (packageStatus) {
			packageStatus = false;
			binder.srtPackage.setVisibility(View.GONE);
			binder.packageVisibility.setImageResource(R.drawable.ic_add_white);
		} else {
			packageStatus = true;
			binder.srtPackage.setVisibility(View.VISIBLE);
			binder.packageVisibility.setImageResource(R.drawable.ic_remove_white);
		}
	}

	public void onClickRedemptionInstructions() {
		if (redemptionInstructionsStatus) {
			redemptionInstructionsStatus = false;
			binder.redemptionInstructions.setVisibility(View.GONE);
			binder.redemptionInstructionsVisibility.setImageResource(R.drawable.ic_add_white);
		} else {
			redemptionInstructionsStatus = true;
			binder.redemptionInstructions.setVisibility(View.VISIBLE);
			binder.redemptionInstructionsVisibility.setImageResource(R.drawable.ic_remove_white);
		}
	}

	public void onClickFavorites() {
		if (PreferenceConnector.readBoolean(OfferDetailsActivity.this, PreferenceConnector.IS_LOGIN, false)) {
			ProgressDialog.getInstance().showProgressDialog(OfferDetailsActivity.this);
			UserOfferIdModel userOfferIdModel = new UserOfferIdModel();
			userOfferIdModel.setUserId(Utility.getUserId(getApplicationContext()));
			userOfferIdModel.setLanguage(Utility.getLanguage(getApplicationContext()));
			userOfferIdModel.setOfferId(sOfferId);
			Api api = APIClient.getClient().create(Api.class);
			final Call<AddFavoritesResponse> responseCall = api.addOfferInWishlist(userOfferIdModel);
			responseCall.enqueue(new Callback<AddFavoritesResponse>() {
				@Override
				public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
						response) {

					AddFavoritesResponse addFavoritesResponse = response.body();
					if (!isDestroyed()) {
						ProgressDialog.getInstance().dismissDialog();
						Log.e("getFavStatus", response.body().getFavStatus() + "");
						if (response.body().getFavStatus().equalsIgnoreCase("1")) {
							binder.bookmark.setBackgroundResource(R.drawable.favorites_hover3x);
							binder.addWishlist.setText(getResources().getString(R.string.added_wishlist));

						} else {
							binder.bookmark.setBackgroundResource(R.drawable.favorites3x);
							binder.addWishlist.setText(getResources().getString(R.string.add_to_wishlist));
						}
					}
				}

				@Override
				public void onFailure(Call<AddFavoritesResponse> call, Throwable t) {
					ProgressDialog.getInstance().dismissDialog();
					if (!isDestroyed()) {
						Utility.showToast(OfferDetailsActivity.this, t + "");
						Log.e("", "onFailure: " + t.getLocalizedMessage());
					}
				}
			});
		} else {
			showGuestUserAlert();
		}
	}

	public void onClickShareDeal() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, shareOfferLink);
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}

	public void onClickRedeemDialog() {
		if (PreferenceConnector.readBoolean(OfferDetailsActivity.this, PreferenceConnector.IS_LOGIN, false)) {
			if (redeemedStatus.equalsIgnoreCase("1")) {
				dialogRedeem = new Dialog(OfferDetailsActivity.this);
				dialogRedeemBinding = DataBindingUtil.inflate(LayoutInflater.from(OfferDetailsActivity.this), R.layout.dialog_redeem, null, false);
				dialogRedeem.setContentView(dialogRedeemBinding.getRoot());
				dialogRedeem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

				dialogRedeemBinding.secreteCode.requestFocus();
				dialogRedeem.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				AQuery aQuery = new AQuery(dialogRedeemBinding.image);
				aQuery.id(dialogRedeemBinding.image).image(brandImage, true, true, 300, R.drawable.logo_small);

				dialogRedeemBinding.name.setText(offerName);

				dialogRedeemBinding.secretCodeMassage.setText(getResources().getString(R.string.please_ask) + partnerName + getResources()
						.getString(R.string.enter_his_secret_code));

				dialogRedeemBinding.close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialogRedeem.dismiss();
					}
				});

				dialogRedeemBinding.txDone.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						if (dialogRedeemBinding.secreteCode.getText().toString().isEmpty()) {
							dialogRedeemBinding.secreteCode.setError(getText(R.string.secrete_code));
						} else
							getRedeemOffer(dialogRedeemBinding.secreteCode.getText().toString());
					}
				});
				dialogRedeem.show();
			} else {
				showUnsbscribedUserAlert();
			}
		} else {
			showGuestUserAlert();
		}
	}

	public void getRedeemOffer(String redeemCode) {
		ProgressDialog.getInstance().showProgressDialog(OfferDetailsActivity.this);
		RedeemModel redeemModel = new RedeemModel();
		redeemModel.setCityId(Utility.getCityId(OfferDetailsActivity.this));
		redeemModel.setUserId(Utility.getUserId(getApplicationContext()));
		redeemModel.setLanguage(Utility.getLanguage(getApplicationContext()));
		redeemModel.setOfferId(sOfferId);
		redeemModel.setPartnerCode(redeemCode);

		Api api = APIClient.getClient().create(Api.class);
		final Call<RedeemResponse> responseCall = api.redeemOffer(redeemModel);
		responseCall.enqueue(new Callback<RedeemResponse>() {
			@Override
			public void onResponse(Call<RedeemResponse> call, retrofit2.Response<RedeemResponse>
					response) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					if (response.body() != null) {
						if (response.body().getResponseCode().equals(Api.SUCCESS)) {
							Utility.showToast(OfferDetailsActivity.this, response.body().getMessage() + "");
							redeemedStatus = response.body().getRedeemedStatus();
							if (response.body().getRedeemedStatus().equalsIgnoreCase("1")) {
								binder.btnRedeem.setBackgroundResource(R.color.colorPrimary);
							} else {
								binder.btnRedeem.setBackgroundResource(R.color.gray_dark);
							}
							dialogRedeem.dismiss();
							onClickCongratulationDialog(response.body());
						} else {
							Utility.showToast(OfferDetailsActivity.this, response.body().getMessage() + "");
						}
					}

				}
			}

			@Override
			public void onFailure(Call<RedeemResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					Utility.showToast(OfferDetailsActivity.this, t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}

	public void onClickCongratulationDialog(RedeemResponse redeemResponse) {
		final Dialog dialogRedeemSuccess = new Dialog(OfferDetailsActivity.this);
		DialogRedeemSuccessBinding dialogRedeemSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from
						(OfferDetailsActivity.this),
				R.layout.dialog_redeem_success, null, false);
		dialogRedeemSuccess.setContentView(dialogRedeemSuccessBinding.getRoot());
		dialogRedeemSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialogRedeemSuccessBinding.redeemedId.setText(redeemResponse.getRedeemedId());
		dialogRedeemSuccessBinding.saved.setText(redeemResponse.getSaved());
		dialogRedeemSuccessBinding.close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogRedeemSuccess.dismiss();
			}
		});
		dialogRedeemSuccess.show();
	}

	/**
	 * Send As Gift
	 */
	public void giftDialog(String sendGift, String reamningGift) {
		if (PreferenceConnector.readBoolean(OfferDetailsActivity.this, PreferenceConnector.IS_LOGIN, false)) {
			dialogRedeem = new Dialog(OfferDetailsActivity.this);
			dialogGiftBinding = DataBindingUtil.inflate(LayoutInflater.from(OfferDetailsActivity.this),
					R.layout.dialog_gift, null, false);
			dialogRedeem.setContentView(dialogGiftBinding.getRoot());
			dialogRedeem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

			dialogGiftBinding.etName.requestFocus();
			dialogRedeem.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            /*InputMethodManager inputMethodManager =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(LinearLayout.getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);*/

			dialogGiftBinding.giftMassage.setText(getResources().getString(R.string.gift_msg1) + " " + sendGift + " " + getResources
					().getString(R.string.gift_msg2) + " " + reamningGift + " " + getResources().getString(R.string.offer));

			dialogGiftBinding.close.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dialogRedeem.dismiss();
				}
			});

			dialogGiftBinding.btSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (dialogGiftBinding.etName.getText().toString().isEmpty() || dialogGiftBinding.etEmail.getText()
							.toString().isEmpty()) {
						if (dialogGiftBinding.etName.getText().toString().isEmpty()) {
							dialogGiftBinding.nameInputLayout.setError(getText(R.string.name));
						}
						if (dialogGiftBinding.etEmail.getText().toString().isEmpty()) {
							dialogGiftBinding.emailInputLayout.setError(getText(R.string.error_email));
						}
					} else {
						sendGift(dialogGiftBinding.etName.getText().toString(), dialogGiftBinding.etEmail.getText()
								.toString());
					}
				}
			});
			dialogRedeem.show();
		} else {
			showGuestUserAlert();
		}
	}


	public void onClickGiftDialog() {
		ProgressDialog.getInstance().showProgressDialog(OfferDetailsActivity.this);
		UserCityIdModel userCityIdModel = new UserCityIdModel();
		userCityIdModel.setCityId(Utility.getCityId(OfferDetailsActivity.this));
		userCityIdModel.setUserId(Utility.getUserId(getApplicationContext()));
		userCityIdModel.setLanguage(Utility.getLanguage(getApplicationContext()));

		Api api = APIClient.getClient().create(Api.class);
		final Call<GetGiftResponse> responseCall = api.getGiftDetails(userCityIdModel);
		responseCall.enqueue(new Callback<GetGiftResponse>() {
			@Override
			public void onResponse(Call<GetGiftResponse> call, retrofit2.Response<GetGiftResponse>
					response) {
				GetGiftResponse getGiftResponse = response.body();
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {

					// int leftGift = Integer.parseInt(getGiftResponse.getReamningGift());

					if (getGiftResponse.getReamningGift().equalsIgnoreCase("0"))
						Utility.showToast(OfferDetailsActivity.this, getString(R.string.remaining_gift_massage));
					else
						giftDialog(getGiftResponse.getReamningGift(), getGiftResponse.getSentOffer());

				}
			}

			@Override
			public void onFailure(Call<GetGiftResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					Utility.showToast(OfferDetailsActivity.this, t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}


	public void sendGift(String sName, String sEmail) {
		ProgressDialog.getInstance().showProgressDialog(OfferDetailsActivity.this);
		GiftModel giftModel = new GiftModel();
		giftModel.setCityId(Utility.getCityId(OfferDetailsActivity.this));
		giftModel.setUserId(Utility.getUserId(getApplicationContext()));
		giftModel.setLanguage(Utility.getLanguage(getApplicationContext()));
		giftModel.setOfferId(sOfferId);
		giftModel.setEmail(sEmail);
		giftModel.setName(sName);

		Api api = APIClient.getClient().create(Api.class);
		final Call<SuccessfulResponse> responseCall = api.sendGift(giftModel);
		responseCall.enqueue(new Callback<SuccessfulResponse>() {
			@Override
			public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
					response) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					Utility.showToast(OfferDetailsActivity.this, response.body().getMessage() + "");
					dialogRedeem.dismiss();
					//onClickCongratulationDialog();
				}
			}

			@Override
			public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				if (!isDestroyed()) {
					Utility.showToast(OfferDetailsActivity.this, t + "");
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}


	/**
	 * Set recycler view Adapter
	 */
	private void setSlider(ArrayList<String> sliderImagesArrayList) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				onClickImageDialog(position);
			}
		};

		PostPagerAdapter mAdapter = new PostPagerAdapter(OfferDetailsActivity.this, sliderImagesArrayList,
				onItemClickListener);
		binder.imageSlider.setAdapter(mAdapter);
		if (mAdapter.getCount() > 1) {
			binder.indicator.setViewPager(binder.imageSlider);
		}
		mAdapter.notifyDataSetChanged();
	}

	public void onClickImageDialog(int position) {
		dialogRedeem = new Dialog(OfferDetailsActivity.this);
		dialogImageBinding = DataBindingUtil.inflate(LayoutInflater.from(OfferDetailsActivity.this), R.layout.dialog_image, null, false);
		dialogRedeem.setContentView(dialogImageBinding.getRoot());
		dialogRedeem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialogRedeem.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT);

		/*Slider Images*/
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				// onClickImageDialog();
			}
		};
		PostPagerZoomAdapter mAdapter = new PostPagerZoomAdapter(OfferDetailsActivity.this, sliderImagesDialog,
				onItemClickListener);
		dialogImageBinding.imageSlider.setAdapter(mAdapter);
		if (mAdapter.getCount() > 1) {
			dialogImageBinding.indicator.setViewPager(dialogImageBinding.imageSlider);
		}
		mAdapter.notifyDataSetChanged();
		dialogImageBinding.imageSlider.setCurrentItem(position);
        /*if (sliderImagesDialog != null && sliderImagesDialog.size() != 0) {
            imageViewPagerAdapter = new ImageViewPagerAdapter(sliderImagesDialog);
            dialogImageBinding.imageSlider.setOffscreenPageLimit(3);
            dialogImageBinding.imageSlider.setAdapter(imageViewPagerAdapter);
            if (imageViewPagerAdapter.getCount() > 1) {
                dialogImageBinding.indicator.setViewPager(dialogImageBinding.imageSlider);
            }
        }*/

		dialogImageBinding.close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogRedeem.dismiss();
			}
		});
		dialogRedeem.show();
	}

	/**
	 * Show Guest Login Alert
	 */
	private void showGuestUserAlert() {
		Utility.setDialog(OfferDetailsActivity.this, getString(R.string.alert), getString(R.string.guest_login_alert),
				getString(R.string.no), getString(R.string.yes), new DialogListener() {
					@Override
					public void onNegative(DialogInterface dialog) {
						dialog.dismiss();
					}

					@Override
					public void onPositive(DialogInterface dialog) {
						dialog.dismiss();
						Intent intent = new Intent(OfferDetailsActivity.this, IntroActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
				});
	}

	/**
	 * Show Guest Login Alert
	 */
	private void showUnsbscribedUserAlert() {
		new AlertDialog.Builder(this)
				.setTitle(getString(R.string.alert))
				.setMessage(redeemedMsg)
				.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public void onClickCall() {

       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_DENIED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                    new AlertDialog.Builder(OfferDetailsActivity.this)
                            .setTitle("Call Permission")
                            .setMessage("Hi there! We can't call anyone without the call permission, could you please
                            grant it?")
                            .setPositiveButton("Yep", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                    PERMISSIONS_REQUEST_CODE);
                                }
                            })
                            .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(OfferDetailsActivity.this, ":(", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                } else
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CODE);
            } else
            { Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + callNumber));
                startActivity(intent);}
        } else
        { Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + callNumber));
            startActivity(intent);}*/
		try {
			startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callNumber)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		//  startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", callNumber, null)));
        /*requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CODE);

        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+callNumber));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            Utility.showToast(OfferDetailsActivity.this,"Could not find an activity to place the call.");
        }*/
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PERMISSIONS_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) ;
			// makeCall();
           /* Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + callNumber));
            startActivity(intent);*/
		}
	}

	private void makeCall() {

	}

	public void onClickLoadMore() {
		ArrayList<OfferDetailsResponse.VenderLocation> locationLists = new ArrayList<>();
		locationLists.clear();
		if (facilityListArrayList.size() > 2) {
			if (bLoadMore) {
				bLoadMore = false;
				binder.loadMore.setText(getResources().getString(R.string.show_all));
				for (int i = 0; i < 2; i++) {
					locationLists.add(facilityListArrayList.get(i));
				}
			} else {
				bLoadMore = true;
				binder.loadMore.setText(getResources().getString(R.string.show_less));
				locationLists = facilityListArrayList;
			}
		} else {
			locationLists = facilityListArrayList;
		}

		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				OfferDetailsResponse.VenderLocation venderLocation = (OfferDetailsResponse.VenderLocation) obj;
				//startActivity(new Intent(OfferDetailsActivity.this, DirectionActivity.class));
				Intent intent = new Intent(OfferDetailsActivity.this, DirectionActivity.class);
				intent.putExtra(DirectionActivity.LAT, venderLocation.getLatitude());
				intent.putExtra(DirectionActivity.LNG, venderLocation.getLongitude());
				startActivity(intent);

			}
		};
		binder.recyclerViewRedeemOffer.setLayoutManager(new GridLayoutManager(OfferDetailsActivity.this, 1));
		binder.recyclerViewRedeemOffer.setAdapter(new VenderLocationAdapter(OfferDetailsActivity.this, locationLists, onItemClickListener));
	}
}

