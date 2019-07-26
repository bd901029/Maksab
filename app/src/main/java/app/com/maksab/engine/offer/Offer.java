package app.com.maksab.engine.offer;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

public class Offer {
	private static String TAG = "Offer";

	@SerializedName("offer_id")
	public String id = "";
	@SerializedName("offer_name")
	public String name = "";
	@SerializedName("offer_description")
	public String description = "";
	@SerializedName("beforeAmount")
	public String beforeAmount = "";
	@SerializedName("afterAmount")
	public String afterAmount = "";
	@SerializedName("discountRate")
	public String discountRate = "";
	@SerializedName("rates")
	public String rates = "";
	@SerializedName("bought")
	public String bought = "";
	@SerializedName("reaming")
	public String reaming = "";
	@SerializedName("offer_img")
	public String image = "";
	@SerializedName("partner_img")
	public String partnerImage = "";
	@SerializedName("favStatus")
	public String favStatus = "";
	@SerializedName("favCount")
	public String favCount = "";
}
