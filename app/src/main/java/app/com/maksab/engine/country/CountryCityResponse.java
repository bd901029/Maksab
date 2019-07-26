package app.com.maksab.engine.country;

import com.google.gson.annotations.SerializedName;

public class CountryCityResponse {
	@SerializedName("countryData")
	private CountryContainer countryContainer;
	@SerializedName("message")
	private String message;
	@SerializedName("responseCode")
	private String responseCode;

	public CountryContainer getCountryContainer() {
		return countryContainer;
	}

	public void setCountryContainer(CountryContainer countryContainer) {
		this.countryContainer = countryContainer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
}
