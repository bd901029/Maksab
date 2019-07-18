package app.com.maksab.api.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFamilyMembers implements Serializable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("famMembers")
    @Expose
    private ArrayList<FamMember> famMembers = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<FamMember> getFamMembers() {
        return famMembers;
    }

    public void setFamMembers(ArrayList<FamMember> famMembers) {
        this.famMembers = famMembers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class FamMember implements Serializable {

        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("member_name")
        @Expose
        private String memberName;
        @SerializedName("member_email")
        @Expose
        private String memberEmail;
        @SerializedName("status")
        @Expose
        private String status;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberEmail() {
            return memberEmail;
        }

        public void setMemberEmail(String memberEmail) {
            this.memberEmail = memberEmail;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}

