package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class CityListResponse {

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

    public ArrayList<CityListResponse.resultList> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<CityListResponse.resultList> resultList) {
        this.resultList = resultList;
    }

    public class resultList {
        @SerializedName("city_id")
        private String cityId;
        private String name;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
