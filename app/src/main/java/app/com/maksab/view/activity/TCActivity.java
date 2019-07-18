package app.com.maksab.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.WindowManager;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.TcResponse;
import app.com.maksab.databinding.ActivityTcBinding;
import app.com.maksab.util.Constant;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;

public class TCActivity extends AppCompatActivity {
    private ActivityTcBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tc);
        mBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getTC();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getTC() {
        ProgressDialog.getInstance().showProgressDialog(TCActivity.this);
        Api api = APIClient.getClient().create(Api.class);
        final Call<TcResponse> responseCall = api.getTermsAndCondition();
        responseCall.enqueue(new Callback<TcResponse>() {
            @Override
            public void onResponse(Call<TcResponse> call, retrofit2.Response<TcResponse> response) {
                ProgressDialog.getInstance().dismissDialog();
                TcResponse tcResponse = response.body();
                switch (Utility.getLanguage(TCActivity.this)){
                    case Constant.LANGUAGE_ARABIC:
                        mBinding.tc.setText(Html.fromHtml(tcResponse.getTermsAb()));
                        break;
                    case Constant.LANGUAGE_ENGLISH:
                        mBinding.tc.setText(Html.fromHtml(tcResponse.getTermsEn()));
                        break;
                    case Constant.LANGUAGE_TURKYCE:
                        mBinding.tc.setText(Html.fromHtml(tcResponse.getTermsTr()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<TcResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(TCActivity.this, t+"");
            }
        });
    }
}