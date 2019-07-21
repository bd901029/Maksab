package app.com.maksab.engine.country;

import android.util.Log;
import app.com.maksab.engine.ApiManager;
import app.com.maksab.util.LanguageUtil;
import app.com.maksab.view.adapter.TabAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CountryManager extends ApiManager {
	private static CountryManager instance = null;
	public static CountryManager sharedInstance() {
		if (instance == null) {
			instance = new CountryManager();
		}
		return instance;
	}

	private String TAG = "CountryManager";

	public ArrayList<Country> countries = new ArrayList<>();

	public String convertCityName(String orgName) {
		return convertCityName(orgName, LanguageUtil.sharedInstance().getLanguage());
	}

	public String convertCityName(String orgName, String language) {
		for (Country country : countries) {
			for (City city : country.cities) {
				for (String tmpLanguage : LanguageUtil.Languages) {
					if (city.getName(tmpLanguage).equals(orgName)) {
						Log.i(TAG, city.getName(LanguageUtil.ENGLISH));
						return city.getName(language);
					}
				}
			}
		}
		return orgName;
	}

	public String convertCountryName(String orgName) {
		return convertCountryName(orgName, LanguageUtil.sharedInstance().getLanguage());
	}

	public String convertCountryName(String orgName, String language) {
		for (Country country : countries) {
			for (String tmpLanguage : LanguageUtil.Languages) {
				if (country.getName(tmpLanguage).equals(orgName)) {
					return country.getName(language);
				}
			}
		}
		return orgName;
	}

	public void load(Callback callback) {
		load(LanguageUtil.ENGLISH, callback);
	}

	public void load(String language, final Callback callback) {
		if (countries.size() > 0) {
			runCallback(callback, null);
			return;
		}

		/*
		Map<String, String> params = new HashMap<String, String>();
		params.put(Key.Language, language);
		*/

		JSONObject params = new JSONObject();
		try {
			params.put(Key.Language, language);
		} catch (Exception e) {
			e.printStackTrace();
		}

		call(PATH.GET_COUNTRY_CITY, params, new JsonCallback() {
			@Override
			public void OnFinished(JSONObject result, String message) {
				if (message != null) {
					runCallback(callback, message);
					return;
				}

				try {
					int responseCode = result.getInt(Key.ResponseCode);
					if (responseCode != 200) {
						runCallback(callback, SomethingWentWrong);
						return;
					}

					countries.clear();

					for (String language : LanguageUtil.Languages) {
						JSONObject countryData = result.getJSONObject(Key.CountryData);
						loadCountries(language, countryData);
					}

					runCallback(callback, null);
				} catch (Exception e) {
					e.printStackTrace();
					runCallback(callback, e.getLocalizedMessage());
				}
			}
		});
	}

	private void loadCountries(String language, JSONObject info) {
		try {
			JSONArray countryInfos = info.getJSONArray(language);
			if (countryInfos == null) {
				return;
			}

			for (int i = 0; i < countryInfos.length(); i++) {
				JSONObject countryInfo = countryInfos.getJSONObject(i);
				String id = countryInfo.getString(Country.Key.Id);

				boolean found = false;
				for (Country country : countries) {
					if (country.id.equals(id)) {
						country.setName(language, countryInfo);
						country.setCities(language, countryInfo);
						found = true;
						break;
					}
				}

				if (found) continue;

				Country country = new Country(language, countryInfo);
				countries.add(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}