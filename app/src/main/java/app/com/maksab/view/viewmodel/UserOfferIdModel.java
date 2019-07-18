package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class UserOfferIdModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("offer_id")
    public String offerId = "";

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
