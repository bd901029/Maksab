package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class OfferListResponse {
    public String responseCode = "";
    public String message = "";
    private ArrayList<OfferList> offerList;

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

    public ArrayList<OfferListResponse.OfferList> getOfferList() {
        return offerList;
    }

    public void setOfferList(ArrayList<OfferListResponse.OfferList> offerList) {
        this.offerList = offerList;
    }

    public class OfferList {
        @SerializedName("offer_id")
        public String offerId;
        @SerializedName("offer_name")
        public String offerName;
        @SerializedName("offer_img")
        public String offerImg = "";
        @SerializedName("partner_img")
        public String partnerImg = "";
        @SerializedName("offer_description")
        public String offerDescription = "";
        public String beforeAmount = "";
        public String afterAmount = "";
        public String discountRate = "";
        public String rates = "";
        public String bought = "";
        public String reaming = "";
        public String favStatus = "";
        public String favCount = "";


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

        public String getOfferImg() {
            return offerImg;
        }

        public void setOfferImg(String offerImg) {
            this.offerImg = offerImg;
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
    }


}
