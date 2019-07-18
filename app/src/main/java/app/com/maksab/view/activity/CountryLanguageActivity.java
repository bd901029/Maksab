package app.com.maksab.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CountryCityListResponse;
import app.com.maksab.databinding.ActivityCountryLanguageBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.*;
import app.com.maksab.view.adapter.DialogArListAdapter;
import app.com.maksab.view.adapter.DialogListAdapter;
import app.com.maksab.view.viewmodel.LanguageModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Locale;

public class CountryLanguageActivity extends AppCompatActivity {

    ActivityCountryLanguageBinding activityBinding;
    private String sLanguage = "";
    private String sLanguageT = "";
    private String sCountryIdT = "";
    private String sCountryId = "";
    private String sCityID = "";
    private String fromActivity = "";
    private int sStep = 1;
    private String cityName = "",countryCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_country_language);
        activityBinding.setActivity(this);

        String language = Locale.getDefault().getDisplayLanguage() + "";
        if (language.equals("العربية"))
            sLanguage = Constant.LANGUAGE_ARABIC;
        else
            sLanguage = Constant.LANGUAGE_ENGLISH;

        if (sStep == 1) {
            activityBinding.pageName.setText(getString(R.string.select_country_city));
            activityBinding.llCountry.setVisibility(View.VISIBLE);
            activityBinding.llLanguage.setVisibility(View.GONE);
        } else {
            activityBinding.pageName.setText(getString(R.string.select_language));
            activityBinding.llCountry.setVisibility(View.GONE);
            activityBinding.llLanguage.setVisibility(View.VISIBLE);
        }


activityBinding.pageName.setTypeface(activityBinding.pageName.getTypeface(), Typeface.BOLD);
        getDataFromBundle();
        try {
            Extension extension = Extension.getInstance();
            if (!extension.executeStrategy(CountryLanguageActivity.this, "", ValidationTemplate.INTERNET)) {
                Utility.setNoInternetPopup(CountryLanguageActivity.this);
            } else
                getCountryList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityBinding.btnCencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        activityBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("city_name", cityName);
                intent.putExtra("country_code",countryCode);
                intent.putExtra("country_id",sCountryId);
                intent.putExtra("city_id",sCityID);
                setResult(Activity.RESULT_OK,intent);
                finish();
                /*Intent intent = new Intent(CountryLanguageActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        activityBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    switch (sStep) {
                        case 1:
                            if (!TextUtils.isEmpty(sCountryIdT)) {
                                sStep++;
                                activityBinding.pageName.setText(getString(R.string.select_language));
                                activityBinding.llCountry.setVisibility(View.GONE);
                                activityBinding.llLanguage.setVisibility(View.VISIBLE);
                                activityBinding.back.setVisibility(View.VISIBLE);
                            } else {
                                AlertDialog(getResources().getString(R.string.select_country_city));
                            }

                            break;
                        case 2:
                            if (!TextUtils.isEmpty(sLanguageT)) {
                                Locale locale = new Locale(sLanguage);
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources()
                                        .getDisplayMetrics());

                                if (fromActivity.equals(Constant.FROM_ACTIVITY_SPLASH)) {
                                    Intent intent = new Intent(CountryLanguageActivity.this, IntroActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(CountryLanguageActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            } else
                                AlertDialog(getResources().getString(R.string.please_select_language));
                            break;
                        case 3:
                            break;

                    }
            }
        });
        activityBinding.lnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
                        .LANGUAGE_ENGLISH);
                sLanguage = Constant.LANGUAGE_ENGLISH;
                sLanguageT = sLanguage;
                activityBinding.checkEn.setVisibility(View.VISIBLE);
                activityBinding.checkAr.setVisibility(View.GONE);
                activityBinding.checkTu.setVisibility(View.GONE);
            }
        });
        activityBinding.lnArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //languageModel.setLanguage(Constant.LANGUAGE_ARABIC);
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
                        .LANGUAGE_ARABIC);
                sLanguage = Constant.LANGUAGE_ARABIC;
                sLanguageT = sLanguage;
                activityBinding.checkEn.setVisibility(View.GONE);
                activityBinding.checkAr.setVisibility(View.VISIBLE);
                activityBinding.checkTu.setVisibility(View.GONE);
            }
        });
        activityBinding.lnTurkce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
                        .LANGUAGE_ENGLISH);
                sLanguage = Constant.LANGUAGE_ENGLISH;
                sLanguageT = sLanguage;
                activityBinding.checkEn.setVisibility(View.GONE);
                activityBinding.checkAr.setVisibility(View.GONE);
                activityBinding.checkTu.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        switch (sStep) {
            case 1:
                //super.onBackPressed();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
                break;
            case 2:
                sStep--;
                activityBinding.pageName.setText(getString(R.string.select_country_city));
                activityBinding.llCountry.setVisibility(View.VISIBLE);
                activityBinding.llLanguage.setVisibility(View.GONE);
                activityBinding.back.setVisibility(View.GONE);
                break;
            case 3:
                break;
        }
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromActivity = bundle.getString(Constant.FROM_ACTIVITY);
            if (fromActivity.equalsIgnoreCase(Constant.FROM_ACTIVITY_SIGN_UP)){
                activityBinding.btnNext.setVisibility(View.GONE);
                activityBinding.llSignup.setVisibility(View.VISIBLE);
            }else {
                activityBinding.btnNext.setVisibility(View.VISIBLE);
                activityBinding.llSignup.setVisibility(View.GONE);
            }
        }
    }

    private void getCountryList() {
        ProgressDialog.getInstance().showProgressDialog(CountryLanguageActivity.this);
            LanguageModel languageModel = new LanguageModel();
            languageModel.setLanguage(sLanguage);
            Api api = APIClient.getClient().create(Api.class);
            final Call<CountryCityListResponse> responseCall = api.getCountryCity(languageModel);
            responseCall.enqueue(new Callback<CountryCityListResponse>() {
                @Override
                public void onResponse(Call<CountryCityListResponse> call, Response<CountryCityListResponse> response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        handleCountryListResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<CountryCityListResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                }
            });

    }

    /**
     * Handle category list response
     * @param countryCityResponse @CountryCityListResponse object
     */
    private void handleCountryListResponse(CountryCityListResponse countryCityResponse) {
        try {
            if (countryCityResponse != null) {
                if (countryCityResponse.getResponseCode() != null && countryCityResponse.getResponseCode().equals(Api.SUCCESS)) {
                    switch (sLanguage) {
                        case Constant.LANGUAGE_ARABIC:
                            if (countryCityResponse.getAllCountryData().getArCountryArrayList() != null && countryCityResponse
                                    .getAllCountryData().getArCountryArrayList().size() != 0) {
                                setRecyclerViewCountryAr(countryCityResponse.getAllCountryData().getArCountryArrayList());
                            }
                            break;
                        case Constant.LANGUAGE_ENGLISH:
                            if (countryCityResponse.getAllCountryData().getEnCountryArrayList() != null && countryCityResponse
                                    .getAllCountryData()
                                    .getEnCountryArrayList().size() != 0) {
                                setRecyclerViewCountryEn(countryCityResponse.getAllCountryData().getEnCountryArrayList());
                            }
                            break;
                        case Constant.LANGUAGE_TURKYCE:
                            if (countryCityResponse.getAllCountryData().getEnCountryArrayList() != null && countryCityResponse
                                    .getAllCountryData()
                                    .getEnCountryArrayList().size() != 0) {
                                setRecyclerViewCountryEn(countryCityResponse.getAllCountryData().getEnCountryArrayList());
                            }
                            break;
                        default:
                            if (countryCityResponse.getAllCountryData().getEnCountryArrayList() != null && countryCityResponse
                                    .getAllCountryData()
                                    .getEnCountryArrayList().size() != 0) {
                                setRecyclerViewCountryEn(countryCityResponse.getAllCountryData().getEnCountryArrayList());
                            }
                            break;
                    }

                    if (countryCityResponse.getAllCountryData().getArCountryArrayList() != null && countryCityResponse
                            .getAllCountryData().getArCountryArrayList().size() != 0) {
                    }
                } else {
                }
            }
        } catch (Exception e) {
            Utility.showToast(CountryLanguageActivity.this, getString(R.string.server_not_response));
            e.printStackTrace();
        }
    }

    /**
     * Set recycler view Adapter
     *
     * @param listArrayList Category array list for adapter
     */
    private void setRecyclerViewCountryEn(final ArrayList<CountryCityListResponse.EnCountry> listArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                for (int i = 0; i < listArrayList.size(); i++) {
                    listArrayList.get(i).setStatusCountry(false);
                    for (int j = 0; j < listArrayList.get(i).getCitys().size(); j++) {
                        listArrayList.get(i).getCitys().get(j).setStatus(false);
                    }
                }
                CountryCityListResponse.CityList resultList = (CountryCityListResponse.CityList) obj;
                sCountryId = resultList.getCountryId();
                sCityID = resultList.getCityId();
                countryCode = resultList.getCountryCode();
                cityName = resultList.getCityName();
                sCountryIdT = sCountryId;

                if (resultList.getLanguageList().isEn())
                    activityBinding.lnEnglish.setVisibility(View.VISIBLE);
                else activityBinding.lnEnglish.setVisibility(View.GONE);

                if (resultList.getLanguageList().isAr())
                    activityBinding.lnArabic.setVisibility(View.VISIBLE);
                else activityBinding.lnArabic.setVisibility(View.GONE);

                if (resultList.getLanguageList().isTr())
                    activityBinding.lnTurkce.setVisibility(View.VISIBLE);
                else activityBinding.lnTurkce.setVisibility(View.GONE);

                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY, resultList
                        .getCountryId());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_FLAG, resultList
                        .getCountryFlag());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_NAME, resultList
                        .getCountryName());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_CODE, resultList
                        .getCountryCode());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.CITY_NAME, resultList
                        .getCityName());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.CITY, resultList
                        .getCityId());


            }
        };
        activityBinding.recyclerView.setLayoutManager(new GridLayoutManager
                (CountryLanguageActivity.this, 1));
        activityBinding.recyclerView.setAdapter(new DialogListAdapter
                (CountryLanguageActivity.this, listArrayList, onItemClickListener));
    }


    /**
     * Set recycler view Adapter
     *
     * @param listArrayList Category array list for adapter
     */
    private void setRecyclerViewCountryAr(final ArrayList<CountryCityListResponse.ArCountry> listArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                for (int i = 0; i < listArrayList.size(); i++) {
                    listArrayList.get(i).setStatusCountry(false);

                    for (int j = 0; j < listArrayList.get(i).getCitys().size(); j++) {
                        listArrayList.get(i).getCitys().get(j).setStatus(false);
                    }
                }
                CountryCityListResponse.CityList resultList = (CountryCityListResponse.CityList) obj;
                sCountryId = resultList.getCountryId();
                sCityID = resultList.getCityId();
                countryCode = resultList.getCountryCode();
                cityName = resultList.getCityName();
                sCountryIdT = sCountryId;

                if (resultList.getLanguageList().isEn())
                    activityBinding.lnEnglish.setVisibility(View.VISIBLE);
                else activityBinding.lnEnglish.setVisibility(View.GONE);


                if (resultList.getLanguageList().isAr())
                    activityBinding.lnArabic.setVisibility(View.VISIBLE);
                else activityBinding.lnArabic.setVisibility(View.GONE);

                if (resultList.getLanguageList().isTr())
                    activityBinding.lnTurkce.setVisibility(View.VISIBLE);
                else activityBinding.lnTurkce.setVisibility(View.GONE);


                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY, resultList
                        .getCountryId());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_FLAG, resultList
                        .getCountryFlag());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_NAME, resultList
                        .getCountryName());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_CODE, resultList
                        .getCountryCode());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.CITY_NAME, resultList
                        .getCityName());
                PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.CITY, resultList
                        .getCityId());


            }
        };
        activityBinding.recyclerView.setLayoutManager(new GridLayoutManager
                (CountryLanguageActivity.this, 1));
        activityBinding.recyclerView.setAdapter(new DialogArListAdapter
                (CountryLanguageActivity.this, listArrayList, onItemClickListener));
    }

    public void AlertDialog(String msg){
        Utility.setDialog(CountryLanguageActivity.this, getString(R.string.alert), msg,
                "", getString(R.string.ok), new DialogListener() {
                    @Override
                    public void onNegative(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                    @Override
                    public void onPositive(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
    }

}
