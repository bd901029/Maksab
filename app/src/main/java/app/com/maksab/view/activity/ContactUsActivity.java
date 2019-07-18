package app.com.maksab.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.ContactUsResponse;
import app.com.maksab.databinding.ActivityContactBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.ContactUsLocationAdapter;
import app.com.maksab.view.viewmodel.ContactUsModel;
import app.com.maksab.view.viewmodel.CountryIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class ContactUsActivity extends AppCompatActivity {
    private ActivityContactBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact);
        activityBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ContactUsModel contactUsModel = new ContactUsModel();
        //loginModel.setEmail("user@gmail.com");
        activityBinding.setModel(contactUsModel);
        getContactUs();
    }
    //sir maksab apk de rha hu check kr lena(language,language to change view, select Countr )

    public void getContactUs() {
        ProgressDialog.getInstance().showProgressDialog(ContactUsActivity.this);
        CountryIdModel countryIdModel = new CountryIdModel();
        countryIdModel.setCountryId(Utility.getCountry(ContactUsActivity.this));
        countryIdModel.setLanguage(Utility.getLanguage(getApplicationContext()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<ContactUsResponse> responseCall = api.getContact(countryIdModel);
        responseCall.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, retrofit2.Response<ContactUsResponse>
                    response) {
                if (!isDestroyed()) {
                    ProgressDialog.getInstance().dismissDialog();
                    handleResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()) {
                    Utility.showToast(ContactUsActivity.this, t + "");
                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * Handle category list response
     * @param myResponse @CategoryListResponse object
     */
    private void handleResponse(ContactUsResponse myResponse) {
        try {
            if (myResponse != null) {
                if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    if (myResponse.getContactListArrayList() != null && myResponse.getContactListArrayList().size() != 0) {
                        setRecyclerView(myResponse.getContactListArrayList());
                    }
                } else {
                }
            }
        } catch (Exception e) {
            Utility.showToast(ContactUsActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }

    /**
     * Set recycler view Adapter
     * @param myArrayList Category array list for adapter
     */
    private void setRecyclerView(ArrayList<ContactUsResponse.ContactList> myArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {

            }
        };
        activityBinding.recyclerView.setLayoutManager(new GridLayoutManager(ContactUsActivity.this, 1));
        activityBinding.recyclerView.setAdapter(new ContactUsLocationAdapter(ContactUsActivity.this, myArrayList,
                onItemClickListener));
    }

    /**
     * Check string empty or not
     *
     * @param str string to check
     * @return true if empty else false
     */
    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * Validate Login form
     *
     * @param contactUsModel LoginModel

     * @return true if valid else false
     */
    private boolean validate(ContactUsModel contactUsModel) {
        app.com.maksab.util.Extension extension = app.com.maksab.util.Extension.getInstance();
        if (isEmpty(contactUsModel.getContactName()) || isEmpty(contactUsModel.getContactEmail())
                || isEmpty(contactUsModel.getContactSubject())|| isEmpty(contactUsModel.getContactMessage())) {
            if (contactUsModel.getContactName().isEmpty()) {
                activityBinding.nameEdt.setError(getText(R.string.error_f_name));
            }
            if (contactUsModel.getContactEmail().isEmpty()) {
                activityBinding.emailEdt.setError(getText(R.string.error_email));
            }
            if (contactUsModel.getContactSubject().isEmpty()) {
                activityBinding.subjectEdt.setError(getText(R.string.error_subject));
            }
            if (contactUsModel.getContactMessage().isEmpty()) {
                activityBinding.messageEdt.setError(getText(R.string.error_massage));
            }
            Utility.showToast(ContactUsActivity.this, getString(R.string.email_pass));
            return false;
        }else if (!extension.executeStrategy(ContactUsActivity.this, contactUsModel.getContactEmail(), ValidationTemplate.EMAIL)) {
            Utility.showToast(ContactUsActivity.this, getString(R.string.invalid_email));
            return false;
        }
        else if (!extension.executeStrategy(ContactUsActivity.this, contactUsModel.getContactEmail(), ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(ContactUsActivity.this);
            return false;
        } else {
            return true;
        }
    }

    public void onClickSave(ContactUsModel contactUsModel) {
        if (validate(contactUsModel)) {

            ProgressDialog.getInstance().showProgressDialog(ContactUsActivity.this);
            CountryIdModel countryIdModel = new CountryIdModel();
            countryIdModel.setCountryId(Utility.getCountry(ContactUsActivity.this));
            countryIdModel.setLanguage(Utility.getLanguage(getApplicationContext()));
            Api api = APIClient.getClient().create(Api.class);
            final Call<ContactUsResponse> responseCall = api.getContact(countryIdModel);
            responseCall.enqueue(new Callback<ContactUsResponse>() {
                @Override
                public void onResponse(Call<ContactUsResponse> call, retrofit2.Response<ContactUsResponse>
                        response) {
                    if (!isDestroyed()) {
                        ProgressDialog.getInstance().dismissDialog();
                        handleResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        Utility.showToast(ContactUsActivity.this, t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    public void onClickBack(){
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}