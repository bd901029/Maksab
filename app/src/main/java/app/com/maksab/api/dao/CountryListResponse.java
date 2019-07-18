package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class CountryListResponse {

    private String responseMessage;
    private String responseCode;
    @SerializedName("resultList")
    private ArrayList<resultList> resultList;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<CountryListResponse.resultList> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<CountryListResponse.resultList> resultList) {
        this.resultList = resultList;
    }

    public class resultList {
        @SerializedName("country_id")
        private String countryId;
        private String shortname;
        private String name;
        private String phonecode;

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(String phonecode) {
            this.phonecode = phonecode;
        }
    }
}
