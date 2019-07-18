package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FavoritePartnerListResponse {
    private String message;
    private String responseCode;
    private ArrayList<BrandDataList> brandData;

    public ArrayList<BrandDataList> getBrandData() {
        return brandData;
    }

    public void setBrandData(ArrayList<BrandDataList> brandData) {
        this.brandData = brandData;
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

    public class BrandDataList {
        @SerializedName("brand_id")
        private String brandId;
        @SerializedName("brand_name")
        private String brandName;
        @SerializedName("brand_img")
        private String brandImg;
        private String favStatus;

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
}
