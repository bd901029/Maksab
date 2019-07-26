package app.com.maksab.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import app.com.maksab.engine.country.CountryCityManager;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;

import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.api.dao.OfferFilterResponse;
import app.com.maksab.api.dao.OfferListResponse;
import app.com.maksab.databinding.ActivityOfferListBinding;
import app.com.maksab.databinding.DialogOfferFilterBinding;
import app.com.maksab.databinding.DialogRecyclerViewOnlyBinding;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.CategoriesFilterAdapter;
import app.com.maksab.view.adapter.CategoryHomeAdapter;
import app.com.maksab.view.adapter.DialogSortListAdapter;
import app.com.maksab.view.adapter.LocationFilterAdapter;
import app.com.maksab.view.adapter.OfferGridAdapter;
import app.com.maksab.view.adapter.OfferListAdapter;
import app.com.maksab.view.viewmodel.CityCatIdModel;
import app.com.maksab.view.viewmodel.LanguageModel;
import app.com.maksab.view.viewmodel.OfferListModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferListActivity extends AppCompatActivity {
	ActivityOfferListBinding activityBinding;
	private String sCategoryId = "",sCategoryName = "";
	OfferListModel offerListModel = new OfferListModel();
	private boolean gridView = true;
	private OfferListResponse offerListResponse;
	private Dialog dialogOfferFilter;
	private Dialog dialogSort;
	private DialogOfferFilterBinding dialogOfferFilterBinding;
	private DialogRecyclerViewOnlyBinding recyclerViewBinding;
	ArrayList<String> selectSubCategories = new ArrayList<String>();
	ArrayList<String> selectLocation = new ArrayList<String>();
	private ArrayList<OfferFilterResponse.SortByList>  sortByLists;
	private String sMaxPrice,sMinPrice, sCurrency;
	OfferFilterResponse myResponse;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_offer_list);
		activityBinding.setActivity(this);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			sCategoryId = bundle.getString(Constant.CATEGORY_ID);
			sCategoryName = bundle.getString(Constant.CATEGORY_NAME,"");
			// if (sCategoryName.equalsIgnoreCase(""))
			//    sCategoryName = "";

			String cityName = CountryCityManager.sharedInstance().convertCityName(Utility.getCityId(OfferListActivity.this));
			activityBinding.catName.setText(sCategoryName+" "+getString(R.string.in) + " " + cityName);
			try {
				if (!bundle.getString(Constant.QUERY,"").equalsIgnoreCase("")){
					offerListModel.setQuery(bundle.getString(Constant.QUERY,""));
					activityBinding.pageTitle.setText(getResources().getString(R.string.search_results));
				}
			} catch (Resources.NotFoundException e) {
				e.printStackTrace();
			}
		}

		if (gridView){
			activityBinding.gridImg.setImageResource(R.drawable.grid_hover3x);
			activityBinding.gridText.setTextColor(getResources().getColor(R.color.colorAccent));
			activityBinding.listImg.setImageResource(R.drawable.list3x);
			activityBinding.listText.setTextColor(getResources().getColor(R.color.gray_dark));
		}else {
			activityBinding.gridImg.setImageResource(R.drawable.gridr3x);
			activityBinding.gridText.setTextColor(getResources().getColor(R.color.gray_dark));
			activityBinding.listImg.setImageResource(R.drawable.lis_hovert3x);
			activityBinding.listText.setTextColor(getResources().getColor(R.color.gray_dark));
		}
		Extension extension = Extension.getInstance();
		if (!extension.executeStrategy(OfferListActivity.this, "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(OfferListActivity.this);
		}else
			getCategoryList();


		activityBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					getOfferList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void onClickBack(){
		onBackPressed();
	}

	public void getCategoryList() {
		ProgressDialog.getInstance().showProgressDialog(OfferListActivity.this);
		LanguageModel languageModel = new LanguageModel();
		languageModel.setLanguage(Utility.getLanguage(OfferListActivity.this));
		Api api = APIClient.getClient().create(Api.class);
		final Call<CategoryHomeResponse> responseCall = api.getCategoryList(languageModel);
		responseCall.enqueue(new Callback<CategoryHomeResponse>() {
			@Override
			public void onResponse(Call<CategoryHomeResponse> call, retrofit2.Response<CategoryHomeResponse> response) {
				Extension extension = Extension.getInstance();
				if (!extension.executeStrategy(OfferListActivity.this, "", ValidationTemplate.INTERNET)) {
					Utility.setNoInternetPopup(OfferListActivity.this);
				}else
					getOfferList();
				handleStoreListResponse(response.body());
			}

			@Override
			public void onFailure(Call<CategoryHomeResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				Toast.makeText(OfferListActivity.this, t + "", Toast.LENGTH_SHORT).show();
				Log.e("", "onFailure: " + t.getLocalizedMessage());
			}
		});
	}

	/**
	 * Handle store list response
	 * @param storeListResponse @StoreListResponse object
	 */
	private void handleStoreListResponse(CategoryHomeResponse storeListResponse) {
		if (storeListResponse.getResponseCode().equals(Api.SUCCESS)) {
			if (storeListResponse.getResultList() != null && storeListResponse.getResultList().size() != 0) {
				setRecyclerView(storeListResponse.getResultList());
			} else {

			}
		} else {
			Utility.showToast(OfferListActivity.this, getString(R.string.wrong));
		}
	}

	/**
	 * Set recycler view Adapter
	 */
	private void setRecyclerView(ArrayList<CategoryHomeResponse.Category> categoryListArrayList) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				CategoryHomeResponse.Category categoryList = (CategoryHomeResponse.Category) obj;
				sCategoryName = categoryList.categoryName;
				//((HomeActivity) OfferListActivity.this).addFragment(CategoryListFragment.newInstance(store),
				// "CategoryListFragment",false);
				sCategoryId = categoryList.categoryId;
				activityBinding.catName.setText(sCategoryName+" "+getString(R.string.in) +" "+Utility.getCityName(OfferListActivity.this));
				getOfferList();


			}
		};
		//binder.recyclerView.setLayoutManager(new GridLayoutManager(OfferListActivity.this, 1));
		activityBinding.recyclerView.setLayoutManager(new LinearLayoutManager(OfferListActivity.this, LinearLayoutManager
				.HORIZONTAL, false));
		activityBinding.recyclerView.setAdapter(new CategoryHomeAdapter(OfferListActivity.this, categoryListArrayList,
				onItemClickListener));
	}



	public void getOfferList() {
		activityBinding.swifeRefresh.setRefreshing(true);
		offerListModel.setCategoryId(sCategoryId);
		offerListModel.setUserId(Utility.getUserId(OfferListActivity.this));
		offerListModel.setLanguage(Utility.getLanguage(OfferListActivity.this));
		offerListModel.setCityId(Utility.getCityId(OfferListActivity.this));
		offerListModel.setSubCategoryId(selectSubCategories);
		offerListModel.setLocations(selectLocation);
		offerListModel.setMinPrice(sMinPrice);
		offerListModel.setMaxPrice(sMaxPrice);
		Api api = APIClient.getClient().create(Api.class);
		final Call<OfferListResponse> responseCall = api.offerList(offerListModel);
		responseCall.enqueue(new Callback<OfferListResponse>() {
			@Override
			public void onResponse(Call<OfferListResponse> call, retrofit2.Response<OfferListResponse>
					response) {
				if (!isDestroyed()) {
					activityBinding.swifeRefresh.setRefreshing(false);
					ProgressDialog.getInstance().dismissDialog();
					offerListResponse = response.body();
					handleResponse();
				}
			}

			@Override
			public void onFailure(Call<OfferListResponse> call, Throwable t) {
				if (!isDestroyed()) {
					activityBinding.swifeRefresh.setRefreshing(false);
					ProgressDialog.getInstance().dismissDialog();
					Toast.makeText(OfferListActivity.this, t + "", Toast.LENGTH_SHORT).show();
					Log.e("", "onFailure: " + t.getLocalizedMessage());
				}
			}
		});
	}


	private void handleResponse() {
		if (offerListResponse != null) {
			if (offerListResponse.getResponseCode().equals(Api.SUCCESS)) {
				if (offerListResponse.getOfferList() != null && offerListResponse.getOfferList().size() != 0) {
					activityBinding.recyclerViewOffer.setVisibility(View.VISIBLE);
					setRecyclerViewOfferList(offerListResponse.getOfferList());
				} else {
					activityBinding.recyclerViewOffer.setVisibility(View.GONE);
					Utility.showToast(OfferListActivity.this, getString(R.string.no_data_found));
				}
			} else {
				Utility.showToast(OfferListActivity.this, getString(R.string.wrong));
			}
		}
	}

	/**
	 * Set recycler view Adapter
	 */
	private void setRecyclerViewOfferList(ArrayList<OfferListResponse.OfferList> myArrayList) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				OfferListResponse.OfferList offerList = (OfferListResponse.OfferList) obj;
				//((HomeActivity) OfferListActivity.this).addFragment(CategoryListFragment.newInstance(store),
				// "CategoryListFragment",false);
				Intent intent = new Intent(OfferListActivity.this, OfferDetailsActivity.class);
				intent.putExtra(Constant.OFFER_ID,offerList.getOfferId());
				startActivity(intent);
			}
		};

		if (gridView){
			activityBinding.recyclerViewOffer.setLayoutManager(new GridLayoutManager(OfferListActivity.this,2));
			activityBinding.recyclerViewOffer.setAdapter(new OfferGridAdapter(OfferListActivity.this, myArrayList, onItemClickListener));
		}else {
			activityBinding.recyclerViewOffer.setLayoutManager(new GridLayoutManager(OfferListActivity.this,1));
			activityBinding.recyclerViewOffer.setAdapter(new OfferListAdapter(OfferListActivity.this, myArrayList, onItemClickListener));
		}
	}

	public void onClickGrid(){
		gridView = true;
		activityBinding.gridImg.setImageResource(R.drawable.grid_hover3x);
		activityBinding.gridText.setTextColor(getResources().getColor(R.color.colorAccent));
		activityBinding.listImg.setImageResource(R.drawable.list3x);
		activityBinding.listText.setTextColor(getResources().getColor(R.color.gray_dark));
		handleResponse();
	}

	public void onClickList(){
		gridView = false;
		handleResponse();
		activityBinding.gridImg.setImageResource(R.drawable.gridr3x);
		activityBinding.gridText.setTextColor(getResources().getColor(R.color.gray_dark));
		activityBinding.listImg.setImageResource(R.drawable.lis_hovert3x);
		activityBinding.listText.setTextColor(getResources().getColor(R.color.colorAccent));
	}

	public void onClickOfferFilterData() {
	}

	public void onClickOfferFilter() {

		dialogOfferFilter = new Dialog(OfferListActivity.this);
		dialogOfferFilterBinding = DataBindingUtil.inflate(LayoutInflater.from (OfferListActivity.this),
				R.layout.dialog_offer_filter, null, false);
		dialogOfferFilter.setContentView(dialogOfferFilterBinding.getRoot());
		dialogOfferFilter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialogOfferFilterBinding.category.setText(sCategoryName);

		OfferFilterResponse();

// get min and max text view
		dialogOfferFilterBinding.priceRangeStart.setText(sMinPrice);
		dialogOfferFilterBinding.priceRangeEnd.setText(sMaxPrice);

// set listener
		dialogOfferFilterBinding.rangeSeekbar4.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
			@Override
			public void valueChanged(Number minValue, Number maxValue) {
				sMinPrice = String.valueOf(minValue);
				sMaxPrice = String.valueOf(maxValue);

				dialogOfferFilterBinding.priceRangeStart.setText(sCurrency+sMinPrice);
				dialogOfferFilterBinding.priceRangeEnd.setText(sCurrency+sMaxPrice);
			}
		});

