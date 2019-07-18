package app.com.maksab.view.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.widget.TextView;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.GetOrderAmount;
import app.com.maksab.databinding.ActivityMyGiftsBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.adapter.GiftHistoryAdapter;
import app.com.maksab.view.adapter.TabAdapter;
import app.com.maksab.view.fragment.FavoritesPartnersFragment;
import app.com.maksab.view.fragment.FavoritesTypeFragment;
import app.com.maksab.view.fragment.ReceiveGiftsFragment;
import app.com.maksab.view.fragment.SendGiftsFragment;
import app.com.maksab.view.viewmodel.GiftHistoryModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class MyGiftsActivity extends AppCompatActivity {
    Context context;
    ActivityMyGiftsBinding mBinding;
    GiftHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_gifts);
        mBinding.setActivity(this);
        context = this;

        addTab();
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        createTabIcons();
    }

    public void onClickBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void createTabIcons() {
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/CALIBRI REGULAR.TTF");
        //textView.setTypeface(type);

        TextView tabZiro = (TextView) LayoutInflater.from(MyGiftsActivity.this).inflate(R.layout.custom_tab_cat, null);
        tabZiro.setText(getString(R.string.received_gifts));
        tabZiro.setTypeface(type);
        mBinding.tabLayout.getTabAt(0).setCustomView(tabZiro);

        TextView tabOne = (TextView) LayoutInflater.from(MyGiftsActivity.this).inflate(R.layout.custom_tab_cat, null);
        tabOne.setText(getString(R.string.sent_gifts));
        tabOne.setTypeface(type);
        mBinding.tabLayout.getTabAt(1).setCustomView(tabOne);
    }

    private void addTab() {
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());//(getChildFragmentManager());
        tabAdapter.addFragment(ReceiveGiftsFragment.newInstance(), getResources().getString(R.string.received_gifts));
        tabAdapter.addFragment(SendGiftsFragment.newInstance(), getResources().getString(R.string.sent_gifts));
        mBinding.viewPager.setAdapter(tabAdapter);
    }

}
