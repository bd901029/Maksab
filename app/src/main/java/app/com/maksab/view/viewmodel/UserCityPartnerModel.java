package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class UserCityPartnerModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("partner_id")
    public String partnerId = "";

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

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
}
