package app.com.maksab.api.dao;

import app.com.maksab.engine.offer.Offer;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class OfferListResponse {
    public String responseCode = "";
    public String message = "";
    private ArrayList<Offer> offerList;

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

    public ArrayList<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(ArrayList<Offer> offerList) {
        this.offerList = offerList;
    }
}
