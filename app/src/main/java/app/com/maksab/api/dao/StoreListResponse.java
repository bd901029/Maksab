package app.com.maksab.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoreListResponse {

    private String responseMessage;
    private String responseCode;

    @SerializedName("stordata")
    private ArrayList<Store> storeArrayList;

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

    public ArrayList<Store> getStoreArrayList() {
        return storeArrayList;
    }

    public void setStoreArrayList(ArrayList<Store> storeArrayList) {
        this.storeArrayList = storeArrayList;
    }

    public static class Store implements Parcelable {
        @SerializedName("store_id")
        private String storeId;
        @SerializedName("store_type_id")
        private String storeTypeId;
        @SerializedName("cover_photo")
        private String coverPhoto;
        @SerializedName("store_logo")
        private String storeLogo;
        @SerializedName("store_name")
        private String storeName;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreTypeId() {
            return storeTypeId;
        }

        public void setStoreTypeId(String storeTypeId) {
            this.storeTypeId = storeTypeId;
        }

        public String getCoverPhoto() {
            return coverPhoto;
        }

        public void setCoverPhoto(String coverPhoto) {
            this.coverPhoto = coverPhoto;
        }

        public String getStoreLogo() {
            return storeLogo;
        }

        public void setStoreLogo(String storeLogo) {
            this.storeLogo = storeLogo;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.storeId);
            dest.writeString(this.storeTypeId);
            dest.writeString(this.coverPhoto);
            dest.writeString(this.storeLogo);
            dest.writeString(this.storeName);
        }

        public Store() {
        }

        protected Store(Parcel in) {
            this.storeId = in.readString();
            this.storeTypeId = in.readString();
            this.coverPhoto = in.readString();
            this.storeLogo = in.readString();
            this.storeName = in.readString();
        }

        public static final Creator<Store> CREATOR = new Creator<Store>() {
            @Override
            public Store createFromParcel(Parcel source) {
                return new Store(source);
            }

            @Override
            public Store[] newArray(int size) {
                return new Store[size];
            }
        };
    }
}
