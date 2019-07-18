package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class RateReviewModel {
    public String language = "";
    @SerializedName("offer_id")
    public String offerId = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("order_id")
    public String orderId = "";
    public String rate = "";
    public String review = "";

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
