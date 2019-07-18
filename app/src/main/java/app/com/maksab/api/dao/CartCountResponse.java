package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 1/24/2018.
 */

public class CartCountResponse {
    private String responseCode;
    private String message;
    @SerializedName("cart_count")
    private String cartCount;

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
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
