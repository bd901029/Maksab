package app.com.maksab.view.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.GetOrderAmount;
import app.com.maksab.api.dao.UpcomingPurchaseModel;
import app.com.maksab.databinding.ActivityPastPurchasesBinding;
import app.com.maksab.util.Extension;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.PastPurchaseAdapter;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class PastPurchaseActivity extends AppCompatActivity {
    Context context;
    ActivityPastPurchasesBinding mBinding;
    PastPurchaseAdapter pastPurchaseAdapter;
    int prosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_past_purchases);
        mBinding.setActivity(this);
        context = this;
        if(getIntent()!=null){
            prosition = getIntent().getIntExtra("position",0);
        }
        initViews();
    }

    public void onClickBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initViews() {
        if(prosition==0){
            mBinding.titleTv.setText(getString(R.string.my_upcoming_purchase));
        }else{
            mBinding.titleTv.setText(getString(R.string.my_past_purchase));
        }
        pastPurchaseAdapter = new PastPurchaseAdapter(context,prosition);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mBinding.purchaseList.setLayoutManager(manager);
        mBinding.purchaseList.setAdapter(pastPurchaseAdapter);
        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(PastPurchaseActivity.this, "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(PastPurchaseActivity.this);
        }else  getOrderAmount();

        mBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getUpcomingPurchase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getOrderAmount() {
        ProgressDialog.getInstance().showProgressDialog(context);
        mBinding.swifeRefresh.setRefreshing(true);
        UserIdModel model = new UserIdModel();
        model.setUserId(Utility.getUserId(context));
        model.setLanguage(Utility.getLanguage(context));
        Api api = APIClient.getClient().create(Api.class);
        final Call<GetOrderAmount> responseCall = api.getOrderAmount(model);
        responseCall.enqueue(new Callback<GetOrderAmount>() {
            @Override
            public void onResponse(Call<GetOrderAmount> call, retrofit2.Response<GetOrderAmount> response) {
                GetOrderAmount amount = response.body();
                mBinding.setModel(amount);
                getUpcomingPurchase();
            }

            @Override
            public void onFailure(Call<GetOrderAmount> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(PastPurchaseActivity.this, t+"");
            }
        });
    }


    public void getUpcomingPurchase() {
        mBinding.swifeRefresh.setRefreshing(true);
        UserCityIdModel model = new UserCityIdModel();
        model.setCityId(Utility.getCityId(context));
        model.setUserId(Utility.getUserId(context));
        model.setLanguage(Utility.getLanguage(context));
        Api api = APIClient.getClient().create(Api.class);
        final Call<UpcomingPurchaseModel> responseCall;
        if(prosition==0){
            responseCall = api.getUpcomingPurchase(model);
        }else{
            responseCall = api.getPastPurchase(model);
        }
        responseCall.enqueue(new Callback<UpcomingPurchaseModel>() {
            @Override
            public void onResponse(Call<UpcomingPurchaseModel> call, retrofit2.Response<UpcomingPurchaseModel> response) {
                ProgressDialog.getInstance().dismissDialog();
                mBinding.swifeRefresh.setRefreshing(false);
                handleStoreListResponse(response.body());
            }

            @Override
            public void onFailure(Call<UpcomingPurchaseModel> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                    Utility.showToast(PastPurchaseActivity.this, t+"");
            }
        });
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void handleStoreListResponse(UpcomingPurchaseModel myResponse) {
        try {
            if (myResponse != null){
                    if (myResponse.getOfferList() != null && myResponse.getOfferList().size() != 0) {
                        pastPurchaseAdapter.setList(myResponse.getOfferList());
                    }
                } else {
                     Utility.showToast(PastPurchaseActivity.this, myResponse.getMessage());

                }
        } catch (Exception e) {
            Utility.showToast(PastPurchaseActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }
}
