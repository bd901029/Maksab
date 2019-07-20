package app.com.maksab.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class CategoryHomeResponse implements Parcelable{
    public String responseCode = "";
    public String message = "";
    @SerializedName("category_list")
    private ArrayList<Category> resultList;

    public ArrayList<Category> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<Category> resultList) {
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

    public class Category {
        @SerializedName("category_id")
        public String categoryId = "";
        @SerializedName("category_name")
        public String categoryName = "";
        @SerializedName("category_img")
        public String categoryImg = "";
        @SerializedName("category_status")
        private String categoryStatus = "1";

        public String getCategoryStatus() {
            return categoryStatus;
        }

        public void setCategoryStatus(String categoryStatus) {
            this.categoryStatus = categoryStatus;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.responseCode);
        dest.writeString(this.message);
        dest.writeList(this.resultList);
    }

    public CategoryHomeResponse() {
    }

    protected CategoryHomeResponse(Parcel in) {
        this.responseCode = in.readString();
        this.message = in.readString();
        this.resultList = new ArrayList<Category>();
        in.readList(this.resultList, Category.class.getClassLoader());
    }

    public static final Creator<CategoryHomeResponse> CREATOR = new Creator<CategoryHomeResponse>() {
        @Override
        public CategoryHomeResponse createFromParcel(Parcel source) {
            return new CategoryHomeResponse(source);
        }

        @Override
        public CategoryHomeResponse[] newArray(int size) {
            return new CategoryHomeResponse[size];
        }
    };
}
