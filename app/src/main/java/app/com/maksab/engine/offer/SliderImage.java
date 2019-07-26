package app.com.maksab.engine.offer;

import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONObject;

public class SliderImage {
	private static String TAG = "SliderImage";

	@SerializedName("slider_id")
	public String id = "";
	@SerializedName("banner_image")
	public String bannerImage = "";
	@SerializedName("banner_text")
	public String bannerText = "";
}
