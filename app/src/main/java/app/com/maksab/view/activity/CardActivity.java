package app.com.maksab.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import app.com.maksab.R;
import app.com.maksab.databinding.ActivityCardBinding;
import app.com.maksab.view.viewmodel.DebitCreditCart;
import app.com.maksab.view.viewmodel.LoginModel;

public class CardActivity extends AppCompatActivity {
    ActivityCardBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_card);
        activityBinding.setActivity(this);
    }
}
