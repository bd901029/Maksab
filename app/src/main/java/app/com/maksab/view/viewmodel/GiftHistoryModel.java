package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GiftHistoryModel implements Serializable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("gifts")
    @Expose
    private ArrayList<Gift> gifts = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(ArrayList<Gift> gifts) {
        this.gifts = gifts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Gift implements Serializable{

        @SerializedName("gift_id")
        @Expose
        private String giftId;
        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("send_gift_date")
        @Expose
        private String sendGiftDate;
        @SerializedName("recieverName")
        @Expose
        private String recieverName;
        @SerializedName("recieverEmail")
        @Expose
        private String recieverEmail;
        @SerializedName("offer_name")
        @Expose
        private String offerName;
        @SerializedName("offer_description")
        @Expose
        private String offerDescription;
        @SerializedName("partner_name")
        @Expose
        private String partnerName;
        @SerializedName("beforeAmount")
        @Expose
        private String beforeAmount;
        @SerializedName("afterAmount")
        @Expose
        private String afterAmount;
        @SerializedName("discountRate")
        @Expose
        private String discountRate;
        @SerializedName("offer_img")
        @Expose
        private String offerImg;

        public String getGiftId() {
            return giftId;
        }

        public void setGiftId(String giftId) {
            this.giftId = giftId;
        }

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getSendGiftDate() {
            return sendGiftDate;
        }

        public void setSendGiftDate(String sendGiftDate) {
            this.sendGiftDate = sendGiftDate;
        }

        public String getRecieverName() {
            return recieverName;
        }

        public void setRecieverName(String recieverName) {
            this.recieverName = recieverName;
        }

        public String getRecieverEmail() {
            return recieverEmail;
        }

        public void setRecieverEmail(String recieverEmail) {
            this.recieverEmail = recieverEmail;
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

        public String getPartnerName() {
            return partnerName;
        }

        public void setPartnerName(String partnerName) {
            this.partnerName = partnerName;
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

        public String getOfferImg() {
            return offerImg;
        }

        public void setOfferImg(String offerImg) {
            this.offerImg = offerImg;
        }

    }

}
