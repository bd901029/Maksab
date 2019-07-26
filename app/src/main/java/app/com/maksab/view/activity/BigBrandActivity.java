package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Arrays;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AlphabetResponse;
import app.com.maksab.api.dao.FavoritePartnerListResponse;
import app.com.maksab.databinding.ActivityBigBrandBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.AlphabetItemAdapter;
import app.com.maksab.view.adapter.FavoritesAllPartnersAdapter;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class BigBrandActivity extends AppCompatActivity {
    private ActivityBigBrandBinding mBinding;
    private String sType = "";
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<AlphabetResponse> alphabetResponseArrayList ;
    private String[] aPartnerEnglish = {"All","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","S","T",
            "U","V","W","X","Y","Z"};
    private String[] aPartnerArabic = {"الجميع","ا","ب","ت","ث","ج","ح","خ","د","ذ","ر","ز","س","ش","ص","ض","ط","ظ","ع","غ","ف",
            "ق","ك","ل","م","ن","ه","و","ي"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_big_brand);
        mBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sType = "all";
        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(BigBrandActivity.this, "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(BigBrandActivity.this);
        }else
            getPartners();

        mBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getPartners();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (Utility.getLanguage(BigBrandActivity.this).equalsIgnoreCase(Constant.LANGUAGE_ARABIC)){
            stringArrayList.addAll(Arrays.asList(aPartnerArabic));
        }else {
            stringArrayList.addAll(Arrays.asList(aPartnerEnglish));
        }

        this.alphabetResponseArrayList = new ArrayList<>();
        for (int i = 0; i < stringArrayList.size(); i++){
            System.out.println(stringArrayList.get(i));
            AlphabetResponse alphabetResponse = new AlphabetResponse();
            alphabetResponse.setAlphabet(stringArrayList.get(i));
            this.alphabetResponseArrayList.add(alphabetResponse);
            System.out.println(alphabetResponseArrayList.get(i).getAlphabet());
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                AlphabetResponse alphabetResponse = (AlphabetResponse) obj;
                sType = alphabetResponse.getAlphabet();
                getPartners();
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BigBrandActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.recyclerViewAlphabet.setLayoutManager(linearLayoutManager);
        mBinding.recyclerViewAlphabet.setAdapter(new AlphabetItemAdapter(BigBrandActivity.this,
                this.alphabetResponseArrayList,onItemClickListener));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void getPartners() {
        mBinding.swifeRefresh.setRefreshing(true);
        UserCityIdModel userCityIdModel = new UserCityIdModel();
        userCityIdModel.setCityId(Utility.getCityId(BigBrandActivity.this));
        userCityIdModel.setType(sType);
        userCityIdModel.setUserId(Utility.getUserId(BigBrandActivity.this));
        userCityIdModel.setLanguage(Utility.getLanguage(BigBrandActivity.this));
        Api api = APIClient.getClient().create(Api.class);
        final Call<FavoritePartnerListResponse> responseCall = api.getAllBrand(userCityIdModel);
        responseCall.enqueue(new Callback<FavoritePartnerListResponse>() {
            @Override
            public void onResponse(Call<FavoritePartnerListResponse> call, retrofit2.Response<FavoritePartnerListResponse>
                    response) {
                if (!isDestroyed()) {
                    mBinding.swifeRefresh.setRefreshing(false);
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FavoritePartnerListResponse> call, Throwable t) {
                if (!isDestroyed()) {
                    ProgressDialog.getInstance().dismissDialog();
                    Utility.showToast(BigBrandActivity.this, t+"");
                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void handleStoreListResponse(FavoritePartnerListResponse myResponse) {
        try {
            if (myResponse != null){
                if (myResponse.getResponseCode()!= null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    if (myResponse.getBrandData() != null && myResponse.getBrandData().size() != 0) {
                        mBinding.recyclerView.setVisibility(View.VISIBLE);
                        setRecyclerView(myResponse.getBrandData());
                    }else {
                        //Utility.showToast(BigBrandActivity.this, myResponse.getMessage());
                        Utility.showToast(BigBrandActivity.this, getString(R.string.no_data_found));
                        mBinding.recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    //Utility.showToast(BigBrandActivity.this, myResponse.getMessage());
                    Utility.showToast(BigBrandActivity.this, getString(R.string.no_data_found));
                    mBinding.recyclerView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            Utility.showToast(BigBrandActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }

    /**
     * Set recycler view Adapter
     * @param myArrayList Category array list for adapter
     */
    private void setRecyclerView(ArrayList<FavoritePartnerListResponse.BrandDataList> myArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                FavoritePartnerListResponse.BrandDataList brandData = (FavoritePartnerListResponse.BrandDataList) obj;
                Intent intent = new Intent(BigBrandActivity.this, BrandDetailActivity.class);
                intent.putExtra(Constant.BRAND_ID, brandData.getBrandId());
                startActivity(intent);
            }
        };
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(BigBrandActivity.this, 2));
        mBinding.recyclerView.setAdapter(new FavoritesAllPartnersAdapter(BigBrandActivity.this, myArrayList, onItemClickListener));
    }
}