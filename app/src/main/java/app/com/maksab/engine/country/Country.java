package app.com.maksab.engine.country;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Country {
	private static String TAG = "Country";

	@SerializedName("country_id")
	private String id = "";
	@SerializedName("country_name")
	private String name = "";
	@SerializedName("country_code")
	private String code = "";
	@SerializedName("country_flag")
	private String flag = "";
	@SerializedName("citys")
	private ArrayList<City> citys = new ArrayList<>();
	private boolean isSelected = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public ArrayList<City> getCitys() {
		return citys;
	}

	public void setCitys(ArrayList<City> citys) {
		this.citys = citys;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
