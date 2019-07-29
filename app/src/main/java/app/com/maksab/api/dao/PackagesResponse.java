package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RWS 6 on 9/2/2017.
 */

public class PackagesResponse {
    public String responseCode = "";
    public String message = "";
    @SerializedName("plans")
    private ArrayList<PackagePlan> plansArrayList;

    public ArrayList<PackagePlan> getPlansArrayList() {
        return plansArrayList;
    }

    public void setPlansArrayList(ArrayList<PackagePlan> plansArrayList) {
        this.plansArrayList = plansArrayList;
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

    public class PackagePlan {
        @SerializedName("plan_id")
        public String planId;
        @SerializedName("plan_name")
        public String planName;
        @SerializedName("plan_img")
        public String planImg;
        @SerializedName("plan_status")
        public String planStatus = "";
        public String beforeAmount;
        public String afterAmount = "";
        @SerializedName("plan_color")
        public String planColor = "";
        @SerializedName("text_shadow")
        public String textShadow;
        @SerializedName("expiry_date")
        public String expiryDate = "";
        @SerializedName("facilitys")
        private ArrayList<Facilitys> facilitysArrayList;

        public String getPlanImg() {
            return planImg;
        }

        public void setPlanImg(String planImg) {
            this.planImg = planImg;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getPlanStatus() {
            return planStatus;
        }

        public void setPlanStatus(String planStatus) {
            this.planStatus = planStatus;
        }

        public String getBeforeAmount() {
            return beforeAmount;
        }

        public void setBeforeAmount(String beforeAmount) {
            this.beforeAmount = beforeAmount;
        }

        public String getAfterAmount() {
            return afterAmount;
        }

        public void setAfterAmount(String afterAmount) {
            this.afterAmount = afterAmount;
        }

        public String getPlanColor() {
            return planColor;
        }

        public void setPlanColor(String planColor) {
            this.planColor = planColor;
        }

        public String getTextShadow() {
            return textShadow;
        }

        public void setTextShadow(String textShadow) {
            this.textShadow = textShadow;
        }

        public ArrayList<Facilitys> getFacilitysArrayList() {
            return facilitysArrayList;
        }

        public void setFacilitysArrayList(ArrayList<Facilitys> facilitysArrayList) {
            this.facilitysArrayList = facilitysArrayList;
        }

        public boolean isFree() {
            return getPlanStatus().equals("1") || getAfterAmount().equals("0");
        }

        public boolean isFreeAmount() {
            return getPlanStatus().equals("1") || getAfterAmount().equals("Free");
        }
    }

    public class Facilitys {
        public String facility;
        @SerializedName("facility_status")
        public String facilityStatus;
        @SerializedName("plan_color")
        public String planColor = "";

        public String getPlanColor() {
            return planColor;
        }

        public void setPlanColor(String planColor) {
            this.planColor = planColor;
        }

        public String getFacility() {
            return facility;
        }

        public void setFacility(String facility) {
            this.facility = facility;
        }

        public String getFacilityStatus() {
            return facilityStatus;
        }

        public void setFacilityStatus(String facilityStatus) {
            this.facilityStatus = facilityStatus;
        }
    }
    }
