package app.com.maksab.view.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import app.com.maksab.engine.ApiManager;
import app.com.maksab.engine.country.CountryCityManager;
import app.com.maksab.engine.offer.*;
import app.com.maksab.util.*;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import java.util.ArrayList;
import java.util.HashMap;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.databinding.FragmentHomeBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.view.activity.BigBrandActivity;
import app.com.maksab.view.activity.BrandDetailActivity;
import app.com.maksab.view.activity.HomeActivity;
import app.com.maksab.view.activity.OfferDetailsActivity;
import app.com.maksab.view.activity.OfferListActivity;
import app.com.maksab.view.activity.PackagesActivity;
import app.com.maksab.view.activity.SearchActivity;
import app.com.maksab.view.adapter.BrandDataAdapter;
import app.com.maksab.view.adapter.CategoryDataAdapter;
import app.com.maksab.view.adapter.CategoryHomeAdapter;
import app.com.maksab.view.adapter.CollectionAdapter;
import app.com.maksab.view.adapter.HomeCatDataAdapter;
import app.com.maksab.view.adapter.HotDealDataAdapter;
import app.com.maksab.view.adapter.SubCategoryDataAdapter;
import app.com.maksab.view.adapter.TrendingAdapter;
import app.com.maksab.view.fragment.OfferListFragment;
import app.com.maksab.view.viewmodel.LanguageModel;
import retrofit2.Call;
import retrofit2.Callback;


