package app.com.maksab.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.maksab.R;
import app.com.maksab.databinding.ActivityDirectionBinding;
import app.com.maksab.util.DirectionsJSONParser;
import app.com.maksab.util.Extension;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DirectionActivity extends FragmentActivity implements OnMapReadyCallback,
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener {
	public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
	GoogleApiClient mGoogleApiClient;
	SupportMapFragment mapFragment;

	ActivityDirectionBinding binder;
	GoogleApiClient googleApiClient;
	GoogleMap mGoogleMap;
	LatLng targetLocation;
	public static String LAT = "latitude";
	public static String LNG = "longitude";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binder = DataBindingUtil.setContentView(this, R.layout.activity_direction);
		binder.setActivity(this);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			targetLocation = new LatLng(Double.parseDouble(bundle.getString(LAT)),Double.parseDouble(bundle.getString(LNG)));
		} else {
			//Utility.showToast(this, getString(R.string.wrong));
		}

		Extension extension = Extension.getInstance();
		if (!extension.executeStrategy(DirectionActivity.this, "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(DirectionActivity.this);
		}

		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.driver_location_map);
		mapFragment.getMapAsync(this);

		googleApiClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.build();

		enableLocationAccess();
	}

	@Override
	protected void onStart() {
		super.onStart();
		buildGoogleApiClient();
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		ArrayList<LatLng> markers = new ArrayList<>();

		this.mGoogleMap = googleMap;

		if (targetLocation != null) {
			googleMap.addMarker(new MarkerOptions().position(targetLocation)
					.title(" ")
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(targetLocation.latitude, targetLocation.longitude), 15));
		}
	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnected(Bundle bundle) {
		if (enableLocationAccess()) {
			mapFragment.getMapAsync(this);
		}
	}

	@Override
	public void onConnectionSuspended(int i) {
	}



	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	}


	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";
		String mode = "mode=driving";
		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters
				+"&key=AIzaSyAaVM-cj3_EG6u0COFHdfTmu4wFuk1RCwQ";

		return url;
	}

	/**
	 * A method to download json data from url
	 */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.connect();

			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	private class DownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... url) {

			String data = "";

			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();
			parserTask.execute(result);

		}
	}

	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();
			try {
				for (int i = 0; i < result.size(); i++) {
					points = new ArrayList();
					lineOptions = new PolylineOptions();

					List<HashMap<String, String>> path = result.get(i);

					for (int j = 0; j < path.size(); j++) {
						HashMap<String, String> point = path.get(j);

						double lat = Double.parseDouble(point.get("lat"));
						double lng = Double.parseDouble(point.get("lng"));
						LatLng position = new LatLng(lat, lng);

						points.add(position);
					}

					lineOptions.addAll(points);
					lineOptions.width(12);
					lineOptions.color(Color.BLUE);
					lineOptions.geodesic(true);

				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			// Drawing polyline in the Google Map for the i-th route
			if (lineOptions != null)
				mGoogleMap.addPolyline(lineOptions);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_LOCATION: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// permission was granted. Do the
					// contacts-related task you need to do.
					if (ContextCompat.checkSelfPermission(this,
							Manifest.permission.ACCESS_FINE_LOCATION)
							== PackageManager.PERMISSION_GRANTED) {
						if (mGoogleApiClient == null) {
							buildGoogleApiClient();
						}
						mGoogleMap.setMyLocationEnabled(true);
					}
				} else {
					Toast.makeText(this, "permission denied",
							Toast.LENGTH_LONG).show();
				}
				return;
			}
		}
	}

	private boolean enableLocationAccess() {
		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, android.Manifest	.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission
					.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, Utility.REQUEST_CODE_LOCATION);
			return false;
		}

		try {
			mGoogleMap.setMyLocationEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
}