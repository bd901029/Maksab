package app.com.maksab.api.dao;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import app.com.maksab.BR;

/**
 * Created by Android on 1/24/2018.
 */

public class ProfileResponse extends BaseObservable implements Parcelable {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("profile_pic")
    private String profilePic;
    @SerializedName("user_name")
    private String userName;
    private String email;
    private String mobile;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("email_verify")
    private String emailVerify;
    @SerializedName("mobile_verify")
    private String mobileVerify;
    private String responseCode;
    private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getEmailVerify() {
        return emailVerify;
    }

    public void setEmailVerify(String emailVerify) {
        this.emailVerify = emailVerify;
    }

    public String getMobileVerify() {
        return mobileVerify;
    }

    public void setMobileVerify(String mobileVerify) {
        this.mobileVerify = mobileVerify;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.profilePic);
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.cityId);
        dest.writeString(this.emailVerify);
        dest.writeString(this.mobileVerify);
        dest.writeString(this.responseCode);
        dest.writeString(this.message);
    }

    public ProfileResponse() {
    }

    protected ProfileResponse(Parcel in) {
        this.userId = in.readString();
        this.profilePic = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.cityId = in.readString();
        this.emailVerify = in.readString();
        this.mobileVerify = in.readString();
        this.responseCode = in.readString();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<ProfileResponse> CREATOR = new Parcelable.Creator<ProfileResponse>() {
        @Override
        public ProfileResponse createFromParcel(Parcel source) {
            return new ProfileResponse(source);
        }

        @Override
        public ProfileResponse[] newArray(int size) {
            return new ProfileResponse[size];
        }
    };
}
