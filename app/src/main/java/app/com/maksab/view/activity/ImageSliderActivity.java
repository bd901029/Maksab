package app.com.maksab.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import app.com.maksab.R;
import app.com.maksab.databinding.ActivityContactBinding;
import app.com.maksab.databinding.ActivityImageSliderBinding;

public class ImageSliderActivity extends AppCompatActivity {
    private ActivityImageSliderBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_slider);
        mBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    //sir maksab apk de rha hu check kr lena(language,language to change view, select Countr )  7:26

    public void onClickBack(){
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}