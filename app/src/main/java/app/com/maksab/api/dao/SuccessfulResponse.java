package app.com.maksab.api.dao;

/**
 * Created by Android on 1/24/2018.
 */

public class SuccessfulResponse {
    private String responseCode;
    private String message;

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
