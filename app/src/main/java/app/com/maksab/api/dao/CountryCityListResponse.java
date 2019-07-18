package app.com.maksab.api.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryCityListResponse {
    private String message;
    private String responseCode;
    @SerializedName("countryData")
    private CountryCityListResponse.AllCountryData allCountryData;

    public AllCountryData getAllCountryData() {
        return allCountryData;
    }

    public void setAllCountryData(AllCountryData allCountryData) {
        this.allCountryData = allCountryData;
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

    public class AllCountryData {
        @SerializedName("ar")
        private ArrayList<ArCountry> arCountryArrayList;
        @SerializedName("en")
        private ArrayList<EnCountry> enCountryArrayList;
        @SerializedName("tr")
        private ArrayList<TrCountry> trCountryArrayList;

        public ArrayList<ArCountry> getArCountryArrayList() {
            return arCountryArrayList;
        }

        public void setArCountryArrayList(ArrayList<ArCountry> arCountryArrayList) {
            this.arCountryArrayList = arCountryArrayList;
        }

        public ArrayList<EnCountry> getEnCountryArrayList() {
            return enCountryArrayList;
        }

        public void setEnCountryArrayList(ArrayList<EnCountry> enCountryArrayList) {
            this.enCountryArrayList = enCountryArrayList;
        }

        public ArrayList<TrCountry> getTrCountryArrayList() {
            return trCountryArrayList;
        }

        public void setTrCountryArrayList(ArrayList<TrCountry> trCountryArrayList) {
            this.trCountryArrayList = trCountryArrayList;
        }
    }

    public class ArCountry {
        @SerializedName("country_id")
        private String countryId;
        @SerializedName("country_code")
        private String countryCode;
        @SerializedName("country_name")
        private String countryName;
        @SerializedName("country_flag")
        private String countryFlag;
        private boolean statusCountry = false;
        private ArrayList<CityList> citys;

        public boolean isStatusCountry() {
            return statusCountry;
        }

        public void setStatusCountry(boolean statusCountry) {
            this.statusCountry = statusCountry;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryFlag() {
            return countryFlag;
        }

        public void setCountryFlag(String countryFlag) {
            this.countryFlag = countryFlag;
        }

        public ArrayList<CityList> getCitys() {
            return citys;
        }

        public void setCitys(ArrayList<CityList> citys) {
            this.citys = citys;
        }
    }

    public class EnCountry {
        @SerializedName("country_id")
        private String countryId;
        @SerializedName("country_code")
        private String countryCode;
        @SerializedName("country_name")
        private String countryName;
        @SerializedName("country_flag")
        private String countryFlag;
        private boolean statusCountry = false;
        private ArrayList<CityList> citys;

        public boolean isStatusCountry() {
            return statusCountry;
        }

        public void setStatusCountry(boolean statusCountry) {
            this.statusCountry = statusCountry;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryFlag() {
            return countryFlag;
        }

        public void setCountryFlag(String countryFlag) {
            this.countryFlag = countryFlag;
        }

        public ArrayList<CityList> getCitys() {
            return citys;
        }

        public void setCitys(ArrayList<CityList> citys) {
            this.citys = citys;
        }
    }

    public class TrCountry {
        @SerializedName("country_id")
        private String countryId;
        @SerializedName("country_code")
        private String countryCode;
        @SerializedName("country_name")
        private String countryName;
        @SerializedName("country_flag")
        private String countryFlag;
        private boolean statusCountry = false;
        private ArrayList<CityList> citys;

        public boolean isStatusCountry() {
            return statusCountry;
        }

        public void setStatusCountry(boolean statusCountry) {
            this.statusCountry = statusCountry;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryFlag() {
            return countryFlag;
        }

        public void setCountryFlag(String countryFlag) {
            this.countryFlag = countryFlag;
        }

        public ArrayList<CityList> getCitys() {
            return citys;
        }

        public void setCitys(ArrayList<CityList> citys) {
            this.citys = citys;
        }
    }

    public class CityList {
        @SerializedName("city_id")
        private String cityId;
        @SerializedName("city_name")
        private String cityName;
        private boolean status = false;
        private String countryId;
        private String countryName;
        private String countryCode;
        private String countryFlag;
        @SerializedName("languages")
        private CountryCityListResponse.LanguageList languageList;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }


        public LanguageList getLanguageList() {
            return languageList;
        }

        public void setLanguageList(LanguageList languageList) {
            this.languageList = languageList;
        }

        /*@SerializedName("languages")
                private ArrayList<LanguageList> languageListArrayList;

                public ArrayList<LanguageList> getLanguageListArrayList() {
                    return languageListArrayList;
                }

                public void setLanguageListArrayList(ArrayList<LanguageList> languageListArrayList) {
                    this.languageListArrayList = languageListArrayList;
                }*/
        public String getCountryFlag() {
            return countryFlag;
        }

        public void setCountryFlag(String countryFlag) {
            this.countryFlag = countryFlag;
        }


        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }
//private ArrayList<LanguageList> languages;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }


        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }


    }

    public class LanguageList {
        private boolean ar = false;
        private boolean en = false;
        private boolean tr = false;

        public boolean isAr() {
            return ar;
        }

        public void setAr(boolean ar) {
            this.ar = ar;
        }

        public boolean isEn() {
            return en;
        }

        public void setEn(boolean en) {
            this.en = en;
        }

        public boolean isTr() {
            return tr;
        }

        public void setTr(boolean tr) {
            this.tr = tr;
        }
    }
}
