package app.com.maksab.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.SearchListResponse;
import app.com.maksab.databinding.ActivitySearchBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.SearchBrandAdapter;
import app.com.maksab.view.adapter.SearchOfferListAdapter;
import app.com.maksab.view.viewmodel.SearchModel;
import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;
    private String sSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mBinding.setActivity(this);

        mBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    if (!sSearch.equalsIgnoreCase(""))
                        getSearchList(sSearch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mBinding.etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                Extension extension = Extension.getInstance();
                if (!extension.executeStrategy(SearchActivity.this, "", ValidationTemplate.INTERNET)) {
                    Utility.setNoInternetPopup(SearchActivity.this);
                }else {
                    sSearch = mEdit.toString();
                    if (!sSearch.equalsIgnoreCase(""))
                    getSearchList(sSearch);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                mBinding.offer.setVisibility(View.GONE);
                mBinding.brand.setVisibility(View.GONE);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        mBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    try {
                        onClickSearch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    public void getSearchList(String search) {
       // ProgressDialog.getInstance().showProgressDialog(SearchActivity.this);
        mBinding.swifeRefresh.setRefreshing(true);
        SearchModel searchModel = new SearchModel();
        searchModel.setUserId(Utility.getUserId(SearchActivity.this));
        searchModel.setLanguage(Utility.getLanguage(SearchActivity.this));
        searchModel.setCityId(Utility.getCityId(SearchActivity.this));
        searchModel.setSearch(search);
        Api api = APIClient.getClient().create(Api.class);
        final Call<SearchListResponse> responseCall = api.getSearchResult(searchModel);
        responseCall.enqueue(new Callback<SearchListResponse>() {
            @Override
            public void onResponse(Call<SearchListResponse> call, retrofit2.Response<SearchListResponse>
                    response) {
                mBinding.swifeRefresh.setRefreshing(false);
                responseCall.cancel();
                //ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()) {
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchListResponse> call, Throwable t) {
                //ProgressDialog.getInstance().dismissDialog();
                mBinding.swifeRefresh.setRefreshing(false);
                responseCall.cancel();
                if (!isDestroyed()) {
                    Utility.showToast(SearchActivity.this, t + "");//getString(R.string.wrong));
                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                }
            }
        });
    }

    private void handleStoreListResponse(SearchListResponse searchListResponse) {
        if (searchListResponse.getResponseCode().equals(Api.SUCCESS)) {
            if (searchListResponse.getOfferDataArrayList() != null && searchListResponse.getOfferDataArrayList().size() != 0) {
                mBinding.offer.setVisibility(View.VISIBLE);
                setRecyclerViewOffer(searchListResponse.getOfferDataArrayList());
            }else
                mBinding.offer.setVisibility(View.GONE);
            if (searchListResponse.getBrandDataArrayList() != null && searchListResponse.getBrandDataArrayList().size() != 0) {
                setRecyclerViewBrand(searchListResponse.getBrandDataArrayList());
                mBinding.brand.setVisibility(View.VISIBLE);
            }else
                mBinding.brand.setVisibility(View.GONE);
        }
    }

    private void setRecyclerViewOffer(ArrayList<SearchListResponse.OfferData> categoryArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                SearchListResponse.OfferData offerData = (SearchListResponse.OfferData) obj;
                Intent intent = new Intent(SearchActivity.this, OfferDetailsActivity.class);
                intent.putExtra(Constant.OFFER_ID, offerData.getOfferId());
                startActivity(intent);
            }
        };
        mBinding.recyclerViewOffers.setLayoutManager(new GridLayoutManager(SearchActivity.this, 1));
        mBinding.recyclerViewOffers.setAdapter(new SearchOfferListAdapter(SearchActivity.this, categoryArrayList, onItemClickListener));
    }

    private void setRecyclerViewBrand(ArrayList<SearchListResponse.BrandData> categoryArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                SearchListResponse.BrandData brandData = (SearchListResponse.BrandData) obj;
                Intent intent = new Intent(SearchActivity.this, BrandDetailActivity.class);
                intent.putExtra(Constant.BRAND_ID, brandData.getBrandId());
                startActivity(intent);
            }
        };
        mBinding.recyclerViewBrand.setLayoutManager(new GridLayoutManager(SearchActivity.this, 1));
        mBinding.recyclerViewBrand.setAdapter(new SearchBrandAdapter(SearchActivity.this, categoryArrayList, onItemClickListener));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

   public void onClickSearch(){
        Intent intent = new Intent(SearchActivity.this,OfferListActivity.class);
        intent.putExtra(Constant.QUERY,mBinding.etSearch.getText().toString());
        startActivity(intent);
       //startActivity(new Intent(SearchActivity.this,OfferListActivity.class));
   }
}
