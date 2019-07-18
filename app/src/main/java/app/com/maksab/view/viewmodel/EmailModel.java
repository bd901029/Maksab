package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class EmailModel {
    public String language = "";
    public String email = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
