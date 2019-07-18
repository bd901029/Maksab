package app.com.maksab.view.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.databinding.ActivityFaqsBinding;
import app.com.maksab.util.Utility;
import app.com.maksab.view.adapter.FaqAdapter;
import app.com.maksab.view.viewmodel.FaqModel;
import app.com.maksab.view.viewmodel.LanguageModel;
import retrofit2.Call;
import retrofit2.Callback;

public class FaqActivity extends AppCompatActivity {

    ActivityFaqsBinding mBinding;
    FaqAdapter adapter;
    //FaqExpandableListAdapter adapter;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_faqs);
        mBinding.setActivity(this);
        context = this;
        initViews();
    }

    private void initViews() {
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        mBinding.faqRecycelrview.setLayoutManager(manager);
        getFaqDetails();
    }

    private void getFaqDetails() {
        LanguageModel languageModel = new LanguageModel();
        languageModel.setLanguage(Utility.getLanguage(FaqActivity.this));
        Api api = APIClient.getClient().create(Api.class);
        final Call<FaqModel> responseCall = api.getFaq(languageModel);
        responseCall.enqueue(new Callback<FaqModel>() {
            @Override
            public void onResponse(Call<FaqModel> call, retrofit2.Response<FaqModel> response) {
                if (!isDestroyed()) {
                    mBinding.progressBar.setVisibility(View.GONE);
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FaqModel> call, Throwable t) {
                if (!isDestroyed()) {
                    //activityBinding.swifeRefresh.setRefreshing(false);
                    mBinding.progressBar.setVisibility(View.GONE);
                    Utility.showToast(FaqActivity.this, t + "");
                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * Handle category list response
     *
     * @param myResponse @CategoryListResponse object
     */
    private void handleStoreListResponse(FaqModel myResponse) {
        try {
            if (myResponse != null) {
               // if (myResponse.getResponseCode().equals("200")) {
                    // activityBinding.swifeRefresh.setRefreshing(false);
                    if (myResponse.getFaqList() != null && myResponse.getFaqList().size() != 0) {
                        adapter = new FaqAdapter(context,myResponse.getFaqList());
                        mBinding.faqRecycelrview.setAdapter(adapter);
                        //adapter.setList(myResponse.getFaqList());
                    }
              /*  } else {
                     Utility.showToast(FaqActivity.this, myResponse.getMessage());
                }*/
            }
        } catch (Exception e) {
            Utility.showToast(FaqActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }

    public void onClickBack(){
        onBackPressed();
    }

}
