package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.SerializedName;

public class OrderIdModel {
    public String language = "";
    @SerializedName("order_id")
    public String orderId = "";

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
