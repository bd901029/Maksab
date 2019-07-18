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
import app.com.maksab.databinding.ActivityPointBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.PointsRewardModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class PointsRewardActivity extends AppCompatActivity {

    Context mContext;
    ActivityPointBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_point);
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
                getPointsReward();
            }

            @Override
            public void onFailure(Call<GetOrderAmount> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(PointsRewardActivity.this, t+"");
            }
        });
    }

    public void getPointsReward() {
        UserIdModel model = new UserIdModel();
        model.setUserId(Utility.getUserId(mContext));
        model.setLanguage(Utility.getLanguage(mContext));
        Api api = APIClient.getClient().create(Api.class);
        final Call<PointsRewardModel> responseCall;
        responseCall = api.getPointReward(model);
        responseCall.enqueue(new Callback<PointsRewardModel>() {
            @Override
            public void onResponse(Call<PointsRewardModel> call, retrofit2.Response<PointsRewardModel> response) {
                ProgressDialog.getInstance().dismissDialog();
                PointsRewardModel rewardModel = response.body();
                if(rewardModel!=null){
                    mBinding.inviteBal.setText(rewardModel.getInviteBal());
                    mBinding.totalPoints.setText(rewardModel.getTotalPoint());
                }
            }

            @Override
            public void onFailure(Call<PointsRewardModel> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(PointsRewardActivity.this, t+"");
            }
        });
    }
}
