package app.com.maksab.view.viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PointsRewardModel implements Serializable {

    @SerializedName("total_point")
    @Expose
    private String totalPoint;
    @SerializedName("inviteBal")
    @Expose
    private String inviteBal;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;

    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getInviteBal() {
        return inviteBal;
    }

    public void setInviteBal(String inviteBal) {
        this.inviteBal = inviteBal;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
