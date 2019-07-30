package app.com.maksab.engine.offer;

import app.com.maksab.engine.category.Category;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeDataResponse {
	public String responseCode = "";
	public String message = "";

	@SerializedName("slider_images")
	public ArrayList<SliderImage> sliderImages;
	@SerializedName("brandData")
	public ArrayList<Brand> brands;
	@SerializedName("collectionsData")
	public ArrayList<Offer> collections;
	@SerializedName("trendingData")
	public ArrayList<Offer> trends;
	@SerializedName("hotdealData")
	public ArrayList<Offer> hotDeals;
	@SerializedName("newdealData")
	public ArrayList<Offer> newDeals;
	@SerializedName("categoryData")
	public ArrayList<Category> categories;
	@SerializedName("subcategoryData")
	public ArrayList<Subcategory> subcategories;
}
