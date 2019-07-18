package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class GiftModel {
    public String language = "";
    @SerializedName("offer_id")
    public String offerId = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("user_id")
    public String userId = "";
    public String email = "";
    public String name = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
