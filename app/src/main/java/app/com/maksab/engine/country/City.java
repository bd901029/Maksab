package app.com.maksab.engine.country;

import app.com.maksab.util.LanguageUtil;
import com.google.gson.annotations.SerializedName;

public class City {
	private String TAG = "City";

	public class Languages {
		@SerializedName("ar")
		private boolean ar = false;

		@SerializedName("en")
		private boolean en = false;

		@SerializedName("tr")
		private boolean tr = false;

		public boolean isAr() {
			return ar;
		}

		public void setAr(boolean ar) {
			this.ar = ar;
		}

		public boolean isEn() {
			return en;
		}

		public void setEn(boolean en) {
			this.en = en;
		}

		public boolean isTr() {
			return tr;
		}

		public void setTr(boolean tr) {
			this.tr = tr;
		}
	}

	@SerializedName("city_id")
	private String id = "";
	@SerializedName("city_name")
	private String name = "";
	@SerializedName("languages")
	private Languages languages;
	private String countryId;
	private String countryName;
	private String countryCode;
	private String countryFlag;
	private boolean selected = false;

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

	public Languages getLanguages() {
		return languages;
	}

	public void setLanguages(Languages languages) {
		this.languages = languages;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryFlag() {
		return countryFlag;
	}

	public void setCountryFlag(String countryFlag) {
		this.countryFlag = countryFlag;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSupported(String language) {
		switch (language) {
			case LanguageUtil.ENGLISH:
				return languages.isEn();
			case LanguageUtil.ARABIC:
				return languages.isAr();
			case LanguageUtil.TURKYCE:
				return languages.isTr();
			default:
				return false;
		}
	}
}
