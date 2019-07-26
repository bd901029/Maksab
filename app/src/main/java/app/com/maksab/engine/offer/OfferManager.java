package app.com.maksab.engine.offer;

import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.engine.ApiManager;
import app.com.maksab.view.activity.HomeActivity;
import app.com.maksab.view.viewmodel.HomeDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class OfferManager extends ApiManager {
	private static OfferManager instance = null;
	public static OfferManager sharedInstance() {
		if (instance == null) {
			instance = new OfferManager();
		}
		return instance;
	}

	public ArrayList<SliderImage> sliderImages = new ArrayList<>();
	public ArrayList<Brand> brands = new ArrayList<>();
	public ArrayList<Offer> collections = new ArrayList<>();
	public ArrayList<Offer> trends = new ArrayList<>();
	public ArrayList<Offer> hotDeals = new ArrayList<>();
	public ArrayList<Offer> newDeals = new ArrayList<>();
	public ArrayList<Category> categories = new ArrayList<>();
	public ArrayList<Subcategory> subcategories = new ArrayList<>();

	public void loadHomeData(String language, String countryId, String cityId, String userId, final Callback callback) {
		sliderImages.clear();
		brands.clear();
		collections.clear();
		trends.clear();
		hotDeals.clear();
		newDeals.clear();
		categories.clear();
		subcategories.clear();

		HomeDataModel model = new HomeDataModel();
		model.setLanguage(language);
		model.setCountryId(countryId);
		model.setCityId(cityId);
		model.setUserId(userId);

		Api api = APIClient.getClient().create(Api.class);
		Call<HomeDataResponse> call = api.getHomeData(model);
		call.enqueue(new retrofit2.Callback<HomeDataResponse>() {
			@Override
			public void onResponse(Call<HomeDataResponse> call, Response<HomeDataResponse> apiResponse) {
				HomeDataResponse response = apiResponse.body();
				String responseCode = response.responseCode;
				if (responseCode != null && responseCode.equals(Api.SUCCESS)) {
					sliderImages = response.sliderImages;
					brands = response.brands;
					collections = response.collections;
					trends = response.trends;
					hotDeals = response.hotDeals;
					newDeals = response.newDeals;
					categories = response.categories;
					subcategories = response.subcategories;

					runCallback(callback, null);
					return;
				}

				runCallback(callback, response.message);
			}

			@Override
			public void onFailure(Call<HomeDataResponse> call, Throwable t) {
				runCallback(callback, SomethingWentWrong);
			}
		});
	}
}
