package app.com.maksab.api.dao;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("User_Id")
    private String userId;
    @SerializedName("First_Name")
    private String firstName;
    @SerializedName("Last_Name")
    private String lastName;
    @SerializedName("Email_Address")
    private String email;
    @SerializedName("Date_Of_Birth")
    private String dob;
    @SerializedName("City")
    private String city;
    @SerializedName("Phone_Number")
    private String phone;
    @SerializedName("email_varify")
    private String isVerify;




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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }
}
