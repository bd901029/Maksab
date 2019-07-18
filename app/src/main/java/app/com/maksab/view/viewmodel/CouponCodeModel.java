package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class CouponCodeModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("plan_id")
    public String planId = "";
    @SerializedName("plan_amount")
    public String planAmount = "";
    @SerializedName("coupon_code")
    public String couponCode = "";

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
