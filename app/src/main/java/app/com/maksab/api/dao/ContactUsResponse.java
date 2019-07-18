package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactUsResponse {
    private String message;
    private String responseCode;
    @SerializedName("contactList")
    private ArrayList<ContactList> contactListArrayList;

    public ArrayList<ContactList> getContactListArrayList() {
        return contactListArrayList;
    }

    public void setContactListArrayList(ArrayList<ContactList> contactListArrayList) {
        this.contactListArrayList = contactListArrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public class ContactList {
        @SerializedName("contact_id")
        private String contactId;
        @SerializedName("conatct_us_name")
        private String conatctUsName;
        @SerializedName("address_location")
        private String addressLocation;
        private String latitude;
        private String longitude;

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getConatctUsName() {
            return conatctUsName;
        }

        public void setConatctUsName(String conatctUsName) {
            this.conatctUsName = conatctUsName;
        }

        public String getAddressLocation() {
            return addressLocation;
        }

        public void setAddressLocation(String addressLocation) {
            this.addressLocation = addressLocation;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
