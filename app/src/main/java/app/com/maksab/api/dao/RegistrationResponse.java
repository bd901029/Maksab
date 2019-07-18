package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    public String ErrorMessage;
    public String Status;
    @SerializedName("Response")
    public RegistrationResponse.MyResponse myResponse;
    public String responseCode;
    public String message;

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

    public MyResponse getMyResponse() {
        return myResponse;
    }

    public void setMyResponse(MyResponse myResponse) {
        this.myResponse = myResponse;
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


