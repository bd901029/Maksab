package app.com.maksab.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import app.com.maksab.R;
import app.com.maksab.databinding.FragmentFavoritesBinding;
import app.com.maksab.util.Extension;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.adapter.TabAdapter;
import app.com.maksab.view.fragment.FavoritesPartnersFragment;
import app.com.maksab.view.fragment.FavoritesTypeFragment;
import app.com.maksab.view.viewmodel.GetStoreListModel;

public class FavoritesFragment extends Fragment {
    public FavoritesFragment() {
        // Required empty public constructor
    }

    private FragmentFavoritesBinding fragmentBinding;
    private final static String STORE_TYPE_ID = "store_type_id";
    private String storeTypeId = "";

    public static FavoritesFragment newInstance(String storeTypeId) {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
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
            Utility.showToast(getActivity(), getString(R.string.wrong));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_favorites, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.setFragment(this);
        Extension extension = Extension.getInstance();
        if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
        }

        if (!TextUtils.isEmpty(storeTypeId)) {
            GetStoreListModel getStoreListModel = new GetStoreListModel();
            getStoreListModel.setStoreTypeId(storeTypeId);
        } else {
            getActivity().onBackPressed();
        }

        addTab();
        fragmentBinding.tabLayout.setupWithViewPager(fragmentBinding.viewPager);
        createTabIcons();
    }

    private void createTabIcons() {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/CALIBRI REGULAR.TTF");
        //textView.setTypeface(type);

        TextView tabZiro = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_cat, null);
        tabZiro.setText(getString(R.string.offer_wishlist));
        tabZiro.setTypeface(type);
        fragmentBinding.tabLayout.getTabAt(0).setCustomView(tabZiro);

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_cat, null);
        tabOne.setText(getString(R.string.partners_wishlist));
        tabOne.setTypeface(type);
        fragmentBinding.tabLayout.getTabAt(1).setCustomView(tabOne);
    }

    private void addTab() {
        //TabAdapter tabAdapter = new TabAdapter(getActivity().getSupportFragmentManager());//(getChildFragmentManager());
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(FavoritesTypeFragment.newInstance(), getResources().getString(R.string.offer_wishlist));
        tabAdapter.addFragment(FavoritesPartnersFragment.newInstance(), getResources().getString(R.string.partners_wishlist));
        fragmentBinding.viewPager.setAdapter(tabAdapter);
    }
}

