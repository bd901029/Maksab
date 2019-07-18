package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyCartResponse {

    private String responseMessage;
    private String responseCode;

    @SerializedName("user_favorite_product")
    private ArrayList<Cart> cartArrayList;

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

    public ArrayList<Cart> getCartArrayList() {
        return cartArrayList;
    }

    public void setCartArrayList(ArrayList<Cart> cartArrayList) {
        this.cartArrayList = cartArrayList;
    }

    public class Cart {
        @SerializedName("Cart_id")
        private String Cart_id;
        @SerializedName("Product_Id")
        private String Product_Id;
        @SerializedName("Store_Id")
        private String Store_Id;
        @SerializedName("Product_Name")
        private String Product_Name;
        @SerializedName("Product_description")
        private String Product_description;
        @SerializedName("Product_Category")
        private String Product_Category;
        @SerializedName("Product_Store_Type")
        private String Product_Store_Type;
        @SerializedName("Product_Price")
        private String Product_Price;
        @SerializedName("Product_Image")
        private String Product_Image;

        public String getCart_id() {
            return Cart_id;
        }

        public void setCart_id(String cart_id) {
            Cart_id = cart_id;
        }

        public String getProduct_Id() {
            return Product_Id;
        }

        public void setProduct_Id(String product_Id) {
            Product_Id = product_Id;
        }

        public String getStore_Id() {
            return Store_Id;
        }

        public void setStore_Id(String store_Id) {
            Store_Id = store_Id;
        }

        public String getProduct_Name() {
            return Product_Name;
        }

        public void setProduct_Name(String product_Name) {
            Product_Name = product_Name;
        }

        public String getProduct_description() {
            return Product_description;
        }

        public void setProduct_description(String product_description) {
            Product_description = product_description;
        }

        public String getProduct_Category() {
            return Product_Category;
        }

        public void setProduct_Category(String product_Category) {
            Product_Category = product_Category;
        }

        public String getProduct_Store_Type() {
            return Product_Store_Type;
        }

        public void setProduct_Store_Type(String product_Store_Type) {
            Product_Store_Type = product_Store_Type;
        }

        public String getProduct_Price() {
            return Product_Price;
        }

        public void setProduct_Price(String product_Price) {
            Product_Price = product_Price;
        }

        public String getProduct_Image() {
            return Product_Image;
        }

        public void setProduct_Image(String product_Image) {
            Product_Image = product_Image;
        }
    }
}
