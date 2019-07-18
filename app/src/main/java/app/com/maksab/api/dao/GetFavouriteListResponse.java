package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetFavouriteListResponse {

    private String responseMessage;
    private String responseCode;

    @SerializedName("user_favorite_product")
    private ArrayList<Product> productArrayList;

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

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public class Product {
        @SerializedName("Favorite_id")
        private String favoriteId;
        @SerializedName("Product_Id")
        private String productId;
        @SerializedName("Store_Id")
        private String storeId;
        @SerializedName("Product_Name")
        private String productName;
        @SerializedName("Product_description")
        private String productDescription;
        @SerializedName("Product_Category")
        private String productCategory;
        @SerializedName("Product_Store_Type")
        private String productStoreType;
        @SerializedName("Product_Price")
        private String productPrice;

        public String getFavoriteId() {
            return favoriteId;
        }

        public void setFavoriteId(String avoriteId) {
            this.favoriteId = avoriteId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public String getProductCategory() {
            return productCategory;
        }

        public void setProductCategory(String productCategory) {
            this.productCategory = productCategory;
        }

        public String getProductStoreType() {
            return productStoreType;
        }

        public void setProductStoreType(String productStoreType) {
            this.productStoreType = productStoreType;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }
    }
}
