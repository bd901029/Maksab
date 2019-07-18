package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FaqModel implements Serializable {

    @SerializedName("faqList")
    @Expose
    private ArrayList<FaqList> faqList = null;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<FaqList> getFaqList() {
        return faqList;
    }

    public void setFaqList(ArrayList<FaqList> faqList) {
        this.faqList = faqList;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class FaqList implements Serializable {

        @SerializedName("faq_id")
        @Expose
        private String faqId;
        @SerializedName("faqTitle")
        @Expose
        private String faqTitle;
        @SerializedName("faqDescription")
        @Expose
        private String faqDescription;

        public String getFaqId() {
            return faqId;
        }

        public void setFaqId(String faqId) {
            this.faqId = faqId;
        }

        public String getFaqTitle() {
            return faqTitle;
        }

        public void setFaqTitle(String faqTitle) {
            this.faqTitle = faqTitle;
        }

        public String getFaqDescription() {
            return faqDescription;
        }

        public void setFaqDescription(String faqDescription) {
            this.faqDescription = faqDescription;
        }

    }

}



