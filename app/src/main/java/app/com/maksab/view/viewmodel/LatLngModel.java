package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LatLngModel {
    public String language = "";
    public String latitude = "";
    public String longitude = "";
    @SerializedName("category_id")
    private ArrayList<String> categoryId = new ArrayList<>();

    public ArrayList<String> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ArrayList<String> categoryId) {
        this.categoryId = categoryId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
