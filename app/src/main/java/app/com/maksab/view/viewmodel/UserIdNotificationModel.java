package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class UserIdNotificationModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    public String notificationStatus = "";

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
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
