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
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import java.util.ArrayList;
import java.util.HashMap;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.api.dao.HomeDataResponse;
import app.com.maksab.databinding.FragmentHomeBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
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
import app.com.maksab.view.fragment.PartnerFragment;
import app.com.maksab.view.viewmodel.HomeDataModel;
import app.com.maksab.view.viewmodel.LanguageModel;
import retrofit2.Call;
import retrofit2.Callback;


public class StoreFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx
        .OnPageChangeListener {

    public StoreFragment() {
        // Required empty public constructor
    }

    private FragmentHomeBinding fragmentBinding;
    private final static String STORE_TYPE_ID = "store_type_id";
    private String storeTypeId = "";
    HashMap<String, String> Hash_file_maps;
    private ArrayList<HomeDataResponse.SliderImages> sliderImagesArrayList;

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
        fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_home,
                container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.setFragment(this);

        if (Utility.getIsMember(getActivity()).equalsIgnoreCase("1")){
            fragmentBinding.filter.setVisibility(View.GONE);
        }

        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }else
        getCategoryList();
    }

    public void getCategoryList() {
        ProgressDialog.getInstance().showProgressDialog(getActivity());
        LanguageModel languageModel = new LanguageModel();
        languageModel.setLanguage(HomeActivity.sLanguage);//(Utility.getLanguage(getActivity()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<CategoryHomeResponse> responseCall = api.getCategoryList(languageModel);
        responseCall.enqueue(new Callback<CategoryHomeResponse>() {
            @Override
            public void onResponse(Call<CategoryHomeResponse> call, retrofit2.Response<CategoryHomeResponse>
                    response) {
                try {
                    myHomeData();
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
                    fragmentBinding.recyclerView.setVisibility(View.VISIBLE);
                   setRecyclerView(categoryHomeResponse.getResultList());
                } else {
                    fragmentBinding.recyclerView.setVisibility(View.GONE);

                }
            } else {
                Utility.showToast(getActivity(), getString(R.string.wrong));
            }
        }
    }

    /**
     * Set recycler view Adapter
     */
    private void setRecyclerView(ArrayList<CategoryHomeResponse.CategoryList> categoryListArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                CategoryHomeResponse.CategoryList categoryList = (CategoryHomeResponse.CategoryList) obj;
                Intent intent = new Intent(getActivity(), OfferListActivity.class);
                intent.putExtra(Constant.CATEGORY_ID, categoryList.categoryId);
                intent.putExtra(Constant.CATEGORY_NAME, categoryList.categoryName);
                startActivity(intent);
            }
        };
        fragmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        fragmentBinding.recyclerView.setAdapter(new CategoryHomeAdapter(getActivity(), categoryListArrayList,
                onItemClickListener));
    }

    public void myHomeData() {
        HomeDataModel homeDataModel = new HomeDataModel();
        homeDataModel.setLanguage(HomeActivity.sLanguage);
        homeDataModel.setCountryId(Utility.getCountry(getActivity()));
        homeDataModel.setCityId(Utility.getCity(getActivity()));
        homeDataModel.setUserId(Utility.getUserId(getActivity()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<HomeDataResponse> responseCall = api.getHomeData(homeDataModel);
        responseCall.enqueue(new Callback<HomeDataResponse>() {
            @Override
            public void onResponse(Call<HomeDataResponse> call, retrofit2.Response<HomeDataResponse>
                    response) {
                ProgressDialog.getInstance().dismissDialog();
                handleHomeDataResponse(response.body());
            }

            @Override
            public void onFailure(Call<HomeDataResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Toast.makeText(getActivity(), t + "", Toast.LENGTH_SHORT).show();
                Log.e("", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    /**
     * Handle store list response
     * @param homeDataResponse @StoreListResponse object
     */
    private void handleHomeDataResponse(HomeDataResponse homeDataResponse) {
        if (homeDataResponse != null) {
            if (homeDataResponse.getResponseCode().equals(Api.SUCCESS)) {
            /*Slider Images*/
                if (homeDataResponse.getSliderImages() != null && homeDataResponse.getSliderImages().size() != 0) {
                    fragmentBinding.imageSliderLayout.setVisibility(View.VISIBLE);
                    setSlider(homeDataResponse.getSliderImages());
                } else fragmentBinding.imageSliderLayout.setVisibility(View.GONE);

            /*Big Brand Data*/
                if (homeDataResponse.getBrandData() != null && homeDataResponse.getBrandData().size() != 0) {
                    fragmentBinding.bigBrandView.setVisibility(View.VISIBLE);
                    setRecyclerViewBrandData(homeDataResponse.getBrandData());
                } else fragmentBinding.bigBrandView.setVisibility(View.GONE);

            /*Collection Data*/
                if (homeDataResponse.getCollectionsData() != null && homeDataResponse.getCollectionsData().size() != 0) {
                    fragmentBinding.collectionsView.setVisibility(View.VISIBLE);
                    setRecyclerViewCollection(homeDataResponse.getCollectionsData());
                } else
                    fragmentBinding.collectionsView.setVisibility(View.GONE);

            /*Trending Data*/
                if (homeDataResponse.getTrendingData() != null && homeDataResponse.getTrendingData().size() != 0) {
                    fragmentBinding.trendingView.setVisibility(View.VISIBLE);
                    setRecyclerViewTrending(homeDataResponse.getTrendingData());
                } else
                    fragmentBinding.trendingView.setVisibility(View.GONE);

            /*Hot Deal Data*/
                if (homeDataResponse.getHotdealData() != null && homeDataResponse.getHotdealData().size() != 0) {
                    fragmentBinding.HotDealDataView.setVisibility(View.VISIBLE);
                    setRecyclerViewHotDealData(homeDataResponse.getHotdealData());
                } else
                    fragmentBinding.HotDealDataView.setVisibility(View.GONE);

            /*New Deal Data*/
                if (homeDataResponse.getNewdealData() != null && homeDataResponse.getNewdealData().size() != 0) {
                    fragmentBinding.NewDealDataView.setVisibility(View.VISIBLE);
                    setRecyclerViewNewDealData(homeDataResponse.getNewdealData());
                } else
                    fragmentBinding.NewDealDataView.setVisibility(View.GONE);

            /*Category Data*/
                if (homeDataResponse.getCategoryData() != null && homeDataResponse.getCategoryData().size() != 0) {
                    setRecyclerViewCategoryData(homeDataResponse.getCategoryData());
                }
                if (homeDataResponse.getSubcategoryData() != null && homeDataResponse.getSubcategoryData().size() != 0) {
                    fragmentBinding.recyclerViewSubCat.setVisibility(View.VISIBLE);
                    setRecyclerViewSubCategoryData(homeDataResponse.getSubcategoryData());
                } else
                    fragmentBinding.recyclerViewSubCat.setVisibility(View.GONE);
            } else {
                Utility.showToast(getActivity(), getString(R.string.wrong));
            }
        }
    }
    /**
     * Set recycler view Adapter
     */
    private void setRecyclerViewBrandData(ArrayList<HomeDataResponse.BrandData> brandDataArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                HomeDataResponse.BrandData brandData = (HomeDataResponse.BrandData) obj;
                Intent intent = new Intent(getActivity(), BrandDetailActivity.class);
                intent.putExtra(Constant.BRAND_ID, brandData.getBrandId());
                startActivity(intent);
            }
        };
        fragmentBinding.recyclerViewBrand.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        fragmentBinding.recyclerViewBrand.setAdapter(new BrandDataAdapter(getActivity(), brandDataArrayList,
                onItemClickListener));
    }

    /**
     * Set recycler view Adapter
     */
    private void setRecyclerViewCollection(ArrayList<HomeDataResponse.CollectionsData> collectionsDataArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                HomeDataResponse.CollectionsData collectionsData = (HomeDataResponse.CollectionsData) obj;
                Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
                intent.putExtra(Constant.OFFER_ID, collectionsData.getOfferId());
                startActivityForResult(intent, 1);
            }
        };
        fragmentBinding.recyclerViewCollections.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        fragmentBinding.recyclerViewCollections.setAdapter(new CollectionAdapter(getActivity(),
                collectionsDataArrayList, onItemClickListener));
    }

    /**
     * Set recycler view Adapter
     */
    private void setRecyclerViewTrending(ArrayList<HomeDataResponse.TrendingData> trendingDataArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                HomeDataResponse.TrendingData offerList = (HomeDataResponse.TrendingData) obj;
                Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
                intent.putExtra(Constant.OFFER_ID, offerList.getOfferId());
                startActivityForResult(intent, 1);
            }
        };
        fragmentBinding.recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        fragmentBinding.recyclerViewTrending.setAdapter(new TrendingAdapter(getActivity(), trendingDataArrayList,
                onItemClickListener));
    }

    /**
     * Set recycler view Adapter
     */
    private void setRecyclerViewHotDealData(ArrayList<HomeDataResponse.HotdealData> hotdealDataArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                HomeDataResponse.HotdealData hotdealData = (HomeDataResponse.HotdealData) obj;
                Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
                intent.putExtra(Constant.OFFER_ID, hotdealData.getOfferId());
                startActivityForResult(intent, 1);
            }
        };
        fragmentBinding.recyclerViewHotDealData.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        fragmentBinding.recyclerViewHotDealData.setAdapter(new HotDealDataAdapter(getActivity(), hotdealDataArrayList,
                onItemClickListener));
    }

    /**
     * Set recycler view Adapter
     */
    private void setRecyclerViewNewDealData(ArrayList<HomeDataResponse.NewdealData> newdealDataArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                HomeDataResponse.NewdealData newdealData = (HomeDataResponse.NewdealData) obj;
                Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
                intent.putExtra(Constant.OFFER_ID, newdealData.getOfferId());
                startActivityForResult(intent, 1);
                //startActivity(intent);
            }
        };
        fragmentBinding.recyclerViewNewdealData.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        fragmentBinding.recyclerViewNewdealData.setAdapter(new HomeCatDataAdapter(getActivity(), newdealDataArrayList,
                onItemClickListener));
    }

    /**
     * Set recycler view Adapter
     */
    private void setRecyclerViewCategoryData(ArrayList<HomeDataResponse.CategoryData> categoryDataArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                HomeDataResponse.CategoryData categoryData = (HomeDataResponse.CategoryData) obj;
                Intent intent = new Intent(getActivity(), OfferListActivity.class);
                intent.putExtra(Constant.CATEGORY_ID, categoryData.getCategoryId());
                intent.putExtra(Constant.CATEGORY_NAME, categoryData.getCategoryName());
                startActivity(intent);
            }
        };

        fragmentBinding.recyclerViewCat.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentBinding.recyclerViewCat.setAdapter(new CategoryDataAdapter(getActivity(), categoryDataArrayList,
                onItemClickListener));
    }

    /**
     * Set recycler view Adapter
     */
    private void setRecyclerViewSubCategoryData(ArrayList<HomeDataResponse.SubcategoryData> subcategoryDataArrayList) {
        if (isDetached()) {
            return;
        }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                HomeDataResponse.SubcategoryData subcategoryData = (HomeDataResponse.SubcategoryData) obj;
                Intent intent = new Intent(getActivity(), OfferListActivity.class);
                intent.putExtra(Constant.CATEGORY_ID, subcategoryData.getSubcategoryId());
                startActivity(intent);
            }
        };

        fragmentBinding.recyclerViewSubCat.setAdapter(new SubCategoryDataAdapter(getActivity(),
                subcategoryDataArrayList, onItemClickListener));
    }

    /**
     * Set recycler view Adapter
     */
    private void setSlider(ArrayList<HomeDataResponse.SliderImages> sliderImagesArrayList) {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.clear();
        for (int i = 0; i < sliderImagesArrayList.size(); i++) {
            url_maps.put(sliderImagesArrayList.get(i).getBannerText(), sliderImagesArrayList.get(i).getBannerImage());
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
            textSliderView.getBundle()
                    .putString("extra", name);
            //textSliderView.getDescription().
           /* try {
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            try {
                fragmentBinding.imageSlider.addSlider(textSliderView);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        fragmentBinding.imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        fragmentBinding.imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        fragmentBinding.imageSlider.setCustomAnimation(new DescriptionAnimation());
        fragmentBinding.imageSlider.setDuration(5500);
        fragmentBinding.imageSlider.addOnPageChangeListener(this);
       // fragmentBinding.imageSlider.setCustomIndicator(fragmentBinding.customIndicator);
    }

    @Override
    public void onStop() {
        //make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        fragmentBinding.imageSlider.stopAutoCycle();
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
        ((HomeActivity) getActivity()).addFragment(OfferListFragment.newInstance("1"), "CategoryListFragment",
                false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Log.e("result",result+"");
                myHomeData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}

