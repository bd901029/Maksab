package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class UserIdMobileModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("mobile_number")
    public String mobileNumber = "";

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
