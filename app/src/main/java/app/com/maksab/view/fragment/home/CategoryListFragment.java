package app.com.maksab.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.maksab.R;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryListResponse;
import app.com.maksab.api.dao.StoreListResponse;
import app.com.maksab.databinding.FragmentCategoryListBinding;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.GetCategoryListModel;

public class CategoryListFragment extends Fragment {

    public CategoryListFragment() {
        // Required empty public constructor
    }

    private FragmentCategoryListBinding fragmentBinding;
    private final static String STORE = "store";
    private StoreListResponse.Store store;

    public static CategoryListFragment newInstance(StoreListResponse.Store store) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putParcelable(STORE, store);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            store = bundle.getParcelable(STORE);
        } else {
            Utility.showToast(getActivity(), getString(R.string.wrong));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_category_list, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fragmentBinding.setFragment(this);
        GetCategoryListModel getCategoryListModel = new GetCategoryListModel();
        getCategoryListModel.setStoreId(store.getStoreId());
       // getCategoryList(getCategoryListModel);
        fragmentBinding.setModel(store);

    }


    /**
     * Get List of category for store
     *
     * @param getCategoryListModel @GetCategoryListModel
     */
    /*private void getCategoryList(final GetCategoryListModel getCategoryListModel) {
        binding.recyclerView.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        Api api = APIClient.getClient().create(Api.class);
        final Call<CategoryListResponse> responseCall = api.getAllCategoryListOfStore(getCategoryListModel);
        responseCall.enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                responseCall.cancel();
                handleStoreListResponse(response.body());
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                responseCall.cancel();
                Utility.showToast(getActivity(), getString(R.string.wrong));
            }
        });
    }*/

    /**
     * Handle category list response
     *
     * @param categoryListResponse @CategoryListResponse object
     */
    private void handleStoreListResponse(CategoryListResponse categoryListResponse) {
        if (categoryListResponse.getResponseCode().equals(Api.SUCCESS)) {
            if (categoryListResponse.getCategoryArrayList() != null && categoryListResponse.getCategoryArrayList().size() != 0) {
               // setRecyclerView(categoryListResponse.getCategoryArrayList());
            }
        } else {
            Utility.showToast(getActivity(), categoryListResponse.getResponseMessage());
            getActivity().onBackPressed();
        }
    }

    /**
     * Set recycler view Adapter
     *
     * @param categoryArrayList Category array list for adapter
     */
    /*private void setRecyclerView(ArrayList<CategoryListResponse.Category> categoryArrayList) {
        if (isDetached()) {
            return;
        }
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                CategoryListResponse.Category category = (CategoryListResponse.Category) obj;
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.STORE_ID, store.getStoreId());
                intent.putExtra(ProductListActivity.CATEGORY_ID, category.getCategoryId());
                intent.putExtra(ProductListActivity.CATEGORY_NAME, category.getCategoryName());
                startActivity(intent);
            }
        };
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.recyclerView.setAdapter(new CategoryListAdapter(getActivity(), categoryArrayList, onItemClickListener));
    }*/

}
