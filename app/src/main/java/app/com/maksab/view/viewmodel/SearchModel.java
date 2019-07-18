package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class SearchModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("city_id")
    public String cityId = "";
    public String search = "";

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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
