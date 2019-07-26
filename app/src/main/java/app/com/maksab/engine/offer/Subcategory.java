package app.com.maksab.engine.offer;

import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Subcategory {
	private static String TAG = "Category";

	@SerializedName("subcategory_id")
	public String id = "";
	@SerializedName("sub_category_name")
	public String name = "";
	@SerializedName("offers")
	public ArrayList<Offer> offers = new ArrayList<>();
}
