package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfferListModel {
    public String language = "";
    public String query = "";
    @SerializedName("city_id")
    public String cityId = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("category_id")
    public String categoryId = "";
    @SerializedName("sub_category_id")
    public ArrayList<String> subCategoryId = null;
    public ArrayList<String> locations = null;
    @SerializedName("min_price")
    public String minPrice = "";
    @SerializedName("max_price")
    public String maxPrice = "";
    @SerializedName("sort_by")
    public String sortBy = "";

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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public ArrayList<String> getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(ArrayList<String> subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public ArrayList<String> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }
}
