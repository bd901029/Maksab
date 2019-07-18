package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class ResendOTPModel {
    public String language = "";
    @SerializedName("country_code")
    public String countryCode = "";
    @SerializedName("mobile")
    public String mobile = "";

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