public class StoreFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx
		.OnPageChangeListener {

	public StoreFragment() {
		// Required empty public constructor
	}

	private FragmentHomeBinding binder;
	private final static String STORE_TYPE_ID = "store_type_id";
	private String storeTypeId = "";
	HashMap<String, String> Hash_file_maps;

	public static StoreFragment newInstance(String storeTypeId) {
		Bundle args = new Bundle();
		StoreFragment fragment = new StoreFragment();
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
			//  Utility.showToast(getActivity(), getString(R.string.wrong));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binder = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_home, container, false);
		return binder.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		binder.setFragment(this);

		if (Utility.getIsMember(getActivity()).equalsIgnoreCase("1")) {
			binder.filter.setVisibility(View.GONE);
		}

		Extension extension = Extension.getInstance();
		if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
			Utility.setNoInternetPopup(getActivity());
		} else {
			if (CountryCityManager.sharedInstance().getCountries().size() <= 0) {
				CountryCityManager.sharedInstance().load(new ApiManager.Callback() {
					@Override
					public void OnFinished(String message) {
						if (message != null) {
							Helper.showErrorToast(getActivity(), message);
							return;
						}

						getCategoryList();
					}
				});
			} else {
				getCategoryList();
			}
		}
	}

	public void getCategoryList() {
		ProgressDialog.getInstance().showProgressDialog(getActivity());
		LanguageModel languageModel = new LanguageModel();
		languageModel.setLanguage(HomeActivity.sLanguage);//(Utility.getLanguage(getActivity()));
		Api api = APIClient.getClient().create(Api.class);
		final Call<CategoryHomeResponse> responseCall = api.getCategoryList(languageModel);
		responseCall.enqueue(new Callback<CategoryHomeResponse>() {
			@Override
			public void onResponse(Call<CategoryHomeResponse> call, retrofit2.Response<CategoryHomeResponse> response) {
				try {
					getMyHomeData();
				} catch (Exception e) {
					e.printStackTrace();
				}
				handleStoreListResponse(response.body());
			}

			@Override
			public void onFailure(Call<CategoryHomeResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				Toast.makeText(getActivity(), t + "", Toast.LENGTH_SHORT).show();
				Log.e("", "onFailure: " + t.getLocalizedMessage());
			}
		});
	}


	/**
	 * Handle store list response
	 *
	 * @param categoryHomeResponse @StoreListResponse object
	 */
	private void handleStoreListResponse(CategoryHomeResponse categoryHomeResponse) {
		if (categoryHomeResponse != null) {
			if (categoryHomeResponse.getResponseCode().equals(Api.SUCCESS)) {
				if (categoryHomeResponse.getResultList() != null && categoryHomeResponse.getResultList().size() != 0) {
					try {
						((HomeActivity)getActivity()).categoryLists = categoryHomeResponse.getResultList();
						((HomeActivity)getActivity()).categoryHomeResponse = categoryHomeResponse;
					} catch (Exception e) {
						e.printStackTrace();
					}
					binder.recyclerView.setVisibility(View.VISIBLE);
					setRecyclerView(categoryHomeResponse.getResultList());
				} else {
					binder.recyclerView.setVisibility(View.GONE);
				}
			} else {
				Utility.showToast(getActivity(), getString(R.string.wrong));
			}
		}
	}

	/**
	 * Set recycler view Adapter
	 */
	private void setRecyclerView(ArrayList<CategoryHomeResponse.Category> categoryListArrayList) {
		if (isDetached()) {
			return;
		}
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				CategoryHomeResponse.Category categoryList = (CategoryHomeResponse.Category) obj;
				Intent intent = new Intent(getActivity(), OfferListActivity.class);
				intent.putExtra(Constant.CATEGORY_ID, categoryList.categoryId);
				intent.putExtra(Constant.CATEGORY_NAME, categoryList.categoryName);
				startActivity(intent);
			}
		};
		binder.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
				.HORIZONTAL, false));
		binder.recyclerView.setAdapter(new CategoryHomeAdapter(getActivity(), categoryListArrayList,
				onItemClickListener));
	}

	public void getMyHomeData() {
		String language = LanguageUtil.sharedInstance().getLanguage();
		String countryId = Utility.getCountry(getActivity());
		String cityId = Utility.getCityId(getActivity());
		String userId = Utility.getUserId(getActivity());

		Helper.showProgress(getActivity());
		OfferManager.sharedInstance().loadHomeData(language, countryId, cityId, userId, new ApiManager.Callback() {
			@Override
			public void OnFinished(String message) {
				Helper.hideProgress();
				if (message != null) {
					Helper.showErrorToast(getActivity(), message);
					return;
				}

				updateUI();
			}
		});
	}

	private void updateUI() {
		ArrayList<SliderImage> sliderImages = OfferManager.sharedInstance().sliderImages;
		if (sliderImages.size() > 0) {
			binder.imageSliderLayout.setVisibility(View.VISIBLE);
			updateSliderImages(sliderImages);
		} else {
			binder.imageSliderLayout.setVisibility(View.GONE);
		}

		ArrayList<Brand> brands = OfferManager.sharedInstance().brands;
		if (brands.size() > 0) {
			binder.bigBrandView.setVisibility(View.VISIBLE);
			updateBrands(brands);
		} else {
			binder.bigBrandView.setVisibility(View.GONE);
		}

		ArrayList<Offer> collections = OfferManager.sharedInstance().collections;
		if (collections.size() > 0) {
			binder.collectionsView.setVisibility(View.VISIBLE);
			updateCollections(collections);
		} else {
			binder.collectionsView.setVisibility(View.GONE);
		}

		ArrayList<Offer> trends = OfferManager.sharedInstance().trends;
		if (trends.size() > 0) {
			binder.trendingView.setVisibility(View.VISIBLE);
			updateTrends(trends);
		} else {
			binder.trendingView.setVisibility(View.GONE);
		}

		ArrayList<Offer> hotDeals = OfferManager.sharedInstance().hotDeals;
		if (hotDeals.size() > 0) {
			updateHotDealData(hotDeals);
			binder.HotDealDataView.setVisibility(View.VISIBLE);
		} else {
			binder.HotDealDataView.setVisibility(View.GONE);
		}

		ArrayList<Offer> newDeals = OfferManager.sharedInstance().newDeals;
		if (newDeals.size() > 0) {
			binder.NewDealDataView.setVisibility(View.VISIBLE);
			updateNewDeals(newDeals);
		} else {
			binder.NewDealDataView.setVisibility(View.GONE);
		}

		ArrayList<Category> categories = OfferManager.sharedInstance().categories;
		updateCategory(categories);

		ArrayList<Subcategory> subcategories = OfferManager.sharedInstance().subcategories;
		if (subcategories.size() > 0) {
			binder.recyclerViewSubCat.setVisibility(View.VISIBLE);
			updateSubCategoryData(subcategories);
		} else {
			binder.recyclerViewSubCat.setVisibility(View.GONE);
		}
	}

	private void updateBrands(ArrayList<Brand> brands) {
		if (isDetached()) {
			return;
		}
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				Brand brand = (Brand) obj;
				Intent intent = new Intent(getActivity(), BrandDetailActivity.class);
				intent.putExtra(Constant.BRAND_ID, brand.id);
				startActivity(intent);
			}
		};
		binder.recyclerViewBrand.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
		binder.recyclerViewBrand.setAdapter(new BrandDataAdapter(getActivity(), brands,	onItemClickListener));
	}

	/**
	 * Set recycler view Adapter
	 */
	private void updateCollections(ArrayList<Offer> collections) {
		if (isDetached()) {
			return;
		}

		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				Offer collection = (Offer) obj;
				Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
				intent.putExtra(Constant.OFFER_ID, collection.id);
				startActivityForResult(intent, 1);
			}
		};
		binder.recyclerViewCollections.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
		binder.recyclerViewCollections.setAdapter(new CollectionAdapter(getActivity(), collections, onItemClickListener));
	}

	/**
	 * Set recycler view Adapter
	 */
	private void updateTrends(ArrayList<Offer> trends) {
		if (isDetached()) {
			return;
		}
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				Offer trend = (Offer) obj;
				Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
				intent.putExtra(Constant.OFFER_ID, trend.id);
				startActivityForResult(intent, 1);
			}
		};
		binder.recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
		binder.recyclerViewTrending.setAdapter(new TrendingAdapter(getActivity(), trends, onItemClickListener));
	}

	/**
	 * Set recycler view Adapter
	 */
	private void updateHotDealData(ArrayList<Offer> hotDeals) {
		if (isDetached()) {
			return;
		}
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				Offer hotdealData = (Offer) obj;
				Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
				intent.putExtra(Constant.OFFER_ID, hotdealData.id);
				startActivityForResult(intent, 1);
			}
		};
		binder.recyclerViewHotDealData.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
		binder.recyclerViewHotDealData.setAdapter(new HotDealDataAdapter(getActivity(), hotDeals, onItemClickListener));
	}

	/**
	 * Set recycler view Adapter
	 */
	private void updateNewDeals(ArrayList<Offer> newDeals) {
		if (isDetached()) {
			return;
		}
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				Offer newdealData = (Offer) obj;
				Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
				intent.putExtra(Constant.OFFER_ID, newdealData.id);
				startActivityForResult(intent, 1);
				//startActivity(intent);
			}
		};
		binder.recyclerViewNewdealData.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
		binder.recyclerViewNewdealData.setAdapter(new HomeCatDataAdapter(getActivity(), newDeals, onItemClickListener));
	}

	/**
	 * Set recycler view Adapter
	 */
	private void updateCategory(ArrayList<Category> categories) {
		if (isDetached()) {
			return;
		}

		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				Category categoryData = (Category) obj;
				Intent intent = new Intent(getActivity(), OfferListActivity.class);
				intent.putExtra(Constant.CATEGORY_ID, categoryData.id);
				intent.putExtra(Constant.CATEGORY_NAME, categoryData.name);
				startActivity(intent);
			}
		};

		binder.recyclerViewCat.setLayoutManager(new LinearLayoutManager(getActivity()));
		binder.recyclerViewCat.setAdapter(new CategoryDataAdapter(getActivity(), categories, onItemClickListener));
	}

	/**
	 * Set recycler view Adapter
	 */
	private void updateSubCategoryData(ArrayList<Subcategory> subcategories) {
		if (isDetached()) {
			return;
		}
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				Subcategory subcategoryData = (Subcategory) obj;
				Intent intent = new Intent(getActivity(), OfferListActivity.class);
				intent.putExtra(Constant.CATEGORY_ID, subcategoryData.id);
				startActivity(intent);
			}
		};

		binder.recyclerViewSubCat.setAdapter(new SubCategoryDataAdapter(getActivity(), subcategories, onItemClickListener));
	}

	/**
	 * Set recycler view Adapter
	 */
	private void updateSliderImages(ArrayList<SliderImage> sliderImages) {
		binder.imageSlider.removeAllSliders();

		HashMap<String, String> url_maps = new HashMap<String, String>();
		url_maps.clear();
		for (int i = 0; i < sliderImages.size(); i++) {
			url_maps.put(sliderImages.get(i).bannerText, sliderImages.get(i).bannerImage);
		}

		for (String name : url_maps.keySet()) {
			// TextSliderView textSliderView = new TextSliderView(getActivity());
			app.com.maksab.util.TextSliderView textSliderView = new app.com.maksab.util.TextSliderView(getActivity());
			// initialize a SliderLayout
			textSliderView.description(name)
					.image(url_maps.get(name))
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(this);

			//add your extra information
			textSliderView.bundle(new Bundle());
			textSliderView.getBundle().putString("extra", name);
			//textSliderView.getDescription().
           /* try {
            } catch (Exception e) {
                e.printStackTrace();
            }*/
			try {
				binder.imageSlider.addSlider(textSliderView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		binder.imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		binder.imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		binder.imageSlider.setCustomAnimation(new DescriptionAnimation());
		binder.imageSlider.setDuration(5500);
		binder.imageSlider.addOnPageChangeListener(this);
		// binder.imageSlider.setCustomIndicator(binder.customIndicator);
	}

	@Override
	public void onStop() {
		//make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
		binder.imageSlider.stopAutoCycle();
		super.onStop();
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {
		// Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
		Log.e("Slider Text", slider.getBundle().get("extra") + "");
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		Log.e("Slider Demo", "Page Changed: " + position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	public void openDrawer() {
		((HomeActivity) getActivity()).openDrawer();
	}

	/*Search Offer and Brand*/
	public void onClickSearch() {
		startActivity(new Intent(getActivity(), SearchActivity.class));
	}

	public void onClickPackages() {
		startActivity(new Intent(getActivity(), PackagesActivity.class));
	}


	public void onClickBigBrand() {
        /*((HomeActivity) getActivity()).addFragment(PartnerFragment.newInstance(),
                "PartnerFragment", false);*/
		startActivity(new Intent(getActivity(), BigBrandActivity.class));
	}

	public void onClickFoodDrink() {
		((HomeActivity) getActivity()).addFragment(OfferListFragment.newInstance("1"), "CategoryListFragment", false);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if(resultCode == Activity.RESULT_OK){
				String result=data.getStringExtra("result");
				Log.e("result",result+"");
				getMyHomeData();
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//Write your code if there's no result
			}
		}
	}//onActivityResult
}

