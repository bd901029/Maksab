package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class ProductDetailResponse {
    public String responseCode = "";
    public String message = "";
    @SerializedName("resultList")
    public ProductDetailResponse.ProductDetail resultList;

    public ProductDetail getResultList() {
        return resultList;
    }

    public void setResultList(ProductDetail resultList) {
        this.resultList = resultList;
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

    public class ProductDetail {
        @SerializedName("product_id")
        public String productId;
        @SerializedName("product_name")
        public String productName;
        public String price = "";
        @SerializedName("category_id")
        public String categoryId = "";
        @SerializedName("category_name")
        public String  categoryName = "";
        @SerializedName("store_id")
        public String storeId = "";
        @SerializedName("product_description")
        public String productDescription = "";
        @SerializedName("store_name")
        public String store_name = "";
        @SerializedName("product_image")
        public String productImage = "";
        @SerializedName("store_type")
        public String storeType = "";

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
    }
}
