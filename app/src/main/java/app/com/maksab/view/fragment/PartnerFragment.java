package app.com.maksab.view.fragment;

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
import java.util.Arrays;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AlphabetResponse;
import app.com.maksab.api.dao.FavoritePartnerListResponse;
import app.com.maksab.databinding.FragmentPartnerBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.Extension;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.activity.BrandDetailActivity;
import app.com.maksab.view.adapter.AlphabetItemAdapter;
import app.com.maksab.view.adapter.FavoritesAllPartnersAdapter;
import app.com.maksab.view.viewmodel.UserCityIdModel;
import retrofit2.Call;
import retrofit2.Callback;


public class PartnerFragment extends Fragment {

    public PartnerFragment() {
        // Required empty public constructor
    }

    private FragmentPartnerBinding fragmentBinding;
    private String sType = "";
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<AlphabetResponse> alphabetResponseArrayList ;
    private String[] aPartnerEnglish = {"All","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","S","T",
            "U","V","W","X","Y","Z"};
    private String[] aPartnerArabic = {"الجميع","ا","ب","ت","ث","ج","ح","خ","د","ذ","ر","ز","س","ش","ص","ض","ط","ظ","ع","غ","ف",
            "ق","ك","ل","م","ن","ه","و","ي"};

    public static PartnerFragment newInstance() {
        Bundle args = new Bundle();
        PartnerFragment fragment = new PartnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_partner, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.setFragment(this);

        sType = "all";
        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }else
        getPartners();

        fragmentBinding.swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getPartners();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
if (Utility.getLanguage(getActivity()).equalsIgnoreCase(Constant.LANGUAGE_ARABIC)){
    stringArrayList.addAll(Arrays.asList(aPartnerArabic));
}else {
    stringArrayList.addAll(Arrays.asList(aPartnerEnglish));
}

        this.alphabetResponseArrayList = new ArrayList<>();
             for (int i = 0; i < stringArrayList.size(); i++){
             System.out.println(stringArrayList.get(i));
                 AlphabetResponse alphabetResponse = new AlphabetResponse();
                 alphabetResponse.setAlphabet(stringArrayList.get(i));
             this.alphabetResponseArrayList.add(alphabetResponse);
                 System.out.println(alphabetResponseArrayList.get(i).getAlphabet());
             }
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                AlphabetResponse alphabetResponse = (AlphabetResponse) obj;
                sType = alphabetResponse.getAlphabet();
                getPartners();
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentBinding.recyclerViewAlphabet.setLayoutManager(linearLayoutManager);
        fragmentBinding.recyclerViewAlphabet.setAdapter(new AlphabetItemAdapter(getActivity(),
                this.alphabetResponseArrayList,onItemClickListener));

    }


    public void getPartners() {
        fragmentBinding.swifeRefresh.setRefreshing(true);
        UserCityIdModel userCityIdModel = new UserCityIdModel();
        userCityIdModel.setCityId(Utility.getCityId(getActivity()));
        userCityIdModel.setType(sType);
        userCityIdModel.setUserId(Utility.getUserId(getActivity()));
        userCityIdModel.setLanguage(Utility.getLanguage(getActivity()));
        Api api = APIClient.getClient().create(Api.class);
        final Call<FavoritePartnerListResponse> responseCall = api.getAllBrand(userCityIdModel);
        responseCall.enqueue(new Callback<FavoritePartnerListResponse>() {
            @Override
            public void onResponse(Call<FavoritePartnerListResponse> call, retrofit2.Response<FavoritePartnerListResponse>
                    response) {
                if (getActivity() != null && isVisible()) {
                    fragmentBinding.swifeRefresh.setRefreshing(false);
                    handleStoreListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FavoritePartnerListResponse> call, Throwable t) {
                if (getActivity() != null && isVisible()) {
                    ProgressDialog.getInstance().dismissDialog();
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
    private void handleStoreListResponse(FavoritePartnerListResponse myResponse) {
        try {
            if (myResponse != null){
                if (myResponse.getResponseCode()!= null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
                    if (myResponse.getBrandData() != null && myResponse.getBrandData().size() != 0) {
                        fragmentBinding.recyclerView.setVisibility(View.VISIBLE);
                        setRecyclerView(myResponse.getBrandData());
                    }else {
                        //Utility.showToast(getActivity(), myResponse.getMessage());
                        Utility.showToast(getActivity(), getString(R.string.no_data_found));
                        fragmentBinding.recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    //Utility.showToast(getActivity(), myResponse.getMessage());
                    Utility.showToast(getActivity(), getString(R.string.no_data_found));
                    fragmentBinding.recyclerView.setVisibility(View.GONE);
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
    private void setRecyclerView(ArrayList<FavoritePartnerListResponse.BrandDataList> myArrayList) {
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                FavoritePartnerListResponse.BrandDataList brandData = (FavoritePartnerListResponse.BrandDataList) obj;
                Intent intent = new Intent(getActivity(), BrandDetailActivity.class);
                intent.putExtra(Constant.BRAND_ID, brandData.getBrandId());
                startActivity(intent);
            }
        };
        fragmentBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        fragmentBinding.recyclerView.setAdapter(new FavoritesAllPartnersAdapter(getActivity(), myArrayList, onItemClickListener));
    }
}

