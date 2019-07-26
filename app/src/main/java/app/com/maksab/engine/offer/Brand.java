package app.com.maksab.engine.offer;

import com.google.gson.annotations.SerializedName;

public class Brand {
	private static String TAG = "Brand";

	@SerializedName("brand_id")
	public String id = "";
	@SerializedName("brand_name")
	public String name = "";
	@SerializedName("brand_img")
	public String image = "";
}
