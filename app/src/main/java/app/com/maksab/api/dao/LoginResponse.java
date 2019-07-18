package app.com.maksab.api.dao;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import app.com.maksab.BR;

public class LoginResponse extends BaseObservable{
    public String message;
    public String responseCode;
    @SerializedName("user_id")
    public String userId;
    @SerializedName("user_name")
    public String userName;
    public String email;
    @SerializedName("country_code")
    public String countryCode;
    public String mobile;
    @SerializedName("city_id")
    public String cityId;
    @SerializedName("profile_pic")
    public String profilePic;
    @SerializedName("email_verify")
    public String emailVerify;
    @SerializedName("mobile_verify")
    public String mobileVerify;
    public String ErrorMessage;
    public String Status;
    @SerializedName("Response")
    public RegistrationResponse.MyResponse myResponse;
    public String isMember;
    @SerializedName("max_device_count")
    public String maxDeviceCount;

    public String getIsMember() {
        return isMember;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    public String getMaxDeviceCount() {
        return maxDeviceCount;
    }

    public void setMaxDeviceCount(String maxDeviceCount) {
        this.maxDeviceCount = maxDeviceCount;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public RegistrationResponse.MyResponse getMyResponse() {
        return myResponse;
    }

    public void setMyResponse(RegistrationResponse.MyResponse myResponse) {
        this.myResponse = myResponse;
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
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
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
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
        notifyPropertyChanged(BR.profilePic);
    }

    @Bindable
    public String getEmailVerify() {
        return emailVerify;
    }

    public void setEmailVerify(String emailVerify) {
        this.emailVerify = emailVerify;
        notifyPropertyChanged(BR.emailVerify);
    }

    @Bindable
    public String getMobileVerify() {
        return mobileVerify;
    }

    public void setMobileVerify(String mobileVerify) {
        this.mobileVerify = mobileVerify;
        notifyPropertyChanged(BR.mobileVerify);
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }
    @Bindable
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        notifyPropertyChanged(BR.responseCode);
    }
    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }
    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    public class MyResponse {
        @SerializedName("message_id")
        public String messageId;
        @SerializedName("message_count")
        public String messageCount;
        public String price = "";

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(String messageCount) {
            this.messageCount = messageCount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
