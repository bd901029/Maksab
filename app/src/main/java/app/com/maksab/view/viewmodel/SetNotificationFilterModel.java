package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SetNotificationFilterModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("catogorys_id")
    private ArrayList<String> catogorysId = new ArrayList<>();

    public ArrayList<String> getCatogorysId() {
        return catogorysId;
    }

    public void setCatogorysId(ArrayList<String> catogorysId) {
        this.catogorysId = catogorysId;
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
