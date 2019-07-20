package app.com.maksab.engine.country;

import app.com.maksab.util.LanguageUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Country {
	public static class Key {
		public static String Id = "country_id";
		public static String Name = "country_name";
		public static String Code = "country_code";
		public static String Flag = "country_flag";
		public static String Cities = "citys";
	}

	public String id = "";
	public String code = "";
	public String flag = "";
	private LinkedHashMap<String, String> nameInfo = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> languages = new LinkedHashMap<String, String>();
	public ArrayList<City> cities = new ArrayList<>();
	public boolean isSelected = false;

	public Country(String language, JSONObject info) {
		try {
			this.id = info.getString(Key.Id);
			this.code = info.getString(Key.Code);
			this.flag = info.getString(Key.Flag);
			setName(language, info);
			setCities(language, info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		String language = LanguageUtil.sharedInstance().getLanguage();
		return getName(language);
	}

	public String getName(String language) {
		if (isSupported(language)) {
			return nameInfo.get(language);
		}
		return "";
	}

	public void setName(String language, JSONObject info) {
		try {
			nameInfo.put(language, info.getString(Key.Name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setCities(String language, JSONObject info) {
		try {
			JSONArray cityInfos = info.getJSONArray(Key.Cities);
			for (int i = 0; i < cityInfos.length(); i++) {
				JSONObject cityInfo = cityInfos.getJSONObject(i);
				String id = cityInfo.getString(City.Key.Id);

				boolean found = false;
				for (City city : cities) {
					if (city.id.equals(id)) {
						city.setName(language, cityInfo);
						city.setLanguages(cityInfo);
						found = true;
						break;
					}
				}

				if (found) continue;

				City city = new City(language, cityInfo);
				city.country = this;
				cities.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isSupported(String language) {
		String name = nameInfo.get(language);
		if (name == null || name.length() <= 0) {
			return false;
		}
		return true;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
