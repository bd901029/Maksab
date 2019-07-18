package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class CityCatIdModel {
    public String language = "";
    @SerializedName("category_id")
    public String categoryId = "";
    @SerializedName("city_id")
    public String cityId = "";

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
