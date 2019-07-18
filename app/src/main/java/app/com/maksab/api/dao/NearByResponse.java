package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NearByResponse {
    private String message;
    private String responseCode;
    @SerializedName("brandData")
    private ArrayList<BrandData> brandDataArrayList;

    public ArrayList<BrandData> getBrandDataArrayList() {
        return brandDataArrayList;
    }

    public void setBrandDataArrayList(ArrayList<BrandData> brandDataArrayList) {
        this.brandDataArrayList = brandDataArrayList;
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

    public class BrandData {
        @SerializedName("brand_id")
        private String brandId;
        @SerializedName("brand_name")
        private String brandName;
        private String longitude;
        private String latitude;
        @SerializedName("brand_img")
        private String brandImg;
        @SerializedName("map_marker")
        private String mapMarker;

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

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getBrandImg() {
            return brandImg;
        }

        public void setBrandImg(String brandImg) {
            this.brandImg = brandImg;
        }

        public String getMapMarker() {
            return mapMarker;
        }

        public void setMapMarker(String mapMarker) {
            this.mapMarker = mapMarker;
        }
    }
}
