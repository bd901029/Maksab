package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 1/24/2018.
 */

public class TcResponse {
    private String responseCode;
    private String message;
    @SerializedName("terms_ab")
    private String termsAb;
    @SerializedName("terms_tr")
    private String termsTr;
    @SerializedName("terms_en")
    private String termsEn;

    public String getTermsAb() {
        return termsAb;
    }

    public void setTermsAb(String termsAb) {
        this.termsAb = termsAb;
    }

    public String getTermsTr() {
        return termsTr;
    }

    public void setTermsTr(String termsTr) {
        this.termsTr = termsTr;
    }

    public String getTermsEn() {
        return termsEn;
    }

    public void setTermsEn(String termsEn) {
        this.termsEn = termsEn;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
