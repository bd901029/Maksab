package app.com.maksab.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AddFavoritesResponse;
import app.com.maksab.api.dao.BrandDetailsResponse;
import app.com.maksab.databinding.ActivityBrandDetailsBinding;
import app.com.maksab.databinding.DialogImageBinding;
import app.com.maksab.databinding.DialogSingleImageBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.BrandLocationAdapter;
import app.com.maksab.view.adapter.BrandOfferListAdapter;
import app.com.maksab.view.adapter.FacilityBrandAdapter;
import app.com.maksab.view.adapter.ImageViewPagerAdapter;
import app.com.maksab.view.adapter.PostPagerAdapter;
import app.com.maksab.view.adapter.PostPagerZoomAdapter;
import app.com.maksab.view.viewmodel.UserCityPartnerModel;
import app.com.maksab.view.viewmodel.UserPartnerIdModel;
import retrofit2.Call;
import retrofit2.Callback;

import android.Manifest;

public class BrandDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityBrandDetailsBinding activityBinding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private DialogImageBinding dialogImageBinding;
    private DialogSingleImageBinding dialogSingleImageBinding;
    private Dialog dialogRedeem;
    private String sOfferId = "", sMenuFileDownload = "";
    private ArrayList<String> sliderImagesDialog;
    private ImageViewPagerAdapter imageViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_brand_details);
        activityBinding.setActivity(this);
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            Extension extension = Extension.getInstance();
            if (!extension.executeStrategy(BrandDetailActivity.this, "", ValidationTemplate.INTERNET)) {
                Utility.setNoInternetPopup(BrandDetailActivity.this);
            } else
                getFavoritePartner(bundle.getString(Constant.BRAND_ID));
        }


        int width = PreferenceConnector.readInteger(BrandDetailActivity.this,PreferenceConnector.DEVICE_WIDTH,350);
        ViewGroup.LayoutParams params = activityBinding.elSlidesPager.getLayoutParams();
        params.height = width;
        params.width = width;
        activityBinding.elSlidesPager.setLayoutParams(params);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "Done");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void getFavoritePartner(String brandId) {
        ProgressDialog.getInstance().showProgressDialog(BrandDetailActivity.this);
        UserCityPartnerModel userCityPartnerModel = new UserCityPartnerModel();
        userCityPartnerModel.setCityId(Utility.getCity(BrandDetailActivity.this));
        userCityPartnerModel.setUserId(Utility.getUserId(getApplicationContext()));
        userCityPartnerModel.setLanguage(Utility.getLanguage(getApplicationContext()));
        userCityPartnerModel.setPartnerId(brandId);
        Api api = APIClient.getClient().create(Api.class);
        final Call<BrandDetailsResponse> responseCall = api.getBrandDetails(userCityPartnerModel);
        responseCall.enqueue(new Callback<BrandDetailsResponse>() {
            @Override
            public void onResponse(Call<BrandDetailsResponse> call, retrofit2.Response<BrandDetailsResponse>
                    response) {
                if (!isDestroyed()) {
                    ProgressDialog.getInstance().dismissDialog();
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<BrandDetailsResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()) {
                    Utility.showToast(BrandDetailActivity.this, t + "");
                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                }
            }
        });
    }

    /**Handle category list response
     * @param myResponse @CategoryListResponse object*/
    private void handleStoreListResponse(BrandDetailsResponse myResponse) {
        try {
            if (myResponse != null) {
                if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    if (myResponse.getBrandDetails() != null) {
                        activityBinding.setModel(myResponse.getBrandDetails());
                        activityBinding.offerBy.setText(getResources().getString(R.string.offers_by) + " " + myResponse.getBrandDetails().getBrandName());
                        sOfferId = myResponse.getBrandDetails().getBrandId();
                        if (TextUtils.isEmpty(myResponse.getBrandDetails().getMenuFileDownload())) {
                            activityBinding.viewMenu.setVisibility(View.GONE);
                        } else {
                            activityBinding.viewMenu.setVisibility(View.VISIBLE);
                            sMenuFileDownload = myResponse.getBrandDetails().getMenuFileDownload();
                        }

                        activityBinding.brandDescription.setText(Html.fromHtml(myResponse.getBrandDetails()
                                .getBrandDescription()));

                        if (myResponse.getBrandDetails().getFavStatus().equalsIgnoreCase("1")) {
                            activityBinding.addWishlist.setText(getResources().getString(R.string.added_wishlist));
                        } else {
                            activityBinding.addWishlist.setText(getResources().getString(R.string.add_to_wishlist));
                        }

                          /*Slider Images*/
                        if (myResponse.getBrandDetails().getImagesListArrayList() != null && myResponse.getBrandDetails()
                                .getImagesListArrayList().size() != 0) {
                            sliderImagesDialog = myResponse.getBrandDetails().getImagesListArrayList();
                            setSlider(myResponse.getBrandDetails().getImagesListArrayList());
                        }

                        if (myResponse.getBrandDetails().getFacilityListArrayList().size() != 0)
                            setRecyclerViewFacilities(myResponse.getBrandDetails().getFacilityListArrayList());
                        else
                            activityBinding.llAvailableFacilities.setVisibility(View.GONE);


                        if (myResponse.getBrandDetails().getOfferListArrayList().size() != 0)
                            setRecyclerViewOffer(myResponse.getBrandDetails().getOfferListArrayList());
                        /*else
                            activityBinding.llOfferBy.setVisibility(View.GONE);*/

                        if (myResponse.getBrandDetails().getLocationListArrayList().size() != 0)
                            setRecyclerViewLocations(myResponse.getBrandDetails().getLocationListArrayList());
                    }
                } else {
                    Utility.showToast(BrandDetailActivity.this, getString(R.string.wrong));
                }
            }
        } catch (Exception e) {
            Utility.showToast(BrandDetailActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }

    /**Set recycler view Adapter */
    private void setRecyclerViewFacilities(ArrayList<BrandDetailsResponse.FacilityList> facilityListArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {}
        };
        activityBinding.recyclerViewFacilitys.setLayoutManager(new LinearLayoutManager(BrandDetailActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        activityBinding.recyclerViewFacilitys.setAdapter(new FacilityBrandAdapter(BrandDetailActivity.this,
                facilityListArrayList, onItemClickListener));
    }

    /** Set recycler view Adapter*/
    private void setRecyclerViewOffer(ArrayList<BrandDetailsResponse.OfferList> otherOfferListArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                BrandDetailsResponse.OfferList offerList = (BrandDetailsResponse.OfferList) obj;
                Intent intent = new Intent(BrandDetailActivity.this, OfferDetailsActivity.class);
                intent.putExtra(Constant.OFFER_ID, offerList.getOfferId());
                startActivity(intent);
            }
        };
        activityBinding.recyclerViewOffers.setLayoutManager(new GridLayoutManager(BrandDetailActivity.this, 1));
        activityBinding.recyclerViewOffers.setAdapter(new BrandOfferListAdapter(BrandDetailActivity.this,
                otherOfferListArrayList, onItemClickListener));
    }

    /**Set Reviews recycler view Adapter */
    private void setRecyclerViewLocations(ArrayList<BrandDetailsResponse.LocationList> locationListArrayList) {
        for (int i = 0; locationListArrayList.size() <= i; i++) {
            locationListArrayList.get(i).setCenter(new LatLng(Double.parseDouble(locationListArrayList.get(i).getLatitude
                    ()), Double.parseDouble(locationListArrayList.get(i).getLongitude())));
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
            }
        };
        activityBinding.recyclerViewLocations.setLayoutManager(new GridLayoutManager(BrandDetailActivity.this, 1));
        activityBinding.recyclerViewLocations.setAdapter(new BrandLocationAdapter(BrandDetailActivity.this,
                locationListArrayList, onItemClickListener));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }
        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    mMap.setMinZoomPreference(12);
                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));
                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                }
            };

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

        PostPagerAdapter mAdapter = new PostPagerAdapter(BrandDetailActivity.this, sliderImagesArrayList,
                onItemClickListener);
        activityBinding.imageSlider.setAdapter(mAdapter);
        activityBinding.imgCount.setText(mAdapter.getCount()+"");
        if (mAdapter.getCount() > 1) {
            activityBinding.indicator.setViewPager(activityBinding.imageSlider);
        }
        mAdapter.notifyDataSetChanged();
        activityBinding.progressBar2.setVisibility(View.GONE);
    }

    public void onClickImageDialog(int position) {
        dialogRedeem = new Dialog(BrandDetailActivity.this);
        dialogImageBinding = DataBindingUtil.inflate(LayoutInflater.from(BrandDetailActivity.this),
                R.layout.dialog_image, null, false);
        dialogRedeem.setContentView(dialogImageBinding.getRoot());
        dialogRedeem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogRedeem.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

         /*Slider Images*/
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
            }
        };

        if (sliderImagesDialog != null && sliderImagesDialog.size() != 0) {
            PostPagerZoomAdapter mAdapter = new PostPagerZoomAdapter(BrandDetailActivity.this, sliderImagesDialog,
                    onItemClickListener);
            dialogImageBinding.imageSlider.setAdapter(mAdapter);
            if (mAdapter.getCount() > 1) {
                dialogImageBinding.indicator.setViewPager(dialogImageBinding.imageSlider);
            }
            mAdapter.notifyDataSetChanged();
            dialogImageBinding.imageSlider.setCurrentItem(position);
        }

        dialogImageBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRedeem.dismiss();
            }
        });
        dialogRedeem.show();
    }

    public void onClickViewMenu() {
        String filenameArray[] = sMenuFileDownload.split("\\.");
        String extension = filenameArray[filenameArray.length - 1];
        if (extension.equalsIgnoreCase("jpg")) {
            ViewMenu();
        }
        if (extension.equalsIgnoreCase("pdf")) {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sMenuFileDownload));
                startActivity(browserIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickFavorites() {
        if (PreferenceConnector.readBoolean(BrandDetailActivity.this, PreferenceConnector.IS_LOGIN, false)) {
            ProgressDialog.getInstance().showProgressDialog(BrandDetailActivity.this);
            UserPartnerIdModel userPartnerIdModel = new UserPartnerIdModel();
            userPartnerIdModel.setUserId(Utility.getUserId(getApplicationContext()));
            userPartnerIdModel.setLanguage(Utility.getLanguage(getApplicationContext()));
            userPartnerIdModel.setPartnerId(sOfferId);

            Api api = APIClient.getClient().create(Api.class);
            final Call<AddFavoritesResponse> responseCall = api.addPartnerInWishlist(userPartnerIdModel);
            responseCall.enqueue(new Callback<AddFavoritesResponse>() {
                @Override
                public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
                        response) {
                    if (!isDestroyed()) {
                        ProgressDialog.getInstance().dismissDialog();
                        if (response.body() != null) {
                            if (response.body().getResponseCode() != null && response.body().getResponseCode().equals(Api
                                    .SUCCESS)) {
                                if (response.body().getFavStatus().equalsIgnoreCase("1")) {
                                    activityBinding.addWishlist.setText(getResources().getString(R.string.added_wishlist));
                                } else {
                                    activityBinding.addWishlist.setText(getResources().getString(R.string.add_to_wishlist));
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddFavoritesResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        Utility.showToast(BrandDetailActivity.this, t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        } else {
            userGuestDialog();
        }
    }

    /**
     * Show Guest Login Alert
     */
    private void userGuestDialog() {
        Utility.setDialog(BrandDetailActivity.this, getString(R.string.alert), getString(R.string.guest_login_alert),
                getString(R.string.no), getString(R.string.yes), new DialogListener() {
                    @Override
                    public void onNegative(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onPositive(DialogInterface dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent(BrandDetailActivity.this, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    public void ViewMenu() {
        dialogRedeem = new Dialog(BrandDetailActivity.this);
        dialogSingleImageBinding = DataBindingUtil.inflate(LayoutInflater.from(BrandDetailActivity.this),
                R.layout.dialog_single_image, null, false);
        dialogRedeem.setContentView(dialogSingleImageBinding.getRoot());
        dialogRedeem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogRedeem.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        AQuery aQuery = new AQuery(dialogSingleImageBinding.image);
        aQuery.id(dialogSingleImageBinding.image).image(sMenuFileDownload, true, true, 300, R.drawable.logo_small);

        dialogSingleImageBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRedeem.dismiss();
            }
        });
        dialogRedeem.show();
    }
}

