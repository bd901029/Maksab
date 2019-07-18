package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class RedeemModel {
    public String language = "";
    @SerializedName("offer_id")
    public String offerId = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("partner_code")
    public String partnerCode = "";

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

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
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
