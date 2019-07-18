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
import android.support.v7.widget.LinearLayoutManager;
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
import app.com.maksab.api.dao.GetOrderAmount;
import app.com.maksab.api.dao.SendGiftsResponce;
import app.com.maksab.databinding.FragmentFavoritesTypeBinding;
import app.com.maksab.databinding.FragmentReceiveGiftsBinding;
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
import app.com.maksab.view.adapter.GiftHistoryAdapter;
import app.com.maksab.view.adapter.GiftReceiveAdapter;
import app.com.maksab.view.viewmodel.GiftHistoryModel;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import retrofit2.Call;
import retrofit2.Callback;


public class ReceiveGiftsFragment extends Fragment {

    private Activity context;
    private GiftReceiveAdapter adapter;

    public ReceiveGiftsFragment() {
        // Required empty public constructor
    }
    private FragmentReceiveGiftsBinding mBinding;

    public static ReceiveGiftsFragment newInstance() {
        Bundle args = new Bundle();
        ReceiveGiftsFragment fragment = new ReceiveGiftsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_receive_gifts, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }else  initViews();
    }

    private void initViews() {
        adapter = new GiftReceiveAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        mBinding.giftHistory.setLayoutManager(manager);
        mBinding.giftHistory.setAdapter(adapter);
        getGiftHistory();

        mBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getGiftHistory();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getGiftHistory() {
        UserIdModel model = new UserIdModel();
        model.setUserId(Utility.getUserId(context));
        model.setLanguage(Utility.getLanguage(context));
        Api api = APIClient.getClient().create(Api.class);
        final Call<SendGiftsResponce> responseCall;
        responseCall = api.getGiftReceive(model);
        responseCall.enqueue(new Callback<SendGiftsResponce>() {
            @Override
            public void onResponse(Call<SendGiftsResponce> call, retrofit2.Response<SendGiftsResponce> response) {
                ProgressDialog.getInstance().dismissDialog();
                mBinding.swifeRefresh.setRefreshing(false);
                handleStoreListResponse(response.body());
            }

            @Override
            public void onFailure(Call<SendGiftsResponce> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(context, t+"");
            }
        });
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void handleStoreListResponse(SendGiftsResponce myResponse) {
        try {
            if (myResponse != null){
                // if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                if (myResponse.getGifts() != null && myResponse.getGifts().size() != 0) {
                    adapter.setList(myResponse.getGifts());
                }
                //  }
            } else {
                Utility.showToast(context, myResponse.getMessage());
            }
        } catch (Exception e) {
            Utility.showToast(context, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }
}
