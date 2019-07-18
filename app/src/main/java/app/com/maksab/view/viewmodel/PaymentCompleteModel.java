package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class PaymentCompleteModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("plan_id")
    public String planId = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("transaction_amount")
    public String transactionAmount = "";
    @SerializedName("coupon_code_id")
    public String couponCodeId = "";
    @SerializedName("customer_name")
    public String customerName = "";
    @SerializedName("customer_phone")
    public String customerPhone = "";
    @SerializedName("customer_email")
    public String customerEmail = "";
    @SerializedName("transaction_id")
    public String transactionId = "";
    @SerializedName("order_id")
    public String orderId = "";
    @SerializedName("response_code")
    public String responseCode = "";
    @SerializedName("response_message")
    public String responseMessage = "";

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(String couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
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
