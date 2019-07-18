package app.com.maksab.view.fragment.home;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.api.dao.NotificationFilterResponse;
import app.com.maksab.api.dao.NotificationResponse;
import app.com.maksab.api.dao.NotificationStatusResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.DialogNotificationFilterBinding;
import app.com.maksab.databinding.FragmentNotificationBinding;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.activity.OfferDetailsActivity;
import app.com.maksab.view.adapter.NotificationAdapter;
import app.com.maksab.view.adapter.NotificationFilterAdapter;
import app.com.maksab.view.viewmodel.GetStoreListModel;
import app.com.maksab.view.viewmodel.SetNotificationFilterModel;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import app.com.maksab.view.viewmodel.UserIdNotificationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    public NotificationFragment() {
        // Required empty public constructor
    }

    private FragmentNotificationBinding fragmentBinding;
    private final static String STORE_TYPE_ID = "store_type_id";
    private String storeTypeId = "";
    private Dialog dialogNotificationFilter;
    private DialogNotificationFilterBinding recyclerViewBinding;
    ArrayList<String> selectNotification = new ArrayList<String>();
    ArrayList<NotificationFilterResponse.CategoryList> notificationFilterArrayList;
    private boolean selectStatus = true;

    public static NotificationFragment newInstance(String storeTypeId) {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        args.putString(STORE_TYPE_ID, storeTypeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            storeTypeId = bundle.getString(STORE_TYPE_ID);
        } else {
            Utility.showToast(getActivity(), getString(R.string.wrong));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_notification,
                container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.setFragment(this);

        if (!TextUtils.isEmpty(storeTypeId)) {
            GetStoreListModel getStoreListModel = new GetStoreListModel();
            getStoreListModel.setStoreTypeId(storeTypeId);
        } else {
            // getActivity().onBackPressed();
        }

        if (PreferenceConnector.readBoolean(getActivity(), PreferenceConnector.IS_LOGIN, false)) {
            Extension extension = Extension.getInstance();
            if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
                Utility.setNoInternetPopup(getActivity());
            }else {
                try {
                    getNotificationStatus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            fragmentBinding.notificationSwitch.setVisibility(View.GONE);
            fragmentBinding.filter.setVisibility(View.GONE);
            Extension extension = Extension.getInstance();
            if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
                Utility.setNoInternetPopup(getActivity());
            }else{
                ProgressDialog.getInstance().showProgressDialog(getActivity());
                getFavoriteDeals();}
        }

        fragmentBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Extension extension = Extension.getInstance();
                    if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
                        Utility.setNoInternetPopup(getActivity());
                    }else{
                        ProgressDialog.getInstance().showProgressDialog(getActivity());
                        getFavoriteDeals();}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fragmentBinding.notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentBinding.notificationSwitch.isChecked()) {
                    //do stuff when Switch is ON
                    fragmentBinding.notificationSwitch.setChecked(true);
                    setNotificationStatus("1");
                } else {
                    //do stuff when Switch if OFF
                    fragmentBinding.notificationSwitch.setChecked(false);
                    setNotificationStatus("0");
                }
            }
        });

    }

    public void getFavoriteDeals() {
        app.com.maksab.util.Extension extension = app.com.maksab.util.Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }else {
            UserCityIdModel userCityIdModel = new UserCityIdModel();
            userCityIdModel.setCityId(Utility.getCity(getActivity()));
            userCityIdModel.setUserId(Utility.getUserId(getActivity()));
            userCityIdModel.setLanguage(Utility.getLanguage(getActivity()));
            Api api = APIClient.getClient().create(Api.class);
            final Call<NotificationResponse> responseCall = api.getNotification(userCityIdModel);
            responseCall.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, retrofit2.Response<NotificationResponse>
                        response) {
                    if (getActivity() != null && isVisible()) {
                       ProgressDialog.getInstance().dismissDialog();
                        handleStoreListResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    if (getActivity() != null && isVisible()) {
                        ProgressDialog.getInstance().dismissDialog();
                        Utility.showToast(getActivity(), t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void handleStoreListResponse(NotificationResponse myResponse) {
        //try {
            if (myResponse != null) {
                if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    fragmentBinding.swifeRefresh.setRefreshing(false);
                    if (myResponse.getNotificationList() != null && myResponse.getNotificationList().size() != 0) {
                        setRecyclerView(myResponse.getNotificationList());
                    }
                } else {
                    Utility.showToast(getActivity(), myResponse.getMessage());
                    // getActivity().onBackPressed();
                }
            }
    }

    /**
     * Set recycler view Adapter
     * @param myArrayList Category array list for adapter
     */
    private void setRecyclerView(ArrayList<NotificationResponse.NotificationList> myArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                NotificationResponse.NotificationList notificationList = (NotificationResponse.NotificationList) obj;
                if (!TextUtils.isEmpty(notificationList.getNotificationOfferId())) {
                    Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
                    intent.putExtra(Constant.OFFER_ID, notificationList.getNotificationOfferId());
                    startActivity(intent);
                }
            }
        };
        fragmentBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        fragmentBinding.recyclerView.setAdapter(new NotificationAdapter(getActivity(), myArrayList,
                onItemClickListener));
    }

    public void onClickNotificationFilter() {
        dialogNotificationFilter = new Dialog(getActivity());
        recyclerViewBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
                R.layout.dialog_notification_filter, null, false);
        dialogNotificationFilter.setContentView(recyclerViewBinding.getRoot());
        dialogNotificationFilter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectNotification.clear();
        notificationFilterArrayList = null;
        try {
            NotificationFilterList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerViewBinding.txCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNotificationFilter.dismiss();
            }
        });

        recyclerViewBinding.txDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.com.maksab.util.Extension extension = app.com.maksab.util.Extension.getInstance();
                if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
                    Utility.setNoInternetPopup(getActivity());
                }else
                selectNotification();
            }
        });

        recyclerViewBinding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationFilterArrayList != null && notificationFilterArrayList.size() != 0) {
                    selectNotification.clear();
                    if (selectStatus) {
                        for (int i = 0; notificationFilterArrayList.size() > i; i++) {
                            if (notificationFilterArrayList.get(i).getCategoryStatus().equalsIgnoreCase("0")) {
                                notificationFilterArrayList.get(i).setCategoryStatus("1");
                            }
                        }
                        selectStatus = false;
                        recyclerViewBinding.selectAll.setText(getString(R.string.unselect_all));
                        setRecyclerViewNotificationFilter();
                    } else {
                        for (int i = 0; notificationFilterArrayList.size() > i; i++) {
                            if (notificationFilterArrayList.get(i).getCategoryStatus().equalsIgnoreCase("1")) {
                                notificationFilterArrayList.get(i).setCategoryStatus("0");
                            }
                        }
                        selectStatus = true;
                        recyclerViewBinding.selectAll.setText(getString(R.string.select_all));
                        setRecyclerViewNotificationFilter();
                    }
                }
            }
        });
        dialogNotificationFilter.show();
    }

    private void NotificationFilterList() {
         ProgressDialog.getInstance().showProgressDialog(getActivity());
        UserIdModel userIdModel = new UserIdModel();
        userIdModel.setLanguage(Utility.getLanguage(getActivity()));
        userIdModel.setUserId(Utility.getUserId(getActivity()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<NotificationFilterResponse> responseCall = api.getUserCategory(userIdModel);
        responseCall.enqueue(new Callback<NotificationFilterResponse>() {
            @Override
            public void onResponse(Call<NotificationFilterResponse> call, Response<NotificationFilterResponse> response) {
                recyclerViewBinding.progressBar.setVisibility(View.GONE);
                 ProgressDialog.getInstance().dismissDialog();
                if (getActivity() != null && isVisible()) {
                    notificationFilterResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<NotificationFilterResponse> call, Throwable t) {
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
    private void notificationFilterResponse(NotificationFilterResponse myResponse) {
        try {
            if (myResponse != null) {
                if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    fragmentBinding.swifeRefresh.setRefreshing(false);
                    notificationFilterArrayList = myResponse.getCategoryList();
                    if (notificationFilterArrayList != null && notificationFilterArrayList.size() != 0) {
                        setRecyclerViewNotificationFilter();
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

    private void setRecyclerViewNotificationFilter() {
        for (int i = 0; notificationFilterArrayList.size() > i; i++) {
            if (notificationFilterArrayList.get(i).getCategoryStatus().equalsIgnoreCase("1")) {
                selectNotification.add(notificationFilterArrayList.get(i).getCategoryId());
            }
        }

        OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener = new OnItemClickCheckUnCheckListener() {
            @Override
            public void onAdd(int id, Object obj) {
                NotificationFilterResponse.CategoryList notificationList = (NotificationFilterResponse.CategoryList) obj;
                notificationList.setCategoryStatus("1");
                selectNotification.add(notificationList.getCategoryId());
            }

            @Override
            public void onRemove(int id, Object obj) {
                NotificationFilterResponse.CategoryList notificationList = (NotificationFilterResponse.CategoryList) obj;
                notificationList.setCategoryStatus("0");
                selectNotification.remove(notificationList.getCategoryId());
            }
        };
        recyclerViewBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerViewBinding.recyclerView.setAdapter(new NotificationFilterAdapter(getActivity(), notificationFilterArrayList,
                onItemClickCheckUnCheckListener));
    }

    private void selectNotification() {
        SetNotificationFilterModel setNotificationFilterModel = new SetNotificationFilterModel();
        setNotificationFilterModel.setLanguage(Utility.getLanguage(getActivity()));
        setNotificationFilterModel.setUserId(Utility.getUserId(getActivity()));
        setNotificationFilterModel.setCatogorysId(selectNotification);
        Api api = APIClient.getClient().create(Api.class);
        final Call<SuccessfulResponse> responseCall = api.selectCategoryNotification(setNotificationFilterModel);
        responseCall.enqueue(new Callback<SuccessfulResponse>() {
            @Override
            public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse>
                    response) {
                recyclerViewBinding.progressBar.setVisibility(View.GONE);
                selectNotificationResponse(response.body());
            }

            @Override
            public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
            }
        });
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void selectNotificationResponse(SuccessfulResponse myResponse) {
        if (myResponse != null) {
            if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(getActivity(), myResponse.getMessage());
                dialogNotificationFilter.dismiss();
            } else {
            }
        }
    }

    /*Get Notification Status*/
    private void getNotificationStatus() {
        ProgressDialog.getInstance().showProgressDialog(getActivity());
        UserIdModel userIdModel = new UserIdModel();
        userIdModel.setLanguage(Utility.getLanguage(getActivity()));
        userIdModel.setUserId(Utility.getUserId(getActivity()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<NotificationStatusResponse> responseCall = api.getUserNotificationSetting(userIdModel);
        responseCall.enqueue(new Callback<NotificationStatusResponse>() {
            @Override
            public void onResponse(Call<NotificationStatusResponse> call, Response<NotificationStatusResponse>
                    response) {

                if (getActivity() != null && isVisible()) {
                    getNotificationStatusResponse(response.body());
                    Extension extension = Extension.getInstance();
                    if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
                        Utility.setNoInternetPopup(getActivity());
                    } else {
                        getFavoriteDeals();}
                }
            }

            @Override
            public void onFailure(Call<NotificationStatusResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
            }
        });
    }

    private void getNotificationStatusResponse(NotificationStatusResponse myResponse) {
        if (myResponse != null) {
            if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                if (myResponse.getNotificationStatus().equalsIgnoreCase("1")){
                    fragmentBinding.notificationSwitch.setChecked(true);
                }else
                    fragmentBinding.notificationSwitch.setChecked(false);
            } else {
            }
        }
    }

    /*Set Notification Status*/
    private void setNotificationStatus(String notificationStatus) {
        app.com.maksab.util.Extension extension = app.com.maksab.util.Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }else {
            UserIdNotificationModel userIdNotificationModel = new UserIdNotificationModel();
            userIdNotificationModel.setLanguage(Utility.getLanguage(getActivity()));
            userIdNotificationModel.setUserId(Utility.getUserId(getActivity()));
            userIdNotificationModel.setNotificationStatus(notificationStatus);
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> responseCall = api.changeUserNotificationSetting(userIdNotificationModel);
            responseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse>
                        response) {
                    // ProgressDialog.getInstance().dismissDialog();
                    setNotificationStatusResponse(response.body());
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                }
            });
        }
    }

    private void setNotificationStatusResponse(SuccessfulResponse myResponse) {
        if (myResponse != null) {
            if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(getActivity(), myResponse.getMessage());
            } else {
            }
        }
    }
}

