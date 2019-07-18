package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class FamilyModel {
    public String language = "";
    @SerializedName("user_id")
    public String userId = "";
    @SerializedName("email")
    public String email = "";
    @SerializedName("name")
    public String name="";

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
