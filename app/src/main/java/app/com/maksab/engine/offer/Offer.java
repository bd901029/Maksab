package app.com.maksab.engine.offer;

import org.json.JSONObject;

public class Offer {
	private static String TAG = "Offer";

	public static class Key {
		public static String Id = "offer_id";
		public static String Name = "offer_name";
		public static String Description = "offer_description";
		public static String BeforeAmount = "beforeAmount";
		public static String AfterAmount = "afterAmount";
		public static String DiscountRate = "discountRate";
		public static String Rates = "rates";
		public static String Bought = "bought";
		public static String Reaming = "reaming";
		public static String Image = "offer_img";
		public static String PartnerImage = "partner_img";
		public static String FavStatus = "favStatus";
		public static String FavCount = "favCount";
	}

	public String id = "";
	public String name = "";
	public String description = "";
	public String beforeAmount = "";
	public String afterAmount = "";
	public String discountRate = "";
	public String rates = "";
	public int bought = 0;
	public String boughtInText = "";
	public String reaming = "";
	public String image = "";
	public String partnerImage = "";
	public int favStatus = 0;
	public int favCount = 0;

	public Offer(JSONObject info) {
		try {
			id = info.getString(Key.Id);
			name = info.getString(Key.Name);
			description = info.getString(Key.Description);
			beforeAmount = info.getString(Key.BeforeAmount);
			afterAmount = info.getString(Key.AfterAmount);
			discountRate = info.getString(Key.DiscountRate);
			rates = info.getString(Key.Rates);
			if (info.get(Key.Bought).getClass() == String.class) {
				boughtInText = info.getString(Key.Bought);
			} else {
				bought = info.getInt(Key.Bought);
			}
			bought = info.getInt(Key.Bought);
			reaming = info.getString(Key.Reaming);
			image = info.getString(Key.Image);
			partnerImage = info.getString(Key.PartnerImage);
			if (info.get(Key.FavStatus).getClass() == String.class) {
				favStatus = Integer.parseInt(info.getString(Key.FavStatus));
			} else {
				favStatus = info.getInt(Key.FavStatus);
			}
			favStatus = info.getInt(Key.FavStatus);
			favCount = info.getInt(Key.FavCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getBought() {
		return boughtInText.length() > 0 ? boughtInText : String.valueOf(bought);
	}

	public String getFavStatus() {
		return String.valueOf(favStatus);
	}
}
