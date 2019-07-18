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
import app.com.maksab.databinding.ActivityGiftHistorysBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.adapter.GiftHistoryAdapter;
import app.com.maksab.view.viewmodel.GiftHistoryModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class GiftHistoryActivity extends AppCompatActivity {
    Context context;
    ActivityGiftHistorysBinding mBinding;
    GiftHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gift_historys);
        mBinding.setActivity(this);
        context = this;
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
        adapter = new GiftHistoryAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mBinding.giftHistory.setLayoutManager(manager);
        mBinding.giftHistory.setAdapter(adapter);
        getOrderAmount();

        mBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getGiftHistory();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getOrderAmount() {
        ProgressDialog.getInstance().showProgressDialog(context);
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
                getGiftHistory();
            }

            @Override
            public void onFailure(Call<GetOrderAmount> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(GiftHistoryActivity.this, t+"");
            }
        });
    }


    public void getGiftHistory() {
        UserIdModel model = new UserIdModel();
        model.setUserId(Utility.getUserId(context));
        model.setLanguage(Utility.getLanguage(context));
        Api api = APIClient.getClient().create(Api.class);
        final Call<GiftHistoryModel> responseCall;
        responseCall = api.getGiftistory(model);
        responseCall.enqueue(new Callback<GiftHistoryModel>() {
            @Override
            public void onResponse(Call<GiftHistoryModel> call, retrofit2.Response<GiftHistoryModel> response) {
                ProgressDialog.getInstance().dismissDialog();
                mBinding.swifeRefresh.setRefreshing(false);
                handleStoreListResponse(response.body());
            }

            @Override
            public void onFailure(Call<GiftHistoryModel> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(GiftHistoryActivity.this, t+"");
            }
        });
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void handleStoreListResponse(GiftHistoryModel myResponse) {
        try {
            if (myResponse != null){
               // if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    if (myResponse.getGifts() != null && myResponse.getGifts().size() != 0) {
                        adapter.setList(myResponse.getGifts());
                    }
              //  }
            } else {
                Utility.showToast(GiftHistoryActivity.this, myResponse.getMessage());

            }
        } catch (Exception e) {
            Utility.showToast(GiftHistoryActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }
}
