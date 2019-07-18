package app.com.maksab.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.GetFamilyMembers;
import app.com.maksab.api.dao.GetOrderAmount;
import app.com.maksab.api.dao.LoginResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityAddMemberBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.FamilyModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class AddMemberActivity extends AppCompatActivity {

    Context mContext;
    ActivityAddMemberBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_member);
        mBinding.setActivity(this);
        mContext = this;
        initViews();
    }

    private void initViews() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClickAdd(){
        if(isValid()){
            AddFamilyMember();
        }
    }

    private void AddFamilyMember() {
        ProgressDialog.getInstance().showProgressDialog(mContext);
        FamilyModel model = new FamilyModel();
        model.setUserId(Utility.getUserId(mContext));
        model.setLanguage(Utility.getLanguage(mContext));
        model.setEmail(mBinding.emailEdt.getText().toString());
        model.setName(mBinding.nameEdt.getText().toString());
        Api api = APIClient.getClient().create(Api.class);
        final Call<SuccessfulResponse> responseCall = api.addFamilyMember(model);
        responseCall.enqueue(new Callback<SuccessfulResponse>() {
            @Override
            public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse> response) {
                ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()){
                    handleLoginResponse(response.body());
                }

            }
            @Override
            public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                //Utility.showToast(AddMemberActivity.this, t+"");
            }
        });
    }

    /**
     * Handle login response
     * @param successfulResponse SuccessfulResponse
     */
    private void handleLoginResponse(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
             Utility.showToast(AddMemberActivity.this, successfulResponse.getMessage());
            if (successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                //setResult(RESULT_OK);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","2");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        }
    }

    private boolean isValid() {
        if(TextUtils.isEmpty(mBinding.emailEdt.getText().toString())){
            Utility.showToast(AddMemberActivity.this,"Please enter email addresss");
            return false;
        }else if(TextUtils.isEmpty(mBinding.nameEdt.getText().toString())){
            Utility.showToast(AddMemberActivity.this,"Please enter name");
            return false;
        }else {
            return true;
        }
    }
}
