package app.com.maksab;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {

	static MyApplication sharedInstance = null;
	public static synchronized MyApplication sharedInstance() {
		return sharedInstance;
	}

	@Override
	public void onCreate() {
		sharedInstance = this;
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
