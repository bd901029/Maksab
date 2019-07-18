package app.com.maksab.api.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOrderAmount implements Serializable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalSavingThisYear")
    @Expose
    private String totalSavingThisYear;
    @SerializedName("noOfRedeemOffers")
    @Expose
    private String noOfRedeemOffers;
    @SerializedName("totalPurchaseAmount")
    @Expose
    private String totalPurchaseAmount;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotalSavingThisYear() {
        return totalSavingThisYear;
    }

    public void setTotalSavingThisYear(String totalSavingThisYear) {
        this.totalSavingThisYear = totalSavingThisYear;
    }

    public String getNoOfRedeemOffers() {
        return noOfRedeemOffers;
    }

    public void setNoOfRedeemOffers(String noOfRedeemOffers) {
        this.noOfRedeemOffers = noOfRedeemOffers;
    }

    public String getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(String totalPurchaseAmount) {
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

}
