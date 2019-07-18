package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class RequestDetailResponse {
    public String responseCode = "";
    public String message = "";
    @SerializedName("resultList")
    public RequestDetailResponse.ProductDetail resultList;

    public ProductDetail getResultList() {
        return resultList;
    }

    public void setResultList(ProductDetail resultList) {
        this.resultList = resultList;
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

    public class ProductDetail {
        @SerializedName("product_id")
        public String productId;
        @SerializedName("user_id")
        public String userId;
        @SerializedName("first_name")
        public String firstName = "";
        @SerializedName("last_name")
        public String  lastName = "";
        public String phone = "";
        @SerializedName("country_name")
        public String countryName = "";
        public String address = "";
        @SerializedName("store_id")
        public String storeId = "";
        public String price = "";
        @SerializedName("product_image")
        public String productImage = "";
        public String product_name = "";
        public String description = "";

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
    }
}
