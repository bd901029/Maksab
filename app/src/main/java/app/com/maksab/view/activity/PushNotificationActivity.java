package app.com.maksab.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.NotificationFilterResponse;
import app.com.maksab.api.dao.NotificationResponse;
import app.com.maksab.api.dao.NotificationStatusResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityPushNotificationBinding;
import app.com.maksab.databinding.DialogNotificationFilterBinding;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.adapter.NotificationAdapter;
import app.com.maksab.view.adapter.NotificationFilterAdapter;
import app.com.maksab.view.viewmodel.SetNotificationFilterModel;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import app.com.maksab.view.viewmodel.UserIdNotificationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PushNotificationActivity extends AppCompatActivity {

    private ActivityPushNotificationBinding activityBinding;
    private final static String STORE_TYPE_ID = "store_type_id";
    private String storeTypeId = "";
    private Dialog dialogNotificationFilter;
    private DialogNotificationFilterBinding recyclerViewBinding;
    ArrayList<String> selectNotification = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_push_notification);
        activityBinding.setActivity(this);
        getFavoriteDeals();

        /*binder.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getFavoriteDeals();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        activityBinding.notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityBinding.notificationSwitch.isChecked()) {
                    //do stuff when Switch is ON
                    activityBinding.notificationSwitch.setChecked(true);
                    setNotificationStatus("1");
                } else {
                    //do stuff when Switch if OFF
                    activityBinding.notificationSwitch.setChecked(false);
                    setNotificationStatus("0");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        startActivity(new Intent(PushNotificationActivity.this, HomeActivity.class));
        finish();
    }

    public void getFavoriteDeals() {
        UserCityIdModel userCityIdModel = new UserCityIdModel();
        userCityIdModel.setCityId(Utility.getCityId(PushNotificationActivity.this));
        userCityIdModel.setUserId(Utility.getUserId(PushNotificationActivity.this));
        userCityIdModel.setLanguage(Utility.getLanguage(PushNotificationActivity.this));
        Api api = APIClient.getClient().create(Api.class);
        final Call<NotificationResponse> responseCall = api.getNotification(userCityIdModel);
        responseCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, retrofit2.Response<NotificationResponse>
                    response) {
                if (!isDestroyed()) {
                    //binder.swifeRefresh.setRefreshing(false);
                    getNotificationStatus();
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                if (!isDestroyed()) {
                    //binder.swifeRefresh.setRefreshing(false);
                    Utility.showToast(PushNotificationActivity.this, t + "");
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
    private void handleStoreListResponse(NotificationResponse myResponse) {
        try {
            if (myResponse != null) {
                if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    // binder.swifeRefresh.setRefreshing(false);
                    if (myResponse.getNotificationList() != null && myResponse.getNotificationList().size() != 0) {
                        setRecyclerView(myResponse.getNotificationList());
                    }
                } else {
                    // Utility.showToast(PushNotificationActivity.this, storeResponse.getMessage());
                    // PushNotificationActivity.this.onBackPressed();
                }
            }
        } catch (Exception e) {
            Utility.showToast(PushNotificationActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }

    /**
     * Set recycler view Adapter
     *
     * @param myArrayList Category array list for adapter
     */
    private void setRecyclerView(ArrayList<NotificationResponse.NotificationList> myArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
               /* FavoriteOfferListResponse.Category category = (FavoriteOfferListResponse.Category) obj;
                Intent intent = new Intent(PushNotificationActivity.this, StoreDetailsActivity.class);
                intent.putExtra(StoreDetailsActivity.STORE_ID, category.getStoreId());
                intent.putExtra(StoreDetailsActivity.STORE_NAME, category.getStoreName());
                intent.putExtra(StoreDetailsActivity.CATEGORY_ID, "");
                intent.putExtra(StoreDetailsActivity.PRODUCT_TYPE, "");
                startActivity(intent);*/
            }
        };
        activityBinding.recyclerView.setLayoutManager(new GridLayoutManager(PushNotificationActivity.this, 1));
        activityBinding.recyclerView.setAdapter(new NotificationAdapter(PushNotificationActivity.this, myArrayList,
                onItemClickListener));
    }

    public void onClickNotificationFilter() {
        dialogNotificationFilter = new Dialog(PushNotificationActivity.this);
        recyclerViewBinding = DataBindingUtil.inflate(LayoutInflater.from
                        (PushNotificationActivity.this),
                R.layout.dialog_notification_filter, null, false);
        dialogNotificationFilter.setContentView(recyclerViewBinding.getRoot());
        dialogNotificationFilter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                selectNotification();

            }
        });
        dialogNotificationFilter.show();
    }

    private void NotificationFilterList() {
        ProgressDialog.getInstance().showProgressDialog(PushNotificationActivity.this);
        UserIdModel userIdModel = new UserIdModel();
        userIdModel.setLanguage(Utility.getLanguage(PushNotificationActivity.this));
        userIdModel.setUserId(Utility.getUserId(PushNotificationActivity.this));
        Api api = APIClient.getClient().create(Api.class);
        final Call<NotificationFilterResponse> responseCall = api.getUserCategory(userIdModel);
        responseCall.enqueue(new Callback<NotificationFilterResponse>() {
            @Override
            public void onResponse(Call<NotificationFilterResponse> call, Response<NotificationFilterResponse>
                    response) {
                recyclerViewBinding.progressBar.setVisibility(View.GONE);
                ProgressDialog.getInstance().dismissDialog();
                notificationFilterResponse(response.body());
            }

            @Override
            public void onFailure(Call<NotificationFilterResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
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
                    //binder.swifeRefresh.setRefreshing(false);
                    if (myResponse.getCategoryList() != null && myResponse.getCategoryList().size() != 0) {
                        setRecyclerViewNotificationFilter(myResponse.getCategoryList());
                    }
                } else {
                    // Utility.showToast(PushNotificationActivity.this, storeResponse.getMessage());
                    // PushNotificationActivity.this.onBackPressed();
                }
            }
        } catch (Exception e) {
            Utility.showToast(PushNotificationActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }


    private void setRecyclerViewNotificationFilter(ArrayList<NotificationFilterResponse.CategoryList> categoryLists) {
        for (int i = 1; categoryLists.size() > i; i++) {
            if (categoryLists.get(i).getCategoryStatus().equalsIgnoreCase("1")) {
                selectNotification.add(categoryLists.get(i).getCategoryId());
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
        recyclerViewBinding.recyclerView.setLayoutManager(new GridLayoutManager(PushNotificationActivity.this, 1));
        recyclerViewBinding.recyclerView.setAdapter(new NotificationFilterAdapter(PushNotificationActivity.this,
                categoryLists,
                onItemClickCheckUnCheckListener));
    }

    private void selectNotification() {
        SetNotificationFilterModel setNotificationFilterModel = new SetNotificationFilterModel();
        setNotificationFilterModel.setLanguage(Utility.getLanguage(PushNotificationActivity.this));
        setNotificationFilterModel.setUserId(Utility.getUserId(PushNotificationActivity.this));
        setNotificationFilterModel.setCatogorysId(selectNotification);
        Api api = APIClient.getClient().create(Api.class);
        final Call<SuccessfulResponse> responseCall = api.selectCategoryNotification(setNotificationFilterModel);
        responseCall.enqueue(new Callback<SuccessfulResponse>() {
            @Override
            public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse>
                    response) {
                recyclerViewBinding.progressBar.setVisibility(View.GONE);
                // ProgressDialog.getInstance().dismissDialog();
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
     *
     * @param myResponse @CategoryListResponse object
     */
    private void selectNotificationResponse(SuccessfulResponse myResponse) {
        if (myResponse != null) {
            if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(PushNotificationActivity.this, myResponse.getMessage());
                dialogNotificationFilter.dismiss();
            } else {
            }
        }

    }

    /*Get Notification Status*/
    private void getNotificationStatus() {
        ProgressDialog.getInstance().showProgressDialog(PushNotificationActivity.this);
        UserIdModel userIdModel = new UserIdModel();
        userIdModel.setLanguage(Utility.getLanguage(PushNotificationActivity.this));
        userIdModel.setUserId(Utility.getUserId(PushNotificationActivity.this));
        Api api = APIClient.getClient().create(Api.class);
        final Call<NotificationStatusResponse> responseCall = api.getUserNotificationSetting(userIdModel);
        responseCall.enqueue(new Callback<NotificationStatusResponse>() {
            @Override
            public void onResponse(Call<NotificationStatusResponse> call, Response<NotificationStatusResponse>
                    response) {
                ProgressDialog.getInstance().dismissDialog();
                getNotificationStatusResponse(response.body());
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
                Utility.showToast(PushNotificationActivity.this, myResponse.getMessage());
                if (myResponse.getNotificationStatus().equalsIgnoreCase("1")) {
                    activityBinding.notificationSwitch.setChecked(true);
                } else
                    activityBinding.notificationSwitch.setChecked(false);
            } else {
            }
        }

    }

    /*Set Notification Status*/
    private void setNotificationStatus(String notificationStatus) {
        UserIdNotificationModel userIdNotificationModel = new UserIdNotificationModel();
        userIdNotificationModel.setLanguage(Utility.getLanguage(PushNotificationActivity.this));
        userIdNotificationModel.setUserId(Utility.getUserId(PushNotificationActivity.this));
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

    private void setNotificationStatusResponse(SuccessfulResponse myResponse) {
        if (myResponse != null) {
            if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(PushNotificationActivity.this, myResponse.getMessage());
            } else {
            }
        }

    }

}
