package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 1/24/2018.
 */

public class CouponCodeResponse {
    private String responseCode;
    private String message;
    @SerializedName("coupon_amount")
    private String couponAmount;
    @SerializedName("discounted_price_with_currency")
    private String discountedPriceWithCurrency;
    @SerializedName("discounted_price")
    private String discountedPrice;
    @SerializedName("coupon_code_id")
    private String couponCodeId;
    @SerializedName("paystatus")
    private String paystatus;
    @SerializedName("subsciber_id")
    private String subsciberId;

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getDiscountedPriceWithCurrency() {
        return discountedPriceWithCurrency;
    }

    public void setDiscountedPriceWithCurrency(String discountedPriceWithCurrency) {
        this.discountedPriceWithCurrency = discountedPriceWithCurrency;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(String couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getSubsciberId() {
        return subsciberId;
    }

    public void setSubsciberId(String subsciberId) {
        this.subsciberId = subsciberId;
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
