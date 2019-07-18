package app.com.maksab.view.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import app.com.maksab.BR;

public class LoginModel extends BaseObservable {
    @SerializedName("email")
    public String email = "";
    @SerializedName("password")
    public String password = "";
    public String language = "";
    @SerializedName("android_device_token")
    public String fcmToken = "";
    @SerializedName("IEMI_number")
    public String IEMINumber = "";

    public String getIEMINumber() {
        return IEMINumber;
    }

    public void setIEMINumber(String IEMINumber) {
        this.IEMINumber = IEMINumber;
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
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        notifyPropertyChanged(BR.language);
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

}
