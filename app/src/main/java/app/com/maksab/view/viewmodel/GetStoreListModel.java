package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class GetStoreListModel {

    public static final String TYPE_GROCERIES = "1";
    public static final String TYPE_NEAR_BY = "2";
    public static final String TYPE_FAVORITES = "3";
    public static final String TYPE_NOTIFICATION = "4";
    public static final String TYPE_PROFILE = "5";

    @SerializedName("store_type_id")
    private String storeTypeId;         //  1=Groceries,2=Coffee and Bakeries,3=Pharmacies,4=Restaurants

    public String getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(String storeTypeId) {
        this.storeTypeId = storeTypeId;
    }
}
