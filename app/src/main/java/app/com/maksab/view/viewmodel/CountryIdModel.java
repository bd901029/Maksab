package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class CountryIdModel {
    public String language = "";
    @SerializedName("country_id")
    public String countryId = "";

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
