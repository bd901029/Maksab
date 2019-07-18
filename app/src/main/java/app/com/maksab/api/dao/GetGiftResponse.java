package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 1/24/2018.
 */

public class GetGiftResponse {
    private String responseCode;
    private String message;
    @SerializedName("max_gift")
    private String maxGift;
    private String sentOffer;
    private String reamningGift;

    public String getMaxGift() {
        return maxGift;
    }

    public void setMaxGift(String maxGift) {
        this.maxGift = maxGift;
    }

    public String getSentOffer() {
        return sentOffer;
    }

    public void setSentOffer(String sentOffer) {
        this.sentOffer = sentOffer;
    }

    public String getReamningGift() {
        return reamningGift;
    }

    public void setReamningGift(String reamningGift) {
        this.reamningGift = reamningGift;
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
