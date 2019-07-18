package app.com.maksab.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.WindowManager;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.PpResponse;
import app.com.maksab.databinding.ActivityPpBinding;
import app.com.maksab.util.Constant;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;

public class PPActivity extends AppCompatActivity {
    private ActivityPpBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pp);
        mBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getTC();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getTC() {
        ProgressDialog.getInstance().showProgressDialog(PPActivity.this);
        Api api = APIClient.getClient().create(Api.class);
        final Call<PpResponse> responseCall = api.getPrivacyPolicy();
        responseCall.enqueue(new Callback<PpResponse>() {
            @Override
            public void onResponse(Call<PpResponse> call, retrofit2.Response<PpResponse> response) {
                ProgressDialog.getInstance().dismissDialog();
                PpResponse ppResponse = response.body();
                switch (Utility.getLanguage(PPActivity.this)){
                    case Constant.LANGUAGE_ARABIC:
                        mBinding.pp.setText(Html.fromHtml(ppResponse.getAbPolicy()));
                        break;
                    case Constant.LANGUAGE_ENGLISH:
                        mBinding.pp.setText(Html.fromHtml(ppResponse.getEnPolicy()));
                        break;
                    case Constant.LANGUAGE_TURKYCE:
                        mBinding.pp.setText(Html.fromHtml(ppResponse.getTrPolicy()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<PpResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(PPActivity.this, t+"");
            }
        });
    }
}