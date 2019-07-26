package app.com.maksab.util;

import android.util.Log;
import app.com.maksab.MyApplication;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageUtil {
	private static LanguageUtil instance = null;
	public static LanguageUtil sharedInstance() {
		if (instance == null) {
			instance = new LanguageUtil();
		}
		return instance;
	}

	public static final String ARABIC = "ar";
	public static final String ENGLISH = "en";
	public static final String TURKYCE = "tu";

	private String TAG = "LanguageUtil";

	public static ArrayList<String> Languages = new ArrayList<String>() {{
		add(ARABIC);
		add(ENGLISH);
		add(TURKYCE);
	}};

	String language = "";

	LanguageUtil() {
		loadLanguage();
	}

	public static String currentLanguage() {
		return LanguageUtil.sharedInstance().getLanguage();
	}

	private void loadLanguage() {
		String prevLanguage = PreferenceConnector.readString(MyApplication.sharedInstance(), PreferenceConnector.APP_LANGUAGE, "");
		if (prevLanguage.length() > 0) {
			setLanguage(prevLanguage);
			return;
		}

		language = Locale.getDefault().getLanguage();
	}

	public String getLanguage() {
		loadLanguage();
		Log.i(TAG, language);
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		Locale.setDefault(new Locale(this.language));
	}
}
