package app.com.maksab.engine.offer;

import app.com.maksab.MyApplication;
import app.com.maksab.engine.ApiManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		brands.clear();
		collections.clear();
		trends.clear();
		hotDeals.clear();
		newDeals.clear();

		/*
		Map<String, String> params = new HashMap<String, String>();
		params.put(Key.Language, language);
		params.put(Key.CountryId, countryId);
		params.put(Key.CityId, cityId);
		params.put(Key.UserId, userId);
		*/

		JSONObject params = new JSONObject();
		try {
			params.put(Key.Language, language);
			params.put(Key.CountryId, countryId);
			params.put(Key.CityId, cityId);
			params.put(Key.UserId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		call(PATH.GET_HOME_DATA, params, new JsonCallback() {
			@Override
			public void OnFinished(JSONObject result, String message) {
				if (message != null) {
					runCallback(callback, SomethingWentWrong);
					return;
				}

				try {
					int responseCode = result.getInt(Key.ResponseCode);
					if (responseCode != 200) {
						runCallback(callback, SomethingWentWrong);
						return;
					}

					if (!result.isNull(Key.SliderImages)) {
						JSONArray sliderImageInfos = result.getJSONArray(Key.SliderImages);
						for (int i = 0; i < sliderImageInfos.length(); i++) {
							SliderImage sliderImage = new SliderImage(sliderImageInfos.getJSONObject(i));
							sliderImages.add(sliderImage);
						}
					}

					if (!result.isNull(Key.BrandData)) {
						JSONArray brandInfos = result.getJSONArray(Key.BrandData);
						for (int i = 0; i < brandInfos.length(); i++) {
							Brand brand = new Brand(brandInfos.getJSONObject(i));
							brands.add(brand);
						}
					}

					if (!result.isNull(Key.Collection)) {
						JSONArray collectionInfos = result.getJSONArray(Key.Collection);
						for (int i = 0; i < collectionInfos.length(); i++) {
							Offer offer = new Offer(collectionInfos.getJSONObject(i));
							collections.add(offer);
						}
					}

					if (!result.isNull(Key.Trend)) {
						JSONArray trendInfos = result.getJSONArray(Key.Trend);
						for (int i = 0; i < trendInfos.length(); i++) {
							Offer offer = new Offer(trendInfos.getJSONObject(i));
							trends.add(offer);
						}
					}

					if (!result.isNull(Key.HotDeal)) {
						JSONArray hotDealInfos = result.getJSONArray(Key.HotDeal);
						for (int i = 0; i < hotDealInfos.length(); i++) {
							Offer offer = new Offer(hotDealInfos.getJSONObject(i));
							hotDeals.add(offer);
						}
					}

					if (!result.isNull(Key.Category)) {
						JSONArray categoryInfos = result.getJSONArray(Key.Category);
						for (int i = 0; i < categoryInfos.length(); i++) {
							Category category = new Category(categoryInfos.getJSONObject(i));
							categories.add(category);
						}
					}

					if (!result.isNull(Key.Subcategory)) {
						JSONArray subcategoryInfos = result.getJSONArray(Key.Subcategory);
						for (int i = 0; i < subcategoryInfos.length(); i++) {
							Subcategory subcategory = new Subcategory(subcategoryInfos.getJSONObject(i));
							subcategories.add(subcategory);
						}
					}

					runCallback(callback, null);
				} catch (Exception e) {
					e.printStackTrace();
					runCallback(callback, e.getLocalizedMessage());
				}
			}
		});
	}
}
