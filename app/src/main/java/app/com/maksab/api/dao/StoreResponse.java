package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoreResponse {
    private String message;
    private String responseCode;
    private ArrayList<Category> resultList;

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

    public ArrayList<Category> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<Category> resultList) {
        this.resultList = resultList;
    }

    public class Category {
        @SerializedName("store_id")
        private String storeId;
        @SerializedName("store_name")
        private String storeName;
        private String name;
        private String mobile;
        private String email;
        private String shipping;
        @SerializedName("store_activity")
        private String storeActivity;
        @SerializedName("store_city")
        private String storeCity;
        @SerializedName("country_name")
        private String countryName;
        @SerializedName("store_pic")
        private String storePic;

        public String getStorePic() {
            return storePic;
        }

        public void setStorePic(String storePic) {
            this.storePic = storePic;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
        }

        public String getStoreActivity() {
            return storeActivity;
        }

        public void setStoreActivity(String storeActivity) {
            this.storeActivity = storeActivity;
        }

        public String getStoreCity() {
            return storeCity;
        }

        public void setStoreCity(String storeCity) {
            this.storeCity = storeCity;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }
    }
}
