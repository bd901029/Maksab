package app.com.maksab.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductListResponse {

    private String responseMessage;
    private String responseCode;
    @SerializedName("stor_category_product_data")
    private ArrayList<Product> productArrayList;

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

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public static class Product implements Parcelable {
        @SerializedName("Product_Id")
        private String productId;
        @SerializedName("Store_Id")
        private String storeId;
        @SerializedName("Product_Name")
        private String productName;
        @SerializedName("Product_description")
        private String productDescription;
        @SerializedName("Product_Category")
        private String productCategory;
        @SerializedName("Product_Store_Type")
        private String productStoreType;
        @SerializedName("Product_Price")
        private String productPrice;
        @SerializedName("Product_Image")
        private String productImage;
        @SerializedName("favorite")
        private String favorite;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public String getProductCategory() {
            return productCategory;
        }

        public void setProductCategory(String productCategory) {
            this.productCategory = productCategory;
        }

        public String getProductStoreType() {
            return productStoreType;
        }

        public void setProductStoreType(String productStoreType) {
            this.productStoreType = productStoreType;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.productId);
            dest.writeString(this.storeId);
            dest.writeString(this.productName);
            dest.writeString(this.productDescription);
            dest.writeString(this.productCategory);
            dest.writeString(this.productStoreType);
            dest.writeString(this.productPrice);
            dest.writeString(this.productImage);
            dest.writeString(this.favorite);
        }

        public Product() {
        }

        protected Product(Parcel in) {
            this.productId = in.readString();
            this.storeId = in.readString();
            this.productName = in.readString();
            this.productDescription = in.readString();
            this.productCategory = in.readString();
            this.productStoreType = in.readString();
            this.productPrice = in.readString();
            this.productImage = in.readString();
            this.favorite = in.readString();
        }

        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel source) {
                return new Product(source);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };
    }
}