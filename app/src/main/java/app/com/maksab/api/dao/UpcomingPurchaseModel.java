package app.com.maksab.api.dao;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UpcomingPurchaseModel implements Serializable {

    @SerializedName("offerList")
    @Expose
    private ArrayList<OfferList> offerList = null;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<OfferList> getOfferList() {
        return offerList;
    }

    public void setOfferList(ArrayList<OfferList> offerList) {
        this.offerList = offerList;
    }

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

    public class OfferList implements Serializable{

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("offer_name")
        @Expose
        private String offerName;
        @SerializedName("offer_description")
        @Expose
        private String offerDescription;
        @SerializedName("beforeAmount")
        @Expose
        private String beforeAmount;
        @SerializedName("afterAmount")
        @Expose
        private String afterAmount;
        @SerializedName("discountRate")
        @Expose
        private String discountRate;
        @SerializedName("rates")
        @Expose
        private String rates;
        @SerializedName("reedeem_dates")
        @Expose
        private String reedeemDates;
        @SerializedName("offer_img")
        @Expose
        private String offerImg;
        @SerializedName("partner_img")
        @Expose
        private String partner_img;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getOfferName() {
            return offerName;
        }

        public void setOfferName(String offerName) {
            this.offerName = offerName;
        }

        public String getOfferDescription() {
            return offerDescription;
        }

        public void setOfferDescription(String offerDescription) {
            this.offerDescription = offerDescription;
        }

        public String getBeforeAmount() {
            return beforeAmount;
        }

        public void setBeforeAmount(String beforeAmount) {
            this.beforeAmount = beforeAmount;
        }

        public String getAfterAmount() {
            return afterAmount;
        }

        public void setAfterAmount(String afterAmount) {
            this.afterAmount = afterAmount;
        }

        public String getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(String discountRate) {
            this.discountRate = discountRate;
        }

        public String getRates() {
            return rates;
        }

        public void setRates(String rates) {
            this.rates = rates;
        }

        public String getReedeemDates() {
            return reedeemDates;
        }

        public void setReedeemDates(String reedeemDates) {
            this.reedeemDates = reedeemDates;
        }

        public String getOfferImg() {
            return offerImg;
        }

        public void setOfferImg(String offerImg) {
            this.offerImg = offerImg;
        }

        public String getPartner_img() {
            return partner_img;
        }

        public void setPartner_img(String partner_img) {
            this.partner_img = partner_img;
        }
    }

}



