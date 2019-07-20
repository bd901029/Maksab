package app.com.maksab.engine.country;

import android.util.Log;
import app.com.maksab.util.LanguageUtil;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class City {
	public static class Key {
		public static String Id = "city_id";
		public static String Name = "city_name";
		public static String Languages = "languages";
	}

	private String TAG = "City";

	public Country country = null;
	public String id = "";
	private LinkedHashMap<String, String> nameInfo = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, Boolean> languages = new LinkedHashMap<String, Boolean>();
	public boolean isSelected = false;

	public City(String language, JSONObject info) {
		try {
			this.id = info.getString(Key.Id);
			setName(language, info);
			setLanguages(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		String language = LanguageUtil.sharedInstance().getLanguage();
		return getName(language);
	}

	public String getName(String language) {
		String name = nameInfo.get(language);
		if (name == null) {
			name = "";
		}

		Log.i(TAG, name);
		return name;
	}

	public void setName(String language, JSONObject info) {
		try {
			nameInfo.put(language, info.getString(Key.Name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean isSupported(String language) {
		Boolean isSupported = languages.get(language);
		if (isSupported != null) {
			return isSupported;
		}
		return false;
	}

	public void setLanguages(JSONObject info) {
		try {
			JSONObject languageInfo = info.getJSONObject(Key.Languages);
			for (String lg : LanguageUtil.Languages) {
				languages.put(lg, languageInfo.getBoolean(lg));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
