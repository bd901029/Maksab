package app.com.maksab.view.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import app.com.maksab.BR;


public class PaymentModel extends BaseObservable{
    public String cardNumber = "";
    public String cardName = "";
    public String expiryMonth = "";
    public String expiryYear = "";
    public String cvv = "";

    @Bindable
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        notifyPropertyChanged(BR.cardNumber);
    }

    @Bindable
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
        notifyPropertyChanged(BR.cardName);
    }

    @Bindable
    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
        notifyPropertyChanged(BR.expiryMonth);
    }

    @Bindable
    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
        notifyPropertyChanged(BR.expiryYear);
    }

    @Bindable
    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
        notifyPropertyChanged(BR.cvv);
    }


}