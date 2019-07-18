package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class HomeDataModel {
    public String language = "";
    @SerializedName("country_id")
    public String countryId = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("user_id")
    public String userId = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
