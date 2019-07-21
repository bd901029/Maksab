package app.com.maksab.engine.offer;

import org.json.JSONObject;

public class Brand {
	private static String TAG = "Offer";

	public static class Key {
		public static String Id = "brand_id";
		public static String Name = "brand_name";
		public static String Image = "brand_img";
	}

	public String id = "";
	public String name = "";
	public String image = "";

	public Brand(JSONObject info) {
		try {
			id = info.getString(Key.Id);
			name = info.getString(Key.Name);
			image = info.getString(Key.Image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
