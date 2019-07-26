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
import app.com.maksab.api.dao.FavoriteOfferListResponse;
import app.com.maksab.databinding.FragmentFavoritesTypeBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.On2ItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.activity.IntroActivity;
import app.com.maksab.view.activity.OfferDetailsActivity;
import app.com.maksab.view.adapter.FavoriteOfferListAdapter;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import retrofit2.Call;
import retrofit2.Callback;


public class FavoritesTypeFragment extends Fragment {

    public FavoritesTypeFragment() {
        // Required empty public constructor
    }
    private FragmentFavoritesTypeBinding mBinding;

    public static FavoritesTypeFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesTypeFragment fragment = new FavoritesTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_favorites_type, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }else getFavoriteDeals();

        mBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getFavoriteDeals();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getFavoriteDeals() {
        mBinding.swifeRefresh.setRefreshing(true);
        UserCityIdModel userCityIdModel = new UserCityIdModel();
        userCityIdModel.setCityId(Utility.getCityId(getActivity()));
        userCityIdModel.setUserId(Utility.getUserId(getActivity()));
        userCityIdModel.setLanguage(Utility.getLanguage(getActivity()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<FavoriteOfferListResponse> responseCall = api.favoriteOfferList(userCityIdModel);
        responseCall.enqueue(new Callback<FavoriteOfferListResponse>() {
            @Override
            public void onResponse(Call<FavoriteOfferListResponse> call, retrofit2.Response<FavoriteOfferListResponse>
                    response) {
                if (getActivity() != null && isVisible()) {
                    mBinding.swifeRefresh.setRefreshing(false);
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FavoriteOfferListResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                if (getActivity() != null && isVisible()) {
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
    private void handleStoreListResponse(FavoriteOfferListResponse myResponse) {
        try {
            if (myResponse != null){
                if (myResponse.getResponseCode()!= null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    if (myResponse.getOfferList() != null && myResponse.getOfferList().size() != 0) {
                        setRecyclerView(myResponse.getOfferList());
                    }
                } else {
                    // Utility.showToast(getActivity(), storeResponse.getMessage());
                    // getActivity().onBackPressed();
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
    private void setRecyclerView(ArrayList<FavoriteOfferListResponse.OfferList> myArrayList) {
        On2ItemClickListener on2ItemClickListener = new On2ItemClickListener() {
            @Override
            public void onClickOne(int position, Object obj) {
                FavoriteOfferListResponse.OfferList offerList = (FavoriteOfferListResponse.OfferList) obj;
                Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
                intent.putExtra(Constant.OFFER_ID,offerList.getOfferId());
                startActivityForResult(intent, 1);
            }

            @Override
            public void onClickTwo(int position, Object obj) {
                FavoriteOfferListResponse.OfferList offerList = (FavoriteOfferListResponse.OfferList) obj;
                onClickFavorites(offerList.getOfferId());
            }
        };
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mBinding.recyclerView.setAdapter(new FavoriteOfferListAdapter(getActivity(), myArrayList, on2ItemClickListener));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Log.e("result",result+"");
                getFavoriteDeals();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    public void onClickFavorites(String sOfferId) {
        if (PreferenceConnector.readBoolean(getActivity(), PreferenceConnector.IS_LOGIN, false)) {
            ProgressDialog.getInstance().showProgressDialog(getActivity());
            UserOfferIdModel userOfferIdModel = new UserOfferIdModel();
            userOfferIdModel.setUserId(Utility.getUserId(getActivity()));
            userOfferIdModel.setLanguage(Utility.getLanguage(getActivity()));
            userOfferIdModel.setOfferId(sOfferId);

            Api api = APIClient.getClient().create(Api.class);
            final Call<AddFavoritesResponse> responseCall = api.addOfferInWishlist(userOfferIdModel);
            responseCall.enqueue(new Callback<AddFavoritesResponse>() {
                @Override
                public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
                        response) {
                    ProgressDialog.getInstance().dismissDialog();
                    AddFavoritesResponse addFavoritesResponse = response.body();
                    if (getActivity() != null && isVisible()) {
                        getFavoriteDeals();
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

