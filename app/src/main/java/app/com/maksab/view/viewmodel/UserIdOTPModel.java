package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class UserIdOTPModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("OTP")
    public String OTP = "";

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
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
