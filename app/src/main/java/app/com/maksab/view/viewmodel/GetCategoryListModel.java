package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class GetCategoryListModel {
    @SerializedName("store_id")
    private String storeId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
