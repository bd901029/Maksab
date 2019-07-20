package app.com.maksab.engine;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import app.com.maksab.MyApplication;
import app.com.maksab.R;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.transform.ErrorListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiManager {
	private String TAG = "ApiManager";

	public class PATH {
		public static final String BASE_URL = "https://www.maksabapp.com/develop_api/";
		public static final String GET_COUNTRY_CITY = "api/get_country_city";
	}

	public class Key {
		public static final String Language = "language";

		public static final String ResponseCode = "responseCode";
		public static final String CountryData = "countryData";
	}

	public static String SomethingWentWrong = MyApplication.sharedInstance().getResources().getString(R.string.wrong);
	public static String ServerNotResponse = MyApplication.sharedInstance().getResources().getString(R.string.server_not_response);

	public interface Callback {
		void OnFinished(String message);
	}

	public void runCallback(final Callback callback, final String strError) {
		if (callback == null)
			return;

		Context context = MyApplication.sharedInstance();
		Handler handler = new Handler(context.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				callback.OnFinished(strError);
			}
		});
	}

	public interface ObjectCallback {
		void OnFinished(ArrayList results, String message);
	}

	public void runCallback(ObjectCallback callback, ArrayList results, int messageID) {
		runCallback(callback, results, MyApplication.sharedInstance().getResources().getString(messageID));
	}

	public void runCallback(final ObjectCallback callback, final ArrayList results, final String strError) {
		if (callback == null)
			return;

		Context context = MyApplication.sharedInstance();
		Handler handler = new Handler(context.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				callback.OnFinished(results, strError);
			}
		});
	}

	private RequestQueue requestQueue;
	public RequestQueue getRequestQueue() {
		if (requestQueue == null)
			requestQueue = Volley.newRequestQueue(MyApplication.sharedInstance());
		return requestQueue;
	}

	public void addToRequestQueue(Request request, String tag) {
		request.setTag(tag);
		getRequestQueue().add(request);
	}

	public void cancelAllRequest(String tag) {
		getRequestQueue().cancelAll(tag);
	}

	public String parseVolleyError(VolleyError error) {
		try {
			String responseBody = new String(error.networkResponse.data, "utf-8");
			JSONObject data = new JSONObject(responseBody);
			JSONArray errors = data.getJSONArray("errors");
			JSONObject jsonMessage = errors.getJSONObject(0);
			return jsonMessage.getString("message");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
	}

	public interface JsonCallback {
		void OnFinished(JSONObject result, String message);
	}

	public void runCallback(final JsonCallback callback, final JSONObject result, final String strError) {
		if (callback == null)
			return;

		Context context = MyApplication.sharedInstance();
		Handler handler = new Handler(context.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				callback.OnFinished(result, strError);
			}
		});
	}

	public void call(String path, final Map<String, String> params, final JsonCallback callback) {
		cancelAllRequest(path);

		String fullPath = PATH.BASE_URL + path;
		Response.Listener<String> onResponse = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, response);
				try {
					JSONObject result = new JSONObject(response);
					runCallback(callback, result, null);
				} catch (Exception e) {
					e.printStackTrace();
					runCallback(callback, null, e.getLocalizedMessage());
				}
			}
		};

		Response.ErrorListener onFailure = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				runCallback(callback, null, parseVolleyError(error));
			}
		};

		StringRequest request = new StringRequest(Request.Method.POST, fullPath, onResponse, onFailure) {
			@Override
			protected  Map<String, String> getParams() {
				return params;
			}
		};
		addToRequestQueue(request, path);
	}
}