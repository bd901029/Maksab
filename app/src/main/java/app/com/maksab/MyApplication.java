package app.com.maksab;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import app.com.maksab.util.Utility;

public class MyApplication extends MultiDexApplication {

	static MyApplication sharedInstance = null;
	public static synchronized MyApplication sharedInstance() {
		return sharedInstance;
	}

	@Override
	public void onCreate() {
		sharedInstance = this;
		super.onCreate();

		Log.i("dragon", "Device Token: " + Utility.getDeviceToken());
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
