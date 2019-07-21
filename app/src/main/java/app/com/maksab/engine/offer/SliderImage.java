package app.com.maksab.engine.offer;

import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONObject;

public class SliderImage {
	private static String TAG = "SliderImage";

	public static class Key {
		public static String Id = "slider_id";
		public static String BannerImage = "banner_image";
		public static String BannerText = "banner_text";
	}

	public String id = "";
	public String bannerImage = "";
	public String bannerText = "";

	public SliderImage(JSONObject info) {
		try {
			id = info.getString(Key.Id);
			bannerImage = info.getString(Key.BannerImage);
			bannerText = info.getString(Key.BannerText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
