package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class OfferDetailsResponse {
    public String responseCode = "";
    public String message = "";
    @SerializedName("offerDetails")
    public OfferDetailsResponse.OfferDetails offerDetails;
    @SerializedName("venderLocations")
    private ArrayList<VenderLocationList> venderLocationLists;
    @SerializedName("otherOffer")
    private ArrayList<OtherOfferList> otherOfferListArrayList;

    public ArrayList<OtherOfferList> getOtherOfferListArrayList() {
        return otherOfferListArrayList;
    }

    public void setOtherOfferListArrayList(ArrayList<OtherOfferList> otherOfferListArrayList) {
        this.otherOfferListArrayList = otherOfferListArrayList;
    }

    public ArrayList<VenderLocationList> getVenderLocationLists() {
        return venderLocationLists;
    }

    public void setVenderLocationLists(ArrayList<VenderLocationList> venderLocationLists) {
        this.venderLocationLists = venderLocationLists;
    }

    public OfferDetails getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(OfferDetails offerDetails) {
        this.offerDetails = offerDetails;
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

    public class OfferDetails {
        @SerializedName("offer_id")
        private String offerId;
        @SerializedName("partner_id")
        private String partnerId;
        @SerializedName("offer_name")
        private String offerName;
        @SerializedName("offer_description")
        private String offerDescription;
        @SerializedName("partner_name")
        private String partnerName;
        @SerializedName("sub_category_name")
        private String subCategoryName;
        @SerializedName("category_name")
        private String categoryName;
        private String reservation;
        @SerializedName("package")
        private String srtPackage;
        private String instructions;
        @SerializedName("call_number")
        private String callNumber;
        @SerializedName("discound_end")
        private String discoundEnd;
        @SerializedName("partner_img")
        private String partnerImg;
        @SerializedName("banner_img")
        private String bannerImg;
        private String favStatus;
        private String favCount;
        private String partnerFavStatus;
        private String partnerFavCount;
        private String partner_rates;
        @SerializedName("partner_offers")
        private String partnerOffers;
        private String beforeAmount;
        private String afterAmount;
        private String discountRate;
        private String savingAmount;
        private String redeemedMsg;
        private String redeemedStatus;
        @SerializedName("offer_reaming_day")
        private String offerReamingDay;
        @SerializedName("offer_link")
        private String offerLink;




        @SerializedName("share_title")
        private String shareTitle;
        @SerializedName("share_logo")
        private String shareLogo;

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareLogo() {
            return shareLogo;
        }

        public void setShareLogo(String shareLogo) {
            this.shareLogo = shareLogo;
        }

        @SerializedName("images")
        private ArrayList<String> imagesListArrayList;
        @SerializedName("facilitys")
        private ArrayList<FacilityList> facilityListArrayList;
        @SerializedName("reviews")
        private ArrayList<ReviewsList> reviewsListArrayList;

        public String getCallNumber() {
            return callNumber;
        }

        public void setCallNumber(String callNumber) {
            this.callNumber = callNumber;
        }

        public String getOfferLink() {
            return offerLink;
        }

        public void setOfferLink(String offerLink) {
            this.offerLink = offerLink;
        }

        public ArrayList<ReviewsList> getReviewsListArrayList() {
            return reviewsListArrayList;
        }

        public void setReviewsListArrayList(ArrayList<ReviewsList> reviewsListArrayList) {
            this.reviewsListArrayList = reviewsListArrayList;
        }

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
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

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getReservation() {
            return reservation;
        }

        public void setReservation(String reservation) {
            this.reservation = reservation;
        }

        public String getSrtPackage() {
            return srtPackage;
        }

        public void setSrtPackage(String srtPackage) {
            this.srtPackage = srtPackage;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public String getDiscoundEnd() {
            return discoundEnd;
        }

        public void setDiscoundEnd(String discoundEnd) {
            this.discoundEnd = discoundEnd;
        }

        public String getPartnerImg() {
            return partnerImg;
        }

        public void setPartnerImg(String partnerImg) {
            this.partnerImg = partnerImg;
        }

        public String getBannerImg() {
            return bannerImg;
        }

        public void setBannerImg(String bannerImg) {
            this.bannerImg = bannerImg;
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

        public String getPartnerFavStatus() {
            return partnerFavStatus;
        }

        public void setPartnerFavStatus(String partnerFavStatus) {
            this.partnerFavStatus = partnerFavStatus;
        }

        public String getPartnerFavCount() {
            return partnerFavCount;
        }

        public void setPartnerFavCount(String partnerFavCount) {
            this.partnerFavCount = partnerFavCount;
        }

        public String getPartner_rates() {
            return partner_rates;
        }

        public void setPartner_rates(String partner_rates) {
            this.partner_rates = partner_rates;
        }

        public String getPartnerOffers() {
            return partnerOffers;
        }

        public void setPartnerOffers(String partnerOffers) {
            this.partnerOffers = partnerOffers;
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

        public String getSavingAmount() {
            return savingAmount;
        }

        public void setSavingAmount(String savingAmount) {
            this.savingAmount = savingAmount;
        }

        public String getRedeemedMsg() {
            return redeemedMsg;
        }

        public void setRedeemedMsg(String redeemedMsg) {
            this.redeemedMsg = redeemedMsg;
        }

        public String getRedeemedStatus() {
            return redeemedStatus;
        }

        public void setRedeemedStatus(String redeemedStatus) {
            this.redeemedStatus = redeemedStatus;
        }

        public String getOfferReamingDay() {
            return offerReamingDay;
        }

        public void setOfferReamingDay(String offerReamingDay) {
            this.offerReamingDay = offerReamingDay;
        }

        public ArrayList<String> getImagesListArrayList() {
            return imagesListArrayList;
        }

        public void setImagesListArrayList(ArrayList<String> imagesListArrayList) {
            this.imagesListArrayList = imagesListArrayList;
        }

        public ArrayList<FacilityList> getFacilityListArrayList() {
            return facilityListArrayList;
        }

        public void setFacilityListArrayList(ArrayList<FacilityList> facilityListArrayList) {
            this.facilityListArrayList = facilityListArrayList;
        }
    }

    public class FacilityList {
        @SerializedName("facility_img")
        private String facilityImg;
        @SerializedName("facility_name")
        private String facilityName;

        public String getFacilityImg() {
            return facilityImg;
        }

        public void setFacilityImg(String facilityImg) {
            this.facilityImg = facilityImg;
        }

        public String getFacilityName() {
            return facilityName;
        }

        public void setFacilityName(String facilityName) {
            this.facilityName = facilityName;
        }
    }

    public class ReviewsList {
        private String profilePic;
        @SerializedName("user_name")
        private String userName;
        private String comment;
        private String rate;
        private String reviewTime;

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getReviewTime() {
            return reviewTime;
        }

        public void setReviewTime(String reviewTime) {
            this.reviewTime = reviewTime;
        }
    }

    public class VenderLocationList {
        @SerializedName("vender_name")
        private String venderName;
        @SerializedName("vender_location")
        private String venderLocation;
        private String latitude;
        private String longitude;

        public String getVenderName() {
            return venderName;
        }

        public void setVenderName(String venderName) {
            this.venderName = venderName;
        }

        public String getVenderLocation() {
            return venderLocation;
        }

        public void setVenderLocation(String venderLocation) {
            this.venderLocation = venderLocation;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }

    public class OtherOfferList {
        @SerializedName("offer_id")
        public String offerId = "";
        @SerializedName("offer_name")
        public String offerName = "";
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

        public String getPartnerImg() {
            return partnerImg;
        }

        public void setPartnerImg(String partnerImg) {
            this.partnerImg = partnerImg;
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
    }
}
