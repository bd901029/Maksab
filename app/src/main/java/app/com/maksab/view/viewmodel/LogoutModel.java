package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutModel {
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("device_type")
        @Expose
        private String deviceType;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

    }