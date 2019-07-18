package app.com.maksab.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import app.com.maksab.MyApplication;
import app.com.maksab.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

public class Helper {
	private static Helper instance = null;
	public static Helper SharedInstance() {
		if (instance == null)
			instance = new Helper();
		return instance;
	}

	public static boolean SDK_SUPPORTED = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

	public static int RESULT_LOAD_IMAGE = 0x1029;
	public static final int REQUEST_CAMERA_PERMISSION = 1;

	public static String TemporaryFolderPath = MyApplication.sharedInstance().getExternalFilesDir(null).getPath();

	public static void showErrorMessage(Context context, String sMsg) {
		new AlertDialog.Builder(context)
				.setTitle("Error")
				.setMessage(sMsg)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public static void showMessage(Context context, String sTitle, String sMsg) {
		new AlertDialog.Builder(context)
				.setTitle(sTitle)
				.setMessage(sMsg)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public static void showToast(Context context, int messageID) {
		Toast.makeText(context, context.getResources().getString(messageID), Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showErrorToast(Context context, int messageID) {
		Toast.makeText(context, context.getResources().getString(messageID), Toast.LENGTH_LONG).show();
	}

	public static void showErrorToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showProgress(Context context) {
		hideProgress();

		app.com.maksab.util.ProgressDialog.getInstance().showProgressDialog(context);
	}

	public static void hideProgress() {
		app.com.maksab.util.ProgressDialog.getInstance().dismissDialog();
	}

	public static void hideSoftKeyboard(Context context) {
		if(((Activity)context).getCurrentFocus()!=null) {
			InputMethodManager inputMethodManager = (InputMethodManager) ((Activity)context).getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 0);
		}
	}

	public static void showSoftKeyboard(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) ((Activity)context).getSystemService(Context.INPUT_METHOD_SERVICE);
		view.requestFocus();
		inputMethodManager.showSoftInput(view, 0);
	}

	private static boolean isImmersiveAvailable(Context context) {
		return Build.VERSION.SDK_INT >= 19;
	}

	// This snippet hides the system bars.
	public static void hideSystemUI(Context context) {
		if (!isImmersiveAvailable(context))
			return;

		// Set the IMMERSIVE flag.
		// Set the content to appear under the system bars so that the content
		// doesn't resize when the system bars hide and show.
		((Activity)context).getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
						| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
						| View.SYSTEM_UI_FLAG_IMMERSIVE);
	}

	// This snippet shows the system bars. It does this by removing all the flags
	// except for the ones that make the content appear under the system bars.
	public static void showSystemUI(Context context) {
		if (!isImmersiveAvailable(context))
			return;

		((Activity)context).getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}

	public static void hideSystemUIPermanently(final Context context) {
		hideSystemUI(context);

		((Activity)context).getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				hideSystemUI(context);
			}
		});
	}

	public static boolean isDataConnectionAvailable() {
		Context context = MyApplication.sharedInstance();
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info == null)
			return false;
		return connectivityManager.getActiveNetworkInfo().isConnected();
	}

	public static void setLanguage(String localeString) {
		Locale locale = new Locale(localeString);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;

		Context context = MyApplication.sharedInstance();
		context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}

	public static void requestStoragePermission(final Activity activity) {
		if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
			// Provide an additional rationale to the user if the permission was not granted
			// and the user would benefit from additional context for the use of the permission.
			// For example if the user has previously denied the permission.
			new AlertDialog.Builder(activity)
					.setTitle("Permission Request")
					.setMessage("Requesting Permission to Access Internet")
					.setCancelable(false)
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//re-request
							ActivityCompat.requestPermissions(activity,
									new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
									0);
						}
					})
					.setIcon(R.drawable.ic_launcher)
					.show();
		} else {
			// READ_PHONE_STATE permission has not been granted yet. Request it directly.
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
		}
	}

	public static boolean checkAndRequestCameraPermission(Activity activity) {
		if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) !=	PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
			return false;
		}

		return true;
	}

	public static void writeByteToFile(byte[] bytes, String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(bytes);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getUniqueIMEIId() {
		Context context = MyApplication.sharedInstance();
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				return "";
			}

			String imei = telephonyManager.getDeviceId();
			if (imei != null && !imei.isEmpty()) {
				return imei;
			} else {
				return android.os.Build.SERIAL;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "not_found";
	}

	public static boolean isNumeric(String strValue) {
		if (strValue == null || strValue.isEmpty())
			return false;
		return strValue.matches("-?\\d+(\\.\\d+)?");
	}

	public static int randomColor() {
		int red = (int)(Math.random()*1000%256);
		int green = (int)(Math.random()*1000%256);
		int blue = (int)(Math.random()*1000%256);
		return Color.rgb(red, green, blue);
	}
}
