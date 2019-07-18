package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationResponse {
    private String message;
    private String responseCode;
    @SerializedName("notification_list")
    private ArrayList<NotificationList> notificationList;

    public ArrayList<NotificationList> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(ArrayList<NotificationList> notificationList) {
        this.notificationList = notificationList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public class NotificationList {
        @SerializedName("notification_id")
        private String notificationId;
        @SerializedName("notification_title")
        private String notificationTitle;
        @SerializedName("notification_description")
        private String notificationDescription;
        @SerializedName("notification_img")
        private String notificationImg;
        @SerializedName("notification_offer_id")
        private String notificationOfferId;
        @SerializedName("notification_date")
        private String notificationDate;

        public String getNotificationOfferId() {
            return notificationOfferId;
        }

        public void setNotificationOfferId(String notificationOfferId) {
            this.notificationOfferId = notificationOfferId;
        }

        public String getNotificationDate() {
            return notificationDate;
        }

        public void setNotificationDate(String notificationDate) {
            this.notificationDate = notificationDate;
        }

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getNotificationTitle() {
            return notificationTitle;
        }

        public void setNotificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
        }

        public String getNotificationDescription() {
            return notificationDescription;
        }

        public void setNotificationDescription(String notificationDescription) {
            this.notificationDescription = notificationDescription;
        }

        public String getNotificationImg() {
            return notificationImg;
        }

        public void setNotificationImg(String notificationImg) {
            this.notificationImg = notificationImg;
        }
    }
}
