package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenModel {
    @SerializedName("language")
    @Expose
    private String language = "";
    @SerializedName("ios_device_token")
    @Expose
    private String iosDeviceToken = "";
    @SerializedName("android_device_token")
    @Expose
    private String androidDeviceToken = "";
    @SerializedName("device_type")
    @Expose
    private String deviceType = "";
    @SerializedName("certificate_type")
    @Expose
    private String certificateType = "";

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIosDeviceToken() {
        return iosDeviceToken;
    }

    public void setIosDeviceToken(String iosDeviceToken) {
        this.iosDeviceToken = iosDeviceToken;
    }

    public String getAndroidDeviceToken() {
        return androidDeviceToken;
    }

    public void setAndroidDeviceToken(String androidDeviceToken) {
        this.androidDeviceToken = androidDeviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

}