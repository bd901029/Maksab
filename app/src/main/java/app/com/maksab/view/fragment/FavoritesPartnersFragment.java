package app.com.maksab.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AddFavoritesResponse;
import app.com.maksab.api.dao.FavoritePartnerListResponse;
import app.com.maksab.databinding.FragmentFavoritesPartnersBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.On2ItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.activity.BrandDetailActivity;
import app.com.maksab.view.activity.IntroActivity;
import app.com.maksab.view.adapter.FavoritesPartnersAdapter;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import app.com.maksab.view.viewmodel.UserPartnerIdModel;
import retrofit2.Call;
import retrofit2.Callback;


public class FavoritesPartnersFragment extends Fragment {

    public FavoritesPartnersFragment() {
        // Required empty public constructor
    }

    private FragmentFavoritesPartnersBinding fragmentBinding;

    public static FavoritesPartnersFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesPartnersFragment fragment = new FavoritesPartnersFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_favorites_partners, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }else   getFavoritePartner();

        fragmentBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getFavoritePartner();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getFavoritePartner() {
        fragmentBinding.swifeRefresh.setRefreshing(true);
        UserCityIdModel userCityIdModel = new UserCityIdModel();
        userCityIdModel.setCityId(Utility.getCityId(getActivity()));
        userCityIdModel.setUserId(Utility.getUserId(getActivity()));
        userCityIdModel.setLanguage(Utility.getLanguage(getActivity()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<FavoritePartnerListResponse> responseCall = api.favoritePartnerList(userCityIdModel);
        responseCall.enqueue(new Callback<FavoritePartnerListResponse>() {
            @Override
            public void onResponse(Call<FavoritePartnerListResponse> call, retrofit2.Response<FavoritePartnerListResponse>
                    response) {
                if (getActivity() != null && isVisible()) {
                    fragmentBinding.swifeRefresh.setRefreshing(false);
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FavoritePartnerListResponse> call, Throwable t) {
                if (getActivity() != null && isVisible()) {
                    ProgressDialog.getInstance().dismissDialog();
                    Utility.showToast(getActivity(), t+"");
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
                        setRecyclerView(myResponse.getBrandData());
                    }
                } else {
                    Utility.showToast(getActivity(), getString(R.string.no_data_found));

                }
            }
        } catch (Exception e) {
            Utility.showToast(getActivity(), getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }

    /**
     * Set recycler view Adapter
     * @param myArrayList Category array list for adapter
     */
    private void setRecyclerView(ArrayList<FavoritePartnerListResponse.BrandDataList> myArrayList) {
        On2ItemClickListener  on2ItemClickListener = new On2ItemClickListener() {
            @Override
            public void onClickOne(int position, Object obj) {
                FavoritePartnerListResponse.BrandDataList brandDataList = (FavoritePartnerListResponse.BrandDataList) obj;
                Intent intent = new Intent(getActivity(), BrandDetailActivity.class);
                intent.putExtra(Constant.BRAND_ID,brandDataList.getBrandId());
                startActivityForResult(intent, 1);
            }

            @Override
            public void onClickTwo(int position, Object obj) {
                FavoritePartnerListResponse.BrandDataList brandDataList = (FavoritePartnerListResponse.BrandDataList) obj;
                onClickFavorites(brandDataList.getBrandId());
            }
        };
        fragmentBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        fragmentBinding.recyclerView.setAdapter(new FavoritesPartnersAdapter(getActivity(), myArrayList, on2ItemClickListener));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Log.e("result",result+"");
                getFavoritePartner();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult


    public void onClickFavorites(String sOfferId) {
        if (PreferenceConnector.readBoolean(getActivity(), PreferenceConnector.IS_LOGIN, false)) {
            ProgressDialog.getInstance().showProgressDialog(getActivity());
            UserPartnerIdModel userPartnerIdModel = new UserPartnerIdModel();
            userPartnerIdModel.setUserId(Utility.getUserId(getActivity()));
            userPartnerIdModel.setLanguage(Utility.getLanguage(getActivity()));
            userPartnerIdModel.setPartnerId(sOfferId);

            Api api = APIClient.getClient().create(Api.class);
            final Call<AddFavoritesResponse> responseCall = api.addPartnerInWishlist(userPartnerIdModel);
            responseCall.enqueue(new Callback<AddFavoritesResponse>() {
                @Override
                public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
                        response) {
                    if (getActivity() != null && isVisible()) {
                        ProgressDialog.getInstance().dismissDialog();
                        if (response.body() != null) {
                            if (response.body().getResponseCode() != null && response.body().getResponseCode().equals(Api
                                    .SUCCESS)) {
                                getFavoritePartner();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddFavoritesResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        Utility.showToast(getActivity(), t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        } else {
            userGuestDialog();
        }
    }

    /**
     * Show Guest Login Alert
     */
    private void userGuestDialog() {
        Utility.setDialog(getActivity(), getString(R.string.alert), getString(R.string.guest_login_alert),
                getString(R.string.no), getString(R.string.yes), new DialogListener() {
                    @Override
                    public void onNegative(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onPositive(DialogInterface dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
    }
}

