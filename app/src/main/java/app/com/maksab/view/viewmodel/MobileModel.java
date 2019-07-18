package app.com.maksab.view.viewmodel;


import com.google.gson.annotations.SerializedName;

public class MobileModel{
    public String otp = "";
    public String language = "";
    public String mobile = "";
    @SerializedName("android_device_token")
    public String androidDeviceToken = "";
    @SerializedName("IEMI_number")
    public String IEMINumber = "";

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAndroidDeviceToken() {
        return androidDeviceToken;
    }

    public void setAndroidDeviceToken(String androidDeviceToken) {
        this.androidDeviceToken = androidDeviceToken;
    }

    public String getIEMINumber() {
        return IEMINumber;
    }

    public void setIEMINumber(String IEMINumber) {
        this.IEMINumber = IEMINumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
