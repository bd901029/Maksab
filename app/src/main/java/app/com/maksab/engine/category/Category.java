package app.com.maksab.engine.category;

import app.com.maksab.engine.offer.Offer;
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
	@SerializedName("category_img")
	public String image = "";
	@SerializedName("category_status")
	public String status = "1";
	@SerializedName("offers")
	public ArrayList<Offer> offers = new ArrayList<>();

	public void setStatus(String status) {
		this.status = status;
	}
}