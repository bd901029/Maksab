package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class CityModel {
    @SerializedName("country_id")
    public String countryId = "";

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
