package app.com.maksab.view.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import app.com.maksab.BR;

import com.google.gson.annotations.SerializedName;


public class UserRegistrationModel extends BaseObservable {
    @SerializedName("user_name")
    public String Username = "";
    public String email = "";
    public String password = "";
    @SerializedName("country_code")
    public String countryCode = "";
    public String language = "";
    @SerializedName("city_id")
    public String cityId = "";
    public String mobile = "";
    @SerializedName("android_device_token")
    public String fcmToken = "";
    public String confirmPassword = "";

    @SerializedName("device_type")
    public String device_type = "Android";

    @SerializedName("IEMI_number")
    public String IEMINumber = "";
    @SerializedName("referral_code")
    public String referralCode = "";

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getIEMINumber() {
        return IEMINumber;
    }

    public void setIEMINumber(String IEMINumber) {
        this.IEMINumber = IEMINumber;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Bindable
    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
        notifyPropertyChanged(BR.fcmToken);

    }

    @Bindable
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        notifyPropertyChanged(BR.countryCode);
    }

    @Bindable
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        notifyPropertyChanged(BR.language);
    }

    @Bindable
    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
        notifyPropertyChanged(BR.cityId);
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }


}