package app.com.maksab.api.dao;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class BrandDetailsResponse {
    public String responseCode = "";
    public String message = "";
    @SerializedName("brandDetails")
    public BrandDetailsResponse.BrandDetails brandDetails;

    public BrandDetails getBrandDetails() {
        return brandDetails;
    }

    public void setBrandDetails(BrandDetails brandDetails) {
        this.brandDetails = brandDetails;
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

    public class BrandDetails {
        @SerializedName("brand_id")
        private String brandId;
        @SerializedName("brand_name")
        private String brandName;
        @SerializedName("brand_description")
        private String brandDescription;
        @SerializedName("brand_img")
        private String brandImg;
        @SerializedName("banner_img")
        private String bannerImg;
        @SerializedName("menu_file_download")
        private String menuFileDownload;
        private String favCount;
        private String favStatus;
        @SerializedName("images")
        private ArrayList<String> imagesListArrayList;
        @SerializedName("facilitys")
        private ArrayList<FacilityList> facilityListArrayList;
        @SerializedName("offers")
        private ArrayList<OfferList> offerListArrayList;
        @SerializedName("locations")
        private ArrayList<LocationList> locationListArrayList;

        public ArrayList<String> getImagesListArrayList() {
            return imagesListArrayList;
        }

        public void setImagesListArrayList(ArrayList<String> imagesListArrayList) {
            this.imagesListArrayList = imagesListArrayList;
        }

        public String getBrandDescription() {
            return brandDescription;
        }

        public void setBrandDescription(String brandDescription) {
            this.brandDescription = brandDescription;
        }

        public String getBannerImg() {
            return bannerImg;
        }

        public void setBannerImg(String bannerImg) {
            this.bannerImg = bannerImg;
        }

        public String getMenuFileDownload() {
            return menuFileDownload;
        }

        public void setMenuFileDownload(String menuFileDownload) {
            this.menuFileDownload = menuFileDownload;
        }

        public String getFavCount() {
            return favCount;
        }

        public void setFavCount(String favCount) {
            this.favCount = favCount;
        }

        public ArrayList<FacilityList> getFacilityListArrayList() {
            return facilityListArrayList;
        }

        public void setFacilityListArrayList(ArrayList<FacilityList> facilityListArrayList) {
            this.facilityListArrayList = facilityListArrayList;
        }

        public ArrayList<OfferList> getOfferListArrayList() {
            return offerListArrayList;
        }

        public void setOfferListArrayList(ArrayList<OfferList> offerListArrayList) {
            this.offerListArrayList = offerListArrayList;
        }

        public ArrayList<LocationList> getLocationListArrayList() {
            return locationListArrayList;
        }

        public void setLocationListArrayList(ArrayList<LocationList> locationListArrayList) {
            this.locationListArrayList = locationListArrayList;
        }

        public String getFavStatus() {
            return favStatus;
        }

        public void setFavStatus(String favStatus) {
            this.favStatus = favStatus;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandImg() {
            return brandImg;
        }

        public void setBrandImg(String brandImg) {
            this.brandImg = brandImg;
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
    }

    public class LocationList {
        @SerializedName("vender_name")
        private String venderName;
        @SerializedName("vender_location")
        private String venderLocation;
        private String contact;
        private String latitude;
        private String longitude;
        public LatLng center = new LatLng(24.677373419084528,46.845210681152366);//new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        public LatLng getCenter() {
            return center;
        }

        public void setCenter(LatLng center) {
            this.center = center;
        }

        @SuppressWarnings("unused")
        public LocationList() {}

        /*public LocationList(String venderName, String venderLocation, String contact, String
                latitude, String longitude) {
            this.venderName = venderName;
            this.venderLocation = venderLocation;
            this.contact = contact;
            this.latitude = latitude;
            this.longitude = longitude;
            this.center = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        }*/

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

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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
}