// set final value listener
		dialogOfferFilterBinding.rangeSeekbar4.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
			@Override
			public void finalValue(Number minValue, Number maxValue) {
				Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
			}
		});


		dialogOfferFilterBinding.dialogClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogOfferFilter.dismiss();
			}
		});

		dialogOfferFilterBinding.llSortBy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//setRecyclerViewSortBy();
				onClickSort();
			}
		});

		dialogOfferFilterBinding.search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogOfferFilter.dismiss();
				getOfferList();
			}
		});

		dialogOfferFilterBinding.reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogOfferFilter.dismiss();
				selectSubCategories = null;
				selectLocation = null;
				sMinPrice = "";
				sMaxPrice = "";
				getOfferList();
			}
		});

		dialogOfferFilter.show();
	}

	public void onClickSort() {
		dialogSort = new Dialog(OfferListActivity.this);
		recyclerViewBinding = DataBindingUtil.inflate(LayoutInflater.from
						(OfferListActivity.this),
				R.layout.dialog_recycler_view_only, null, false);
		dialogSort.setContentView(recyclerViewBinding.getRoot());
		dialogSort.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		Window window = dialogSort.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();

		wlp.gravity = Gravity.BOTTOM;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		window.setAttributes(wlp);

		setRecyclerViewSortBy();

		recyclerViewBinding.close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogSort.dismiss();
			}
		});
		dialogSort.show();
	}

	public void getOfferFilter() {
		ProgressDialog.getInstance().showProgressDialog(OfferListActivity.this);

		try {
			selectSubCategories.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

		CityCatIdModel cityCatIdModel = new CityCatIdModel();
		cityCatIdModel.setLanguage(Utility.getLanguage(OfferListActivity.this));
		cityCatIdModel.setCityId(Utility.getCityId(OfferListActivity.this));
		cityCatIdModel.setCategoryId(sCategoryId);
		Api api = APIClient.getClient().create(Api.class);
		final Call<OfferFilterResponse> responseCall = api.getFilterCategory(cityCatIdModel);
		responseCall.enqueue(new Callback<OfferFilterResponse>() {
			@Override
			public void onResponse(Call<OfferFilterResponse> call, Response<OfferFilterResponse>
					response) {
				//dialogOfferFilterBinding.progressBar.setVisibility(View.GONE);
				ProgressDialog.getInstance().dismissDialog();

				myResponse = response.body();
				onClickOfferFilter();

			}

			@Override
			public void onFailure(Call<OfferFilterResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
			}
		});
	}


	private void OfferFilterResponse() {
		if (myResponse != null) {
			if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {

				sMinPrice = myResponse.getMinPrice().replaceAll("[^0-9]", "");
				sMaxPrice = myResponse.getMaxPrice().replaceAll("[^0-9]", "");
				sCurrency = myResponse.getMinPrice().replaceAll("[^A-Za-z]+", "");

				dialogOfferFilterBinding.priceRangeStart.setText(sCurrency+sMinPrice);
				dialogOfferFilterBinding.priceRangeEnd.setText(sCurrency+sMaxPrice);

				//dialogOfferFilterBinding.rangeSeekbar.setRangeValues(Integer.parseInt(sMinPrice), Integer.parseInt
				//        (sMaxPrice));

				if (myResponse.getSubCategoryList() != null && myResponse.getSubCategoryList().size() != 0) {
					setRecyclerViewSubCategoryList(myResponse.getSubCategoryList());
				}

				if (myResponse.getLocationList() != null && myResponse.getLocationList().size() != 0) {
					setRecyclerViewLocation(myResponse.getLocationList());
				}

				if (myResponse.getSortByList() != null && myResponse.getSortByList().size() != 0) {
					sortByLists = myResponse.getSortByList();
                     /* sSortByList = new String[myResponse.getSortByList().size()];
                    for (int i = 0; myResponse.getSortByList().size() > i; i++) {
                        sSortByList[i] = myResponse.getSortByList().get(i).getSortId();
                    }*/
				}
			} else {
				Utility.showToast(OfferListActivity.this, myResponse.getResponseMessage());
			}
		}

	}

	private void setRecyclerViewSubCategoryList(ArrayList<OfferFilterResponse.SubCategoryList> categoryLists) {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				OfferFilterResponse.SubCategoryList subCategoryList = (OfferFilterResponse.SubCategoryList) obj;
				if (subCategoryList.isStatus()){
					selectSubCategories.add(subCategoryList.getSubCategoryId());
				}else {
					selectSubCategories.remove(subCategoryList.getSubCategoryId());
				}
			}
		};

		dialogOfferFilterBinding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(OfferListActivity.this, 1));
		dialogOfferFilterBinding.recyclerViewCategory.setAdapter(new CategoriesFilterAdapter(OfferListActivity.this,
				categoryLists, onItemClickListener));
	}


	private void setRecyclerViewLocation(ArrayList<OfferFilterResponse.LocationList> locationListArrayList) {
		for (int i = 0; locationListArrayList.size() > i; i++) {
           /* if (categoryLists.get(i).getCategoryStatus().equalsIgnoreCase("1")) {
                selectNotification.add(categoryLists.get(i).getCategoryId());
            }*/
			// locationListArrayList.get(i).setCategoryStatus("0");

		}
		OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener = new OnItemClickCheckUnCheckListener() {

			@Override
			public void onAdd(int id, Object obj) {
				OfferFilterResponse.LocationList subCategoryList = (OfferFilterResponse.LocationList) obj;
				// subCategoryList.setCategoryStatus("1");
				selectLocation.add(subCategoryList.getLocationId());
			}

			@Override
			public void onRemove(int id, Object obj) {
				OfferFilterResponse.LocationList subCategoryList = (OfferFilterResponse.LocationList) obj;
				// subCategoryList.setCategoryStatus("0");
				selectLocation.remove(subCategoryList.getLocationId());
			}
		};
		dialogOfferFilterBinding.recyclerViewLocation.setLayoutManager(new GridLayoutManager(OfferListActivity.this, 1));
		dialogOfferFilterBinding.recyclerViewLocation.setAdapter(new LocationFilterAdapter(OfferListActivity.this,
				locationListArrayList,
				onItemClickCheckUnCheckListener));
	}

    /*public void sortBy(){
        Log.e("sSortByList",sSortByList+"");
        final AlertDialog.Builder builder = new AlertDialog.Builder(OfferListActivity.this);
        builder.setTitle(R.string.sort_by);
        //final String[] sPaymentTypeList  = getResources().getStringArray(R.array.payment_type);
        builder.setItems(sSortByList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //   mBinding.paymentType.setText(sortByLists[which]);
            }
        });
        builder.show();
    }*/


	private void setRecyclerViewSortBy() {
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				OfferFilterResponse.SortByList resultList = (OfferFilterResponse.SortByList) obj;
				dialogOfferFilterBinding.sortBy.setText(resultList.getSortText());
				offerListModel.setSortBy(resultList.getSortId());
				// mBinding.tvCityName.setText(resultList.getName());
				// sCityId = resultList.getCityId();
				dialogSort.dismiss();
			}
		};
		recyclerViewBinding.recyclerView.setLayoutManager(new GridLayoutManager
				(OfferListActivity.this, 1));
		recyclerViewBinding.recyclerView.setAdapter(new DialogSortListAdapter (OfferListActivity.this, sortByLists, onItemClickListener));
	}



}
