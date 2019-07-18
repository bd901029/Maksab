package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class PreviousApplicationResponse {
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
        @SerializedName("product_id")
        private String productId;
        @SerializedName("product_name")
        private String productName;
        @SerializedName("user_name")
        private String userName;
        @SerializedName("user_id")
        private String userId;
        @SerializedName("category_id")
        private String categoryId;
        @SerializedName("category_name")
        private String categoryName;
        private String price;
        @SerializedName("store_id")
        private String storeId;
        @SerializedName("product_description")
        private String productDescription;
        @SerializedName("store_name")
        private String storeName;
        @SerializedName("product_image")
        private String productImage;
        @SerializedName("create_date")
        private String createDate;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
    }
}
