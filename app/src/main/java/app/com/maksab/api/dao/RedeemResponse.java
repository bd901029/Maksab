package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 1/24/2018.
 */
public class RedeemResponse {
    private String responseCode;
    private String message;
    private String redeemedMsg;
    private String redeemedStatus;
    @SerializedName("redeemed_id")
    private String redeemedId;
    private String saved;

    public String getRedeemedId() {
        return redeemedId;
    }

    public void setRedeemedId(String redeemedId) {
        this.redeemedId = redeemedId;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public String getRedeemedMsg() {
        return redeemedMsg;
    }

    public void setRedeemedMsg(String redeemedMsg) {
        this.redeemedMsg = redeemedMsg;
    }

    public String getRedeemedStatus() {
        return redeemedStatus;
    }

    public void setRedeemedStatus(String redeemedStatus) {
        this.redeemedStatus = redeemedStatus;
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
