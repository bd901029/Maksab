package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class OfferFilterResponse {
    public ArrayList<SortByList> getSortByList() {
        return sortByList;
    }

    public void setSortByList(ArrayList<SortByList> sortByList) {
        this.sortByList = sortByList;
    }

    private String responseMessage;
    private String responseCode;
    @SerializedName("min_price")
    private String minPrice;
    @SerializedName("max_price")
    private String maxPrice;
    @SerializedName("sub_category_list")
    private ArrayList<SubCategoryList> subCategoryList;
    @SerializedName("location_list")
    private ArrayList<LocationList> locationList;
    @SerializedName("sort_by")
    private ArrayList<SortByList> sortByList;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public ArrayList<SubCategoryList> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<SubCategoryList> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public ArrayList<LocationList> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<LocationList> locationList) {
        this.locationList = locationList;
    }


    public class SubCategoryList {
        @SerializedName("sub_category_id")
        private String subCategoryId;
        @SerializedName("sub_category_name")
        private String subCategoryName;
        private boolean status = false;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }
    }

    public class LocationList {
        @SerializedName("location_id")
        private String locationId;
        private String location;
        private boolean status = false;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }


    public class SortByList {
        @SerializedName("sort_id")
        private String sortId;
        @SerializedName("sort_text")
        private String sortText;

        public String getSortId() {
            return sortId;
        }

        public void setSortId(String sortId) {
            this.sortId = sortId;
        }

        public String getSortText() {
            return sortText;
        }

        public void setSortText(String sortText) {
            this.sortText = sortText;
        }
    }
}
