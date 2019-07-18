package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class MemberIdModel {
    public String language = "";
    @SerializedName("member_id")
    public String memberId = "";

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
