package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationFilterResponse {
    private String message;
    private String responseCode;
    @SerializedName("category_list")
    private ArrayList<CategoryList> categoryList;

    public ArrayList<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<CategoryList> categoryList) {
        this.categoryList = categoryList;
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

    public class CategoryList {
        @SerializedName("category_id")
        private String categoryId;
        @SerializedName("category_name")
        private String categoryName;
        @SerializedName("category_img")
        private String categoryImg;
        @SerializedName("category_status")
        private String categoryStatus;

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

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

        public String getCategoryStatus() {
            return categoryStatus;
        }

        public void setCategoryStatus(String categoryStatus) {
            this.categoryStatus = categoryStatus;
        }
    }
}
