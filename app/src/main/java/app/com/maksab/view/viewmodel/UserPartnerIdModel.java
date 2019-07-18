package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class UserPartnerIdModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("partner_id")
    public String partnerId = "";

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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
