package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class HomeDataResponse {
    public String responseCode = "";
    public String message = "";

    @SerializedName("slider_images")
    private ArrayList<SliderImages> sliderImages;
    private ArrayList<BrandData> brandData;
    private ArrayList<CollectionsData> collectionsData;
    private ArrayList<TrendingData> trendingData;
    private ArrayList<HotdealData> hotdealData;
    private ArrayList<NewdealData> newdealData;
    private ArrayList<CategoryData> categoryData;
    private ArrayList<SubcategoryData> subcategoryData;

    public ArrayList<SliderImages> getSliderImages() {
        return sliderImages;
    }

    public void setSliderImages(ArrayList<SliderImages> sliderImages) {
        this.sliderImages = sliderImages;
    }

    public ArrayList<BrandData> getBrandData() {
        return brandData;
    }

    public void setBrandData(ArrayList<BrandData> brandData) {
        this.brandData = brandData;
    }

    public ArrayList<CollectionsData> getCollectionsData() {
        return collectionsData;
    }

    public void setCollectionsData(ArrayList<CollectionsData> collectionsData) {
        this.collectionsData = collectionsData;
    }

    public ArrayList<TrendingData> getTrendingData() {
        return trendingData;
    }

    public void setTrendingData(ArrayList<TrendingData> trendingData) {
        this.trendingData = trendingData;
    }

    public ArrayList<HotdealData> getHotdealData() {
        return hotdealData;
    }

    public void setHotdealData(ArrayList<HotdealData> hotdealData) {
        this.hotdealData = hotdealData;
    }

    public ArrayList<NewdealData> getNewdealData() {
        return newdealData;
    }

    public void setNewdealData(ArrayList<NewdealData> newdealData) {
        this.newdealData = newdealData;
    }

    public ArrayList<CategoryData> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(ArrayList<CategoryData> categoryData) {
        this.categoryData = categoryData;
    }

    public ArrayList<SubcategoryData> getSubcategoryData() {
        return subcategoryData;
    }

    public void setSubcategoryData(ArrayList<SubcategoryData> subcategoryData) {
        this.subcategoryData = subcategoryData;
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


    public class SliderImages {
        @SerializedName("slider_id")
        public String sliderId;
        @SerializedName("banner_image")
        public String bannerImage;
        @SerializedName("banner_text")
        public String bannerText = "";

        public String getSliderId() {
            return sliderId;
        }

        public void setSliderId(String sliderId) {
            this.sliderId = sliderId;
        }

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        public String getBannerText() {
            return bannerText;
        }

        public void setBannerText(String bannerText) {
            this.bannerText = bannerText;
        }
    }

    public class BrandData {
        @SerializedName("brand_id")
        public String brandId;
        @SerializedName("brand_name")
        public String brandName;
        @SerializedName("brand_img")
        public String brandImg = "";

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

    public class CollectionsData {
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

    public class TrendingData {
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

    public class HotdealData {
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

    public class NewdealData {
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
    }

    public class CategoryData {
        @SerializedName("category_name")
        public String categoryName;
        @SerializedName("category_id")
        public String categoryId;
        private ArrayList<Offers> offers;

        public ArrayList<HomeDataResponse.Offers> getOffers() {
            return offers;
        }

        public void setOffers(ArrayList<HomeDataResponse.Offers> offers) {
            this.offers = offers;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
    }

    public class SubcategoryData {
        @SerializedName("sub_category_name")
        public String subCategoryName;
        @SerializedName("subcategory_id")
        public String subcategoryId;
        private ArrayList<Offers> offers;

        public ArrayList<Offers> getOffers() {
            return offers;
        }

        public void setOffers(ArrayList<Offers> offers) {
            this.offers = offers;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(String subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

    }

    public class Offers {
        @SerializedName("offer_id")
        public String offerId;
        @SerializedName("offer_name")
        public String offerName;
        @SerializedName("offer_img")
        public String offerImg = "";
        @SerializedName("offer_description")
        public String offerDescription = "";
        public String beforeAmount = "";
        public String afterAmount = "";
        public String discountRate = "";
        public String rates = "";
        public String bought = "";
        public String reaming = "";
        @SerializedName("partner_img")
        public String partnerImg = "";
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
