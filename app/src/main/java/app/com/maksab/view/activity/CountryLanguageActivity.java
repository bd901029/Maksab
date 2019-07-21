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
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CountryCityListResponse;
import app.com.maksab.databinding.ActivityCountryLanguageBinding;
import app.com.maksab.engine.ApiManager;
import app.com.maksab.engine.country.City;
import app.com.maksab.engine.country.Country;
import app.com.maksab.engine.country.CountryManager;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.*;
import app.com.maksab.view.adapter.DialogArListAdapter;
import app.com.maksab.view.adapter.CountryListAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class CountryLanguageActivity extends AppCompatActivity {

	ActivityCountryLanguageBinding binder;
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
		binder = DataBindingUtil.setContentView(this, R.layout.activity_country_language);
		binder.setActivity(this);

		String language = Locale.getDefault().getDisplayLanguage() + "";
		if (language.equals("العربية"))
			sLanguage = Constant.LANGUAGE_ARABIC;
		else
			sLanguage = Constant.LANGUAGE_ENGLISH;
		LanguageUtil.sharedInstance().setLanguage(sLanguage);

		if (sStep == 1) {
			binder.pageName.setText(getString(R.string.select_country_city));
			binder.llCountry.setVisibility(View.VISIBLE);
			binder.llLanguage.setVisibility(View.GONE);
		} else {
			binder.pageName.setText(getString(R.string.select_language));
			binder.llCountry.setVisibility(View.GONE);
			binder.llLanguage.setVisibility(View.VISIBLE);
		}


		binder.pageName.setTypeface(binder.pageName.getTypeface(), Typeface.BOLD);
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
		binder.btnCencel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(Activity.RESULT_CANCELED, returnIntent);
				finish();
			}
		});

		binder.btnDone.setOnClickListener(new View.OnClickListener() {
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

		binder.btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				switch (sStep) {
					case 1:
						if (!TextUtils.isEmpty(sCountryIdT)) {
							sStep++;
							binder.pageName.setText(getString(R.string.select_language));
							binder.llCountry.setVisibility(View.GONE);
							binder.llLanguage.setVisibility(View.VISIBLE);
							binder.back.setVisibility(View.VISIBLE);
						} else {
							AlertDialog(getResources().getString(R.string.select_country_city));
						}

						break;
					case 2:
						if (!TextUtils.isEmpty(sLanguageT)) {
							LanguageUtil.sharedInstance().setLanguage(sLanguage);

							Configuration config = new Configuration();
							config.locale = new Locale(LanguageUtil.sharedInstance().getLanguage());

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
		binder.lnEnglish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
						.LANGUAGE_ENGLISH);
				sLanguage = Constant.LANGUAGE_ENGLISH;
				sLanguageT = sLanguage;
				binder.checkEn.setVisibility(View.VISIBLE);
				binder.checkAr.setVisibility(View.GONE);
				binder.checkTu.setVisibility(View.GONE);
			}
		});
		binder.lnArabic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//languageModel.setLanguage(Constant.LANGUAGE_ARABIC);
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
						.LANGUAGE_ARABIC);
				sLanguage = Constant.LANGUAGE_ARABIC;
				sLanguageT = sLanguage;
				binder.checkEn.setVisibility(View.GONE);
				binder.checkAr.setVisibility(View.VISIBLE);
				binder.checkTu.setVisibility(View.GONE);
			}
		});
		binder.lnTurkce.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
						.LANGUAGE_ENGLISH);
				sLanguage = Constant.LANGUAGE_ENGLISH;
				sLanguageT = sLanguage;
				binder.checkEn.setVisibility(View.GONE);
				binder.checkAr.setVisibility(View.GONE);
				binder.checkTu.setVisibility(View.VISIBLE);
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
				binder.pageName.setText(getString(R.string.select_country_city));
				binder.llCountry.setVisibility(View.VISIBLE);
				binder.llLanguage.setVisibility(View.GONE);
				binder.back.setVisibility(View.GONE);
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
				binder.btnNext.setVisibility(View.GONE);
				binder.llSignup.setVisibility(View.VISIBLE);
			}else {
				binder.btnNext.setVisibility(View.VISIBLE);
				binder.llSignup.setVisibility(View.GONE);
			}
		}
	}

	/*
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
	*/

	private void getCountryList() {
		Helper.showProgress(this);

		CountryManager.sharedInstance().load(LanguageUtil.sharedInstance().getLanguage(), new ApiManager.Callback() {
			@Override
			public void OnFinished(String message) {
				Helper.hideProgress();

				if (message != null) {
					Helper.showErrorToast(CountryLanguageActivity.this, message);
					return;
				}

				handleCountryResponse();
			}
		});
	}

	private void handleCountryResponse() {
		final ArrayList<Country> countries = CountryManager.sharedInstance().countries;

		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				for (int i = 0; i < countries.size(); i++) {
					countries.get(i).setSelected(false);
					for (int j = 0; j < countries.get(i).cities.size(); j++) {
						countries.get(i).cities.get(j).setSelected(false);
					}
				}

				City city = (City) obj;
				sCountryId = city.country.id;
				sCityID = city.id;
				countryCode = city.country.code;
				cityName = city.getName();
				sCountryIdT = sCountryId;

				if (city.isSupported(LanguageUtil.ENGLISH))
					binder.lnEnglish.setVisibility(View.VISIBLE);
				else
					binder.lnEnglish.setVisibility(View.GONE);

				if (city.isSupported(LanguageUtil.ARABIC))
					binder.lnArabic.setVisibility(View.VISIBLE);
				else
					binder.lnArabic.setVisibility(View.GONE);

				if (city.isSupported(LanguageUtil.TURKYCE))
					binder.lnTurkce.setVisibility(View.VISIBLE);
				else
					binder.lnTurkce.setVisibility(View.GONE);

				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY, city.country.id);
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_FLAG, city.country.flag);
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_NAME, city.country.getName());
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.COUNTRY_CODE, city.country.code);
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.CITY_NAME, city.getName());
				PreferenceConnector.writeString(CountryLanguageActivity.this, PreferenceConnector.CITY, city.id);


			}
		};

		binder.recyclerView.setLayoutManager(new GridLayoutManager(CountryLanguageActivity.this, 1));
		binder.recyclerView.setAdapter(new CountryListAdapter(CountryLanguageActivity.this, countries, onItemClickListener));
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
					binder.lnEnglish.setVisibility(View.VISIBLE);
				else binder.lnEnglish.setVisibility(View.GONE);

				if (resultList.getLanguageList().isAr())
					binder.lnArabic.setVisibility(View.VISIBLE);
				else binder.lnArabic.setVisibility(View.GONE);

				if (resultList.getLanguageList().isTr())
					binder.lnTurkce.setVisibility(View.VISIBLE);
				else binder.lnTurkce.setVisibility(View.GONE);

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
//		binder.recyclerView.setLayoutManager(new GridLayoutManager(CountryLanguageActivity.this, 1));
//		binder.recyclerView.setAdapter(new CountryListAdapter(CountryLanguageActivity.this, listArrayList, onItemClickListener));
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
					binder.lnEnglish.setVisibility(View.VISIBLE);
				else binder.lnEnglish.setVisibility(View.GONE);


				if (resultList.getLanguageList().isAr())
					binder.lnArabic.setVisibility(View.VISIBLE);
				else binder.lnArabic.setVisibility(View.GONE);

				if (resultList.getLanguageList().isTr())
					binder.lnTurkce.setVisibility(View.VISIBLE);
				else binder.lnTurkce.setVisibility(View.GONE);


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
		binder.recyclerView.setLayoutManager(new GridLayoutManager
				(CountryLanguageActivity.this, 1));
		binder.recyclerView.setAdapter(new DialogArListAdapter
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
