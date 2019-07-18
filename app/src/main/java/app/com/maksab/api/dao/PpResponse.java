package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 1/24/2018.
 */

public class PpResponse {
    private String responseCode;
    private String message;
    @SerializedName("ab_policy")
    private String abPolicy;
    @SerializedName("en_policy")
    private String enPolicy;
    @SerializedName("tr_policy")
    private String trPolicy;

    public String getAbPolicy() {
        return abPolicy;
    }

    public void setAbPolicy(String abPolicy) {
        this.abPolicy = abPolicy;
    }

    public String getEnPolicy() {
        return enPolicy;
    }

    public void setEnPolicy(String enPolicy) {
        this.enPolicy = enPolicy;
    }

    public String getTrPolicy() {
        return trPolicy;
    }

    public void setTrPolicy(String trPolicy) {
        this.trPolicy = trPolicy;
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
