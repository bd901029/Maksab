package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class ContactUsModel {
    @SerializedName("contact_name")
    public String contactName = "";
    @SerializedName("contact_email")
    public String contactEmail = "";
    @SerializedName("contact_subject")
    public String contactSubject = "";
    @SerializedName("contact_message")
    public String contactMessage = "";
    public String language = "";

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactSubject() {
        return contactSubject;
    }

    public void setContactSubject(String contactSubject) {
        this.contactSubject = contactSubject;
    }

    public String getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(String contactMessage) {
        this.contactMessage = contactMessage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
