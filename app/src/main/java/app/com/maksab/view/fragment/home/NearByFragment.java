package app.com.maksab.view.fragment.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.NearByResponse;
import app.com.maksab.databinding.DialogNotificationFilterBinding;
import app.com.maksab.engine.category.Category;
import app.com.maksab.engine.category.CategoryHomeResponse;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;
import app.com.maksab.util.*;
import app.com.maksab.view.activity.BrandDetailActivity;
import app.com.maksab.view.activity.HomeActivity;
import app.com.maksab.view.adapter.NearbyFilterAdapter;
import app.com.maksab.view.viewmodel.GetStoreListModel;
import app.com.maksab.view.viewmodel.LatLngModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NearByFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback,
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener, LocationListener {

	private Double Latitude = 0.00;
	private Double Longitude = 0.00;
	private final static String STORE_TYPE_ID = "store_type_id";
	private String storeTypeId = "";
	private Dialog dialogNotificationFilter;
	private DialogNotificationFilterBinding recyclerViewBinding;
	ArrayList<String> selectNotification = new ArrayList<String>();
	TextView notificationView;
	GoogleApiClient mGoogleApiClient;
	Location mCurrentLocation = null;
	LocationRequest mLocationRequest;
	private GoogleMap mMap = null;
	private boolean firstTime = true;
	private boolean shouldUpdateLocations = true;
	private SupportMapFragment mapFragment;
	private boolean selectStatus = true;
	CategoryHomeResponse categoryHomeResponse;
	ArrayList<Category> categories;

	public NearByFragment() {
		// Required empty public constructor
	}

	public static NearByFragment newInstance(String storeTypeId) {
		Bundle args = new Bundle();
		NearByFragment fragment = new NearByFragment();
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
	public void onStart() {
		super.onStart();

		if (!isLocationEnabled()) {
			showLocationAlert();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_near_by, container, false);

		ImageView nearByFilterView = (ImageView) view.findViewById(R.id.nearByFilter);
		nearByFilterView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickNearByFilter(v);
			}
		});

		notificationView = (TextView) view.findViewById(R.id.txNotification);
		FragmentManager fm = getChildFragmentManager();

		mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container_near_by);
		mapFragment.getMapAsync(this);

		Extension extension = Extension.getInstance();
		if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(getActivity());
		} else {
			buildGoogleApiClient();
		}

		categoryHomeResponse = ((HomeActivity) getActivity()).categoryResponse;
		categories = categoryHomeResponse.categories;
		for (int i = 0; categories.size() > i; i++) {
			categories.get(i).status = "1";
			selectNotification.add(categories.get(i).id);
		}

		return view;
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (!TextUtils.isEmpty(storeTypeId)) {
			GetStoreListModel getStoreListModel = new GetStoreListModel();
			getStoreListModel.setStoreTypeId(storeTypeId);
		}

		enableLocationAccess();
	}

	private boolean enableLocationAccess() {
		if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission
					.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, Utility.REQUEST_CODE_LOCATION);
			return false;
		}

		return true;
	}

	public void setMyLocationEnabled() {
		if (mMap == null) {
			return;
		}

		try {
			mMap.setMyLocationEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getNearByBrand() {
		if (mCurrentLocation == null) {
			Utility.showToast(getActivity(), "No location find");
			return;
		}

		LatLngModel latLngModel = new LatLngModel();
		latLngModel.setLatitude(mCurrentLocation.getLatitude() + "");
		latLngModel.setLongitude(mCurrentLocation.getLongitude() + "");
		latLngModel.setLanguage(Utility.getLanguage(getActivity()));
		latLngModel.setCategoryId(selectNotification);

		Api api = APIClient.getClient().create(Api.class);
		final Call<NearByResponse> responseCall = api.getNearByBrand(latLngModel);
		responseCall.enqueue(new Callback<NearByResponse>() {
			@Override
			public void onResponse(Call<NearByResponse> call, Response<NearByResponse> response) {
				ProgressDialog.getInstance().dismissDialog();
				if (getActivity() != null && isVisible()) {
					handleStoreListResponse(response.body());
				}
			}

			@Override
			public void onFailure(Call<NearByResponse> call, Throwable throwable) {
				ProgressDialog.getInstance().dismissDialog();
				if (getActivity() != null && isVisible()) {
					Utility.showToast(getActivity(), throwable + "");
					Log.e("", "onFailure: " + throwable.getLocalizedMessage());
				}
			}
		});
	}

	private void handleStoreListResponse(NearByResponse myResponse) {
		try {
			if (myResponse != null) {
				if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
					if (myResponse.getBrandDataArrayList() != null && myResponse.getBrandDataArrayList().size() != 0) {
						//brandDataArrayList = myResponse.getBrandDataArrayList();
						((HomeActivity) getActivity()).onFirstVisible(true);
						updateBrands(myResponse.getBrandDataArrayList());
					}
				} else {
					// Utility.showToast(getActivity(), myResponse.getMessage());
				}
			}
		} catch (Exception e) {
			Utility.showToast(getActivity(), getString(R.string.server_not_response));
			e.printStackTrace();
		}
	}

	private void updateBrands(ArrayList<NearByResponse.BrandData> brandDataArrayList) {
		// *** Marker (Loop)
		for (int i = 0; i < brandDataArrayList.size(); i++) {
			Latitude = Double.parseDouble(brandDataArrayList.get(i).getLatitude());
			Longitude = Double.parseDouble(brandDataArrayList.get(i).getLongitude());
			//String name = brandDataArrayList.get(i).getBrandName();
			//String sId = brandDataArrayList.get(i).getBrandId();
			try {
				//set Brand img
				Bitmap bmImg2 = Ion.with(getActivity()).load(brandDataArrayList.get(i).getBrandImg())
						.withBitmap()
						.resize(120, 120)
						.asBitmap().get();
				brandDataArrayList.get(i).setBrandImg(BitMapToString(bmImg2));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			String brandData = new Gson().toJson(brandDataArrayList.get(i));
			MarkerOptions marker = new MarkerOptions()
					.position(new LatLng(Latitude, Longitude))
					.title(brandDataArrayList.get(i).getBrandId())
					.snippet(brandData);
			try {
				//set Map Marker img
				Bitmap bmImg = Ion.with(getActivity()).load(brandDataArrayList.get(i).getMapMarker())
						.withBitmap()
						.resize(75, 75)
						.asBitmap().get();
				marker.icon(BitmapDescriptorFactory.fromBitmap(bmImg));
				marker.position(new LatLng(Latitude, Longitude));

				mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(brandDataArrayList.get(i)));
				mMap.addMarker(marker);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		if (firstTime) {
			ProgressDialog.getInstance().showProgressDialog(getActivity());
			firstTime = false;
		}

		mMap = googleMap;
		mMap.setPadding(0, 0, 30, 105);
		//mMap.getUiSettings().setMyLocationButtonEnabled(true);

		if (shouldUpdateLocations && mCurrentLocation != null) {
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 16));
			shouldUpdateLocations = false;
			try {
				Extension extension = Extension.getInstance();
				if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
					Utility.setNoInternetPopup(getActivity());
				} else {
					getNearByBrand();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			googleMap.setMyLocationEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set a listener for info window events.
		mMap.setOnInfoWindowClickListener(this);
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent intent = new Intent(getActivity(), BrandDetailActivity.class);
		intent.putExtra(Constant.BRAND_ID, marker.getTitle());
		startActivity(intent);
		marker.hideInfoWindow();
	}

	public String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	public void onClickNearByFilter(View v) {
		if (getActivity() != null && isVisible()) {
			dialogNotificationFilter = new Dialog(getActivity());
			recyclerViewBinding = DataBindingUtil.inflate(LayoutInflater.from
							(getActivity()),
					R.layout.dialog_notification_filter, null, false);
			dialogNotificationFilter.setContentView(recyclerViewBinding.getRoot());
			dialogNotificationFilter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			recyclerViewBinding.progressBar.setVisibility(View.GONE);
			setRecyclerViewNotificationFilter();
			recyclerViewBinding.selectAll.setText(getString(R.string.unselect_all));

			for (int i = 0; categories.size() > i; i++) {
				if (categories.get(i).status.equalsIgnoreCase("0")) {
					recyclerViewBinding.selectAll.setText(getString(R.string.select_all));
				}
			}

			recyclerViewBinding.txCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dialogNotificationFilter.dismiss();
				}
			});

			recyclerViewBinding.txDone.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (mMap != null)
						mMap.clear();
					firstTime = true;
					shouldUpdateLocations = true;
					dialogNotificationFilter.dismiss();
					Extension extension = Extension.getInstance();
					if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
						Utility.setNoInternetPopup(getActivity());
					} else
						buildGoogleApiClient();
				}
			});

			recyclerViewBinding.selectAll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (categories != null && categories.size() != 0) {
						selectNotification.clear();
						if (selectStatus) {
							for (int i = 0; categories.size() > i; i++) {
								if (categories.get(i).status.equalsIgnoreCase("0")) {
									categories.get(i).status = "1";
								}
							}
							selectStatus = false;
							recyclerViewBinding.selectAll.setText(getString(R.string.unselect_all));
							setRecyclerViewNotificationFilter();
						} else {
							for (int i = 0; categories.size() > i; i++) {
								if (categories.get(i).status.equalsIgnoreCase("1")) {
									categories.get(i).status = "0";
								}
							}
							selectStatus = true;
							recyclerViewBinding.selectAll.setText(getString(R.string.select_all));
							setRecyclerViewNotificationFilter();
						}
					}
				}
			});
			dialogNotificationFilter.show();
		}
	}

	private void setRecyclerViewNotificationFilter() {
		OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener = new OnItemClickCheckUnCheckListener() {

			@Override
			public void onAdd(int id, Object obj) {
				Category category = (Category) obj;
				category.status = "1";
				selectNotification.add(category.id);
			}

			@Override
			public void onRemove(int id, Object obj) {
				Category category = (Category) obj;
				category.setStatus("0");
				selectNotification.remove(category.id);
			}
		};
		recyclerViewBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
		recyclerViewBinding.recyclerView.setAdapter(new NearbyFilterAdapter(getActivity(), categories, onItemClickCheckUnCheckListener));
	}

	@Override
	public void onStop() {
		super.onStop();
		// stop GoogleApiClient
		try {
			if (mGoogleApiClient.isConnected()) {
				mGoogleApiClient.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		if (!mGoogleApiClient.isConnected()) {
			mGoogleApiClient.connect();
		}
		super.onResume();
	}

	public synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnected(Bundle bundle) {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(12000);
		mLocationRequest.setFastestInterval(12000);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		try {
			if (ContextCompat.checkSelfPermission(getActivity(),
					Manifest.permission.ACCESS_FINE_LOCATION)
					== PackageManager.PERMISSION_GRANTED) {
				LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionSuspended(int i) {
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onLocationChanged(Location location) {
        /*if (mMap != null)
            mMap.clear();*/
		mCurrentLocation = location;
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	public void showLocationAlert() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("Enable Location")
				.setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
						"use this app")
				.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {

						Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(myIntent);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {

					}
				});
		dialog.show();
	}

	private boolean isLocationEnabled() {
		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
				locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	/**
	 * Demonstrates customizing the info window and/or its contents.
	 */
	class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
		// These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
		private final View mWindow;
		private final View mContents;
		private NearByResponse.BrandData brandData;

		CustomInfoWindowAdapter(NearByResponse.BrandData brandData) {
			this.brandData = brandData;
			mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
			mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
		}

		@Override
		public View getInfoWindow(Marker marker) {
			render(marker, mWindow);
			return mWindow;
		}

		@Override
		public View getInfoContents(Marker marker) {
			render(marker, mContents);
			return mContents;
		}

		private void render(Marker marker, View view) {
			try {
				JSONObject json2 = new JSONObject(marker.getSnippet());
				((ImageView) view.findViewById(R.id.badge)).setImageBitmap(StringToBitMap((String) json2.get("brand_img")
						.toString()));
				TextView titleUi = ((TextView) view.findViewById(R.id.title));
				if (json2.get("brand_name").toString() != null) {
					// Spannable string allows us to edit the formatting of the text.
					SpannableString titleText = new SpannableString(json2.get("brand_name").toString());
					titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
					titleUi.setText("\u200e"+titleText);
				} else {
					titleUi.setText("");
				}
			} catch (Throwable t) {
			}
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
	}
}

