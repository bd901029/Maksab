package app.com.maksab.view.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import app.com.maksab.BR;

public class DebitCreditCart extends BaseObservable {

    public String cardHolderName = "";
    public String cardCVVNo = "";
    public String cartMonth = "";
    public String cartYear = "";

    @Bindable
    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        notifyPropertyChanged(BR.cardHolderName);
    }

    @Bindable
    public String getCardCVVNo() {
        return cardCVVNo;
    }

    public void setCardCVVNo(String cardCVVNo) {
        this.cardCVVNo = cardCVVNo;
        notifyPropertyChanged(BR.cardCVVNo);
    }

    @Bindable
    public String getCartMonth() {
        return cartMonth;
    }

    public void setCartMonth(String cartMonth) {
        this.cartMonth = cartMonth;
        notifyPropertyChanged(BR.cartMonth);
    }

    @Bindable
    public String getCartYear() {
        return cartYear;
    }

    public void setCartYear(String cartYear) {
        this.cartYear = cartYear;
        notifyPropertyChanged(BR.cartYear);
    }
}
