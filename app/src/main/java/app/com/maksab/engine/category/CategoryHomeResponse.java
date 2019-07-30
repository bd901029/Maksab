package app.com.maksab.engine.category;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryHomeResponse {
	public String responseCode = "";
	public String message = "";
	@SerializedName("category_list")
	public ArrayList<Category> categories = new ArrayList<>();
}
