package app.com.maksab.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.api.dao.OfferListResponse;
import app.com.maksab.databinding.FragmentOfferListBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.activity.OfferDetailsActivity;
import app.com.maksab.view.adapter.CategoryHomeAdapter;
import app.com.maksab.view.adapter.OfferGridAdapter;
import app.com.maksab.view.adapter.OfferListAdapter;
import app.com.maksab.view.viewmodel.LanguageModel;
import app.com.maksab.view.viewmodel.OfferListModel;
import retrofit2.Call;
import retrofit2.Callback;


public class OfferListFragment extends Fragment {

	public OfferListFragment() {
		// Required empty public constructor
	}
	private FragmentOfferListBinding binder;
	private String sCategoryId = "";
	OfferListModel offerListModel = new OfferListModel();
	private boolean gridView = true;
	private OfferListResponse offerListResponse;

	public static OfferListFragment newInstance(String storeTypeId) {
		Bundle args = new Bundle();
		OfferListFragment fragment = new OfferListFragment();
		args.putString(Constant.CATEGORY_ID, storeTypeId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			sCategoryId = bundle.getString(Constant.CATEGORY_ID);
		} else {
			Utility.showToast(getActivity(), getString(R.string.wrong));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binder = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_offer_list,
				container, false);
		return binder.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		binder.setFragment(this);

		if (gridView){
			binder.gridImg.setImageResource(R.drawable.grid_hover3x);
			binder.gridText.setTextColor(getResources().getColor(R.color.colorAccent));
			binder.listImg.setImageResource(R.drawable.list3x);
			binder.listText.setTextColor(getResources().getColor(R.color.gray_dark));
		}else {
			binder.gridImg.setImageResource(R.drawable.gridr3x);
			binder.gridText.setTextColor(getResources().getColor(R.color.gray_dark));
			binder.listImg.setImageResource(R.drawable.lis_hovert3x);
			binder.listText.setTextColor(getResources().getColor(R.color.gray_dark));
		}

		getCategoryList();
	}

	public void getCategoryList() {
		ProgressDialog.getInstance().showProgressDialog(getActivity());
		LanguageModel languageModel = new LanguageModel();
		languageModel.setLanguage(Utility.getLanguage(getActivity()));
		Api api = APIClient.getClient().create(Api.class);
		final Call<CategoryHomeResponse> responseCall = api.getCategoryList(languageModel);
		responseCall.enqueue(new Callback<CategoryHomeResponse>() {
			@Override
			public void onResponse(Call<CategoryHomeResponse> call, retrofit2.Response<CategoryHomeResponse> response) {
				getOfferList(sCategoryId);
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
	 * @param storeListResponse @StoreListResponse object
	 */
	private void handleStoreListResponse(CategoryHomeResponse storeListResponse) {
		if (storeListResponse.getResponseCode().equals(Api.SUCCESS)) {
			if (storeListResponse.getResultList() != null && storeListResponse.getResultList().size() != 0) {
				setRecyclerView(storeListResponse.getResultList());
			} else {
			}
		} else {
			Utility.showToast(getActivity(), getString(R.string.wrong));
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
				CategoryHomeResponse.Category category = (CategoryHomeResponse.Category) obj;
				//((HomeActivity) getActivity()).addFragment(CategoryListFragment.newInstance(store),
				// "CategoryListFragment",false);
				Log.i("dragon", category.categoryName);
				binder.catName.setText(category.categoryName);
				getOfferList(category.categoryId);
			}
		};
		//binder.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
		binder.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
				.HORIZONTAL, true));
		binder.recyclerView.setAdapter(new CategoryHomeAdapter(getActivity(), categoryListArrayList,
				onItemClickListener));
	}



	public void getOfferList(String categoryId) {
		binder.progressBar.setVisibility(View.VISIBLE);
		offerListModel.setCategoryId(categoryId);
		offerListModel.setUserId(Utility.getUserId(getActivity()));
		offerListModel.setLanguage(Utility.getLanguage(getActivity()));
		Api api = APIClient.getClient().create(Api.class);
		final Call<OfferListResponse> responseCall = api.offerList(offerListModel);
		responseCall.enqueue(new Callback<OfferListResponse>() {
			@Override
			public void onResponse(Call<OfferListResponse> call, retrofit2.Response<OfferListResponse>
					response) {
				ProgressDialog.getInstance().dismissDialog();
				offerListResponse = response.body();
				handleResponse();
			}

			@Override
			public void onFailure(Call<OfferListResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				Toast.makeText(getActivity(), t + "", Toast.LENGTH_SHORT).show();
				Log.e("", "onFailure: " + t.getLocalizedMessage());
			}
		});
	}


	private void handleResponse() {
		binder.progressBar.setVisibility(View.GONE);
		if (offerListResponse.getResponseCode().equals(Api.SUCCESS)) {
			if (offerListResponse.getOfferList() != null && offerListResponse.getOfferList().size() != 0) {
				binder.recyclerViewOffer.setVisibility(View.VISIBLE);
				setRecyclerViewOfferList(offerListResponse.getOfferList());
			} else {
				binder.recyclerViewOffer.setVisibility(View.GONE);
				Utility.showToast(getActivity(), getString(R.string.no_data_found));
			}
		} else {
			Utility.showToast(getActivity(), getString(R.string.wrong));
		}
	}

	/**
	 * Set recycler view Adapter
	 */
	private void setRecyclerViewOfferList(ArrayList<OfferListResponse.OfferList> myArrayList) {
		if (isDetached()) {
			return;
		}
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				OfferListResponse.OfferList offerList = (OfferListResponse.OfferList) obj;
				//((HomeActivity) getActivity()).addFragment(CategoryListFragment.newInstance(store),
				// "CategoryListFragment",false);
				Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
				intent.putExtra(Constant.OFFER_ID,offerList.getOfferId());
				startActivity(intent);
			}
		};

		if (gridView){
			binder.recyclerViewOffer.setLayoutManager(new GridLayoutManager(getActivity(),2));
			binder.recyclerViewOffer.setAdapter(new OfferGridAdapter(getActivity(), myArrayList, onItemClickListener));
		}else {
			binder.recyclerViewOffer.setLayoutManager(new GridLayoutManager(getActivity(),1));
			binder.recyclerViewOffer.setAdapter(new OfferListAdapter(getActivity(), myArrayList, onItemClickListener));
		}
	}

	public void onClickGrid(){
		gridView = true;
		binder.gridImg.setImageResource(R.drawable.grid_hover3x);
		binder.gridText.setTextColor(getResources().getColor(R.color.colorAccent));
		binder.listImg.setImageResource(R.drawable.list3x);
		binder.listText.setTextColor(getResources().getColor(R.color.gray_dark));
		handleResponse();
	}

	public void onClickList(){
		gridView = false;
		handleResponse();
		binder.gridImg.setImageResource(R.drawable.gridr3x);
		binder.gridText.setTextColor(getResources().getColor(R.color.gray_dark));
		binder.listImg.setImageResource(R.drawable.lis_hovert3x);
		binder.listText.setTextColor(getResources().getColor(R.color.colorAccent));
	}
}

