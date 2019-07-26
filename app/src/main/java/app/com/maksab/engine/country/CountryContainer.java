package app.com.maksab.engine.country;

import app.com.maksab.util.LanguageUtil;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryContainer {
	@SerializedName("ar")
	private ArrayList<Country> arCountries;
	@SerializedName("en")
	private ArrayList<Country> enCountries;
	@SerializedName("tr")
	private ArrayList<Country> trCountries;

	public ArrayList<Country> getArCountries() {
		return arCountries;
	}

	public void setArCountries(ArrayList<Country> arCountries) {
		this.arCountries = arCountries;
	}

	public ArrayList<Country> getEnCountries() {
		return enCountries;
	}

	public void setEnCountries(ArrayList<Country> enCountries) {
		this.enCountries = enCountries;
	}

	public ArrayList<Country> getCountries(String language) {
		switch (language) {
			case LanguageUtil.ENGLISH:
				return enCountries;
			case LanguageUtil.ARABIC:
				return arCountries;
			case LanguageUtil.TURKYCE:
				return trCountries;
			default:
				return enCountries;
		}
	}
}
