package app.com.maksab;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class MyApplication extends Application {

	static MyApplication sharedInstance = null;
	public static synchronized MyApplication sharedInstance() {
		return sharedInstance;
	}

	@Override
	public void onCreate() {
		sharedInstance = this;
		super.onCreate();
	}

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
