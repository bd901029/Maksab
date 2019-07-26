package app.com.maksab.engine.country;

import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.engine.ApiManager;
import app.com.maksab.util.LanguageUtil;
import app.com.maksab.view.viewmodel.LanguageModel;
import app.com.maksab.view.viewmodel.OrderIdModel;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;

public class CountryCityManager extends ApiManager {
	private String TAG = "CountryCityManager";

	private static CountryCityManager instance = null;
	public static CountryCityManager sharedInstance() {
		if (instance == null) {
			instance = new CountryCityManager();
		}
		return instance;
	}

	public CountryContainer countryContainer = null;

	public void load(Callback callback) {
		load(LanguageUtil.currentLanguage(), callback);
	}

	public void load(String language, final Callback callback) {
		LanguageModel languageModel = new LanguageModel(language);
		Api api = APIClient.getClient().create(Api.class);
		Call<CountryCityResponse> call = api.getCountryCity(languageModel);
		call.enqueue(new retrofit2.Callback<CountryCityResponse>() {
			@Override
			public void onResponse(Call<CountryCityResponse> call, Response<CountryCityResponse> apiResponse) {
				CountryCityResponse response = apiResponse.body();
				String responseCode = response.getResponseCode();
				if (responseCode != null && responseCode.equals(Api.SUCCESS)) {
					countryContainer = response.getCountryContainer();
					runCallback(callback, null);
					return;
				}

				runCallback(callback, response.getMessage());
			}

			@Override
			public void onFailure(Call<CountryCityResponse> call, Throwable t) {
				runCallback(callback, t.getLocalizedMessage());
			}
		});
	}

	public ArrayList<Country> getCountries() {
		return getCountries(LanguageUtil.currentLanguage());
	}

	public ArrayList<Country> getCountries(String language) {
		if (countryContainer == null) {
			return new ArrayList<>();
		}
		return countryContainer.getCountries(language);
	}

	public String convertCityName(City city) {
		String currentLanguage = LanguageUtil.currentLanguage();
		ArrayList<Country> countries = getCountries(currentLanguage);
		if (countries == null || countries.size() <= 0) {
			return city.getName();
		}

		for (Country country : countries) {
			for (City tmpCity : country.getCitys()) {
				if (tmpCity.getId().equals(city.getId())) {
					return tmpCity.getName();
				}
			}
		}

		return city.getName();
	}

	public String convertCityName(String cityId) {
		String currentLanguage = LanguageUtil.currentLanguage();
		ArrayList<Country> countries = getCountries(currentLanguage);
		for (Country country : countries) {
			for (City tmpCity : country.getCitys()) {
				if (tmpCity.getId().equals(cityId)) {
					return tmpCity.getName();
				}
			}
		}

		return "";
	}
}