package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class FavoriteOfferListResponse {
    private String message;
    private String responseCode;
    private ArrayList<OfferList> offerList;

    public ArrayList<OfferList> getOfferList() {
        return offerList;
    }

    public void setOfferList(ArrayList<OfferList> offerList) {
        this.offerList = offerList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public class OfferList {
        @SerializedName("offer_id")
        private String offerId;
        @SerializedName("offer_name")
        private String offerName;
        @SerializedName("offer_description")
        private String offerDescription;
        private String beforeAmount;
        private String afterAmount;
        private String discountRate;
        private String rates;
        private String bought;
        private String reaming;
        @SerializedName("offer_img")
        private String offerImg;
        @SerializedName("partner_img")
        private String partnerImg;
        private String favStatus;
        private String favCount;

        public String getPartnerImg() {
            return partnerImg;
        }

        public void setPartnerImg(String partnerImg) {
            this.partnerImg = partnerImg;
        }

        public String getFavStatus() {
            return favStatus;
        }

        public void setFavStatus(String favStatus) {
            this.favStatus = favStatus;
        }

        public String getFavCount() {
            return favCount;
        }

        public void setFavCount(String favCount) {
            this.favCount = favCount;
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

        public String getBought() {
            return bought;
        }

        public void setBought(String bought) {
            this.bought = bought;
        }

        public String getReaming() {
            return reaming;
        }

        public void setReaming(String reaming) {
            this.reaming = reaming;
        }

        public String getOfferImg() {
            return offerImg;
        }

        public void setOfferImg(String offerImg) {
            this.offerImg = offerImg;
        }
    }
}
