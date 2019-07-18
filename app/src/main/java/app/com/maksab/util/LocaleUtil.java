package app.com.maksab.util;

import app.com.maksab.MyApplication;

import java.util.Locale;

public class LocaleUtil {
	private static LocaleUtil instance = null;
	public static LocaleUtil sharedInstance() {
		if (instance == null) {
			instance = new LocaleUtil();
		}
		return instance;
	}

	String language = "";

	LocaleUtil() {
		loadLanguage();
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
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		Locale.setDefault(new Locale(this.language));
	}
}
