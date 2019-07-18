package app.com.maksab.api.dao;

/**
 * Created by Android on 1/24/2018.
 */

public class NotificationStatusResponse {
    private String responseCode;
    private String message;
    private String notificationStatus;

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
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
}
