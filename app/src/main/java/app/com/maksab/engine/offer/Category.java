package app.com.maksab.engine.offer;

import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category {
	private static String TAG = "Category";

	@SerializedName("category_id")
	public String id = "";
	@SerializedName("category_name")
	public String name = "";
	@SerializedName("offers")
	public ArrayList<Offer> offers = new ArrayList<>();
}