package app.com.maksab.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class StoreDetailsResponse implements Parcelable {
    public String responseCode = "";
    public String message = "";
    @SerializedName("resultList")

    public StoreDetailsResponse.storeDetails storeDetails;

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

    public StoreDetailsResponse.storeDetails getStoreDetails() {
        return storeDetails;
    }

    public void setStoreDetails(StoreDetailsResponse.storeDetails storeDetails) {
        this.storeDetails = storeDetails;
    }

    public static class storeDetails implements Parcelable {
        @SerializedName("store_id")
        public String storeId;
        @SerializedName("store_name")
        public String storeName;
        public String name = "";
        public String mobile = "";
        public String email = "";
        @SerializedName("store_activity")
        public String storeActivity = "";
        public String shipping = "";
        @SerializedName("store_description")
        public String  storeDescription = "";
        @SerializedName("store_city")
        public String storeCity = "";
        @SerializedName("country_name")
        public String countryName = "";
        @SerializedName("store_pic")
        public String storePic = "";
        @SerializedName("products_list")
        public ArrayList<products> productsList;

        public ArrayList<products> getProductsList() {
            return productsList;
        }

        public void setProductsList(ArrayList<products> productsList) {
            this.productsList = productsList;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStoreActivity() {
            return storeActivity;
        }

        public void setStoreActivity(String storeActivity) {
            this.storeActivity = storeActivity;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
        }

        public String getStoreDescription() {
            return storeDescription;
        }

        public void setStoreDescription(String storeDescription) {
            this.storeDescription = storeDescription;
        }

        public String getStoreCity() {
            return storeCity;
        }

        public void setStoreCity(String storeCity) {
            this.storeCity = storeCity;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getStorePic() {
            return storePic;
        }

        public void setStorePic(String storePic) {
            this.storePic = storePic;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.storeId);
            dest.writeString(this.storeName);
            dest.writeString(this.name);
            dest.writeString(this.mobile);
            dest.writeString(this.email);
            dest.writeString(this.storeActivity);
            dest.writeString(this.shipping);
            dest.writeString(this.storeDescription);
            dest.writeString(this.storeCity);
            dest.writeString(this.countryName);
            dest.writeString(this.storePic);
            dest.writeList(this.productsList);
        }

        public storeDetails() {
        }

        protected storeDetails(Parcel in) {
            this.storeId = in.readString();
            this.storeName = in.readString();
            this.name = in.readString();
            this.mobile = in.readString();
            this.email = in.readString();
            this.storeActivity = in.readString();
            this.shipping = in.readString();
            this.storeDescription = in.readString();
            this.storeCity = in.readString();
            this.countryName = in.readString();
            this.storePic = in.readString();
            this.productsList = new ArrayList<products>();
            in.readList(this.productsList, products.class.getClassLoader());
        }

        public static final Creator<storeDetails> CREATOR = new Creator<storeDetails>() {
            @Override
            public storeDetails createFromParcel(Parcel source) {
                return new storeDetails(source);
            }

            @Override
            public storeDetails[] newArray(int size) {
                return new storeDetails[size];
            }
        };
    }

    public static class products implements Parcelable {
        @SerializedName("product_id")
        public String productId = "";
        public String image = "";

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.productId);
            dest.writeString(this.image);
        }

        public products() {
        }

        protected products(Parcel in) {
            this.productId = in.readString();
            this.image = in.readString();
        }

        public static final Creator<products> CREATOR = new Creator<products>() {
            @Override
            public products createFromParcel(Parcel source) {
                return new products(source);
            }

            @Override
            public products[] newArray(int size) {
                return new products[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.responseCode);
        dest.writeString(this.message);
        dest.writeParcelable(this.storeDetails, flags);
    }

    public StoreDetailsResponse() {
    }

    protected StoreDetailsResponse(Parcel in) {
        this.responseCode = in.readString();
        this.message = in.readString();
        this.storeDetails = in.readParcelable(StoreDetailsResponse.storeDetails.class.getClassLoader());
    }

    public static final Creator<StoreDetailsResponse> CREATOR = new Creator<StoreDetailsResponse>() {
        @Override
        public StoreDetailsResponse createFromParcel(Parcel source) {
            return new StoreDetailsResponse(source);
        }

        @Override
        public StoreDetailsResponse[] newArray(int size) {
            return new StoreDetailsResponse[size];
        }
    };
}
