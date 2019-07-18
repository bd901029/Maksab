package app.com.maksab.view.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.GetOrderAmount;
import app.com.maksab.databinding.ActivitySubscriptionsBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.SubscriptionModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class SubscriptionActivity extends AppCompatActivity {

    Context mContext;
    ActivitySubscriptionsBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_subscriptions);
        mBinding.setActivity(this);
        mContext = this;
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
        getOrderAmount();
    }

    private void getOrderAmount() {
        ProgressDialog.getInstance().showProgressDialog(mContext);
        UserIdModel model = new UserIdModel();
        model.setUserId(Utility.getUserId(mContext));
        model.setLanguage(Utility.getLanguage(mContext));
        Api api = APIClient.getClient().create(Api.class);
        final Call<GetOrderAmount> responseCall = api.getOrderAmount(model);
        responseCall.enqueue(new Callback<GetOrderAmount>() {
            @Override
            public void onResponse(Call<GetOrderAmount> call, retrofit2.Response<GetOrderAmount> response) {
                GetOrderAmount amount = response.body();
                mBinding.setModel(amount);
                getSubscription();
            }

            @Override
            public void onFailure(Call<GetOrderAmount> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(SubscriptionActivity.this, t+"");
            }
        });
    }


    public void getSubscription() {
        UserIdModel model = new UserIdModel();
        model.setUserId(Utility.getUserId(mContext));
        model.setLanguage(Utility.getLanguage(mContext));
        Api api = APIClient.getClient().create(Api.class);
        final Call<SubscriptionModel> responseCall;
        responseCall = api.getUserSubscription(model);
        responseCall.enqueue(new Callback<SubscriptionModel>() {
            @Override
            public void onResponse(Call<SubscriptionModel> call, retrofit2.Response<SubscriptionModel> response) {
                ProgressDialog.getInstance().dismissDialog();
                SubscriptionModel subscriptionModel = response.body();
                if(subscriptionModel!=null){
                    mBinding.subsId.setText(subscriptionModel.getSubscriptionId());
                    mBinding.subsPackage.setText(subscriptionModel.getSubscriptionPlanName());
                    mBinding.subsStartingDate.setText(subscriptionModel.getSubscriptionStartDate());
                    mBinding.subsExpiry.setText(subscriptionModel.getSubscriptionEndDate());
                }
            }

            @Override
            public void onFailure(Call<SubscriptionModel> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(SubscriptionActivity.this, t+"");
            }
        });
    }
}
