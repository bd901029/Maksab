package app.com.maksab.engine.offer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category {
	private static String TAG = "Category";

	public static class Key {
		public static String Id = "category_id";
		public static String Name = "category_name";
		public static String Offers = "offers";
	}

	public String id = "";
	public String name = "";
	public ArrayList<Offer> offers = new ArrayList<>();

	public Category(JSONObject info) {
		try {
			id = info.getString(Key.Id);
			name = info.getString(Key.Name);
			JSONArray offerInfos = info.getJSONArray(Key.Offers);
			for (int i = 0; i < offerInfos.length(); i++) {
				Offer offer = new Offer(offerInfos.getJSONObject(i));
				offers.add(offer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}