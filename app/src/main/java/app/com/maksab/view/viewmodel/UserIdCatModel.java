package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class UserIdCatModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("category_id")
    public String categoryId = "";

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
