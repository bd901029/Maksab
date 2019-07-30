package app.com.maksab.view.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityHomeBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.adapter.DrawerItem;
import app.com.maksab.view.adapter.NavigationAdapter;
import app.com.maksab.view.adapter.NavigationAdapterFirst;
import app.com.maksab.view.fragment.home.*;
import app.com.maksab.view.viewmodel.GetStoreListModel;
import app.com.maksab.view.viewmodel.LogoutModel;
import com.androidquery.AQuery;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
	private ActivityHomeBinding binding;
	public static String sLanguage = Constant.LANGUAGE_ENGLISH;
	String title[] = new String[4];
	boolean isFirst = true;
	int tempM = 1;
	public boolean intFirstVisible = false;
	public ArrayList<CategoryHomeResponse.Category> categoryLists;
	public CategoryHomeResponse categoryHomeResponse;

	NearByFragment nearByFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Utility.saveDeviceHeightWidth(this);

		binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
		sLanguage = Utility.getLanguage(HomeActivity.this);
		binding.appBarLayout.setActivity(this);
		binding.appBarHeader.setActivity(this);
		addFragment(StoreFragment.newInstance(GetStoreListModel.TYPE_GROCERIES), "StoreFragment", false);
		highlightSelectedItem(1);
		if (PreferenceConnector.readBoolean(HomeActivity.this, PreferenceConnector.IS_LOGIN, false)) {
			binding.appBarHeader.llUser.setVisibility(View.VISIBLE);
			binding.appBarHeader.llGuest.setVisibility(View.GONE);
			UpdateProfilePic();
		} else {
			binding.appBarHeader.llUser.setVisibility(View.GONE);
			binding.appBarHeader.llGuest.setVisibility(View.VISIBLE);
		}

		setNavigationItemList();
		setNavigationItemList2();
		setNavigationItemList3();

		AQuery aQuery = new AQuery(binding.countryFlag);
		aQuery.id(binding.countryFlag).image(Utility.getCountryFlag(HomeActivity.this), true, true, 50, R.drawable
				.logo_small);

		binding.llCountryLanguage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, CountryLanguageActivity.class);
				intent.putExtra(Constant.FROM_ACTIVITY, Constant.FROM_ACTIVITY_HOME);
				startActivity(intent);
			}
		});

		binding.appBarHeader.llNavigation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (PreferenceConnector.readBoolean(HomeActivity.this, PreferenceConnector.IS_LOGIN, false)) {
					closeDrawer();
					binding.appBarLayout.home.setImageResource(R.drawable.home3x);
					binding.appBarLayout.location.setImageResource(R.drawable.neaer_me3x);
					binding.appBarLayout.rating.setImageResource(R.drawable.ic_star);
					binding.appBarLayout.notification.setImageResource(R.drawable.notifications3x);
					binding.appBarLayout.user.setImageResource(R.drawable.me_hover3x);
					addFragment(ProfileFragment.newInstance(GetStoreListModel.TYPE_PROFILE), "ProfileFragment",
							false);
				} else {
					Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			}
		});

		title[0] = getString(R.string.my_upcoming_purchase);
		title[1] = getString(R.string.my_past_purchase);
		title[2] = getString(R.string.points_rewards);
		title[3] = getString(R.string.subscription);
		// title[4] = getString(R.string.gift_history);
	}

	@Override
	public void onBackPressed() {
		if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
			binding.drawer.closeDrawer(GravityCompat.START);
		} else {
			if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
				finish();
			} else {
				super.onBackPressed();
			}
		}
	}

	/**
	 * Bottom item click
	 * @param position click item position
	 */
	public void onBottomNavigationItemClick(final int position) {
		if (tempM != position) {
			tempM = position;
			Utility.removeAllFragment(HomeActivity.this);
			highlightSelectedItem(position);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					switch (position) {
						case 1:
							addFragment(StoreFragment.newInstance(GetStoreListModel.TYPE_GROCERIES), "StoreFragment",
									false);
							break;
						case 2:
							nearByFragment = NearByFragment.newInstance(GetStoreListModel.TYPE_NEAR_BY);
							addFragment(nearByFragment, "NearByFragment",
									false);
							break;
						case 3:
							if (PreferenceConnector.readBoolean(HomeActivity.this, PreferenceConnector.IS_LOGIN, false)) {
								addFragment(FavoritesFragment.newInstance(GetStoreListModel.TYPE_FAVORITES),
										"FavoritesTypeFragment",
										false);
							} else {
								userGuestDialog();
							}
							break;

						case 4:
							addFragment(NotificationFragment.newInstance(GetStoreListModel.TYPE_NOTIFICATION),
									"NotificationFragment",
									false);
							break;
						case 5:
							if (PreferenceConnector.readBoolean(HomeActivity.this, PreferenceConnector.IS_LOGIN, false)) {
								addFragment(ProfileFragment.newInstance(GetStoreListModel.TYPE_PROFILE), "ProfileFragment",
										false);
							} else {
								userGuestDialog();
							}

							break;
					}
				}
			}, 300);
		}
	}

	/**
	 * Highlight selected item in bottom navigation view
	 * @param position selected position
	 */
	private void highlightSelectedItem(int position) {
		binding.appBarLayout.home.setImageResource(R.drawable.home3x);
		binding.appBarLayout.location.setImageResource(R.drawable.neaer_me3x);
		binding.appBarLayout.rating.setImageResource(R.drawable.ic_star);
		binding.appBarLayout.notification.setImageResource(R.drawable.notifications3x);
		binding.appBarLayout.user.setImageResource(R.drawable.me3x);
		switch (position) {
			case 1:
				binding.appBarLayout.home.setImageResource(R.drawable.home_hover3x);
				break;
			case 2:
				binding.appBarLayout.location.setImageResource(R.drawable.neaer_me_hover3x);
				break;
			case 3:
				binding.appBarLayout.rating.setImageResource(R.drawable.ic_star_accent);
				break;
			case 4:
				binding.appBarLayout.notification.setImageResource(R.drawable.notifications_hover3x);
				break;
			case 5:
				binding.appBarLayout.user.setImageResource(R.drawable.me_hover3x);
				break;
		}
	}

	/**
	 * Open drawer
	 */
	public void openDrawer() {
		binding.drawer.openDrawer(GravityCompat.START);
	}

	private void closeDrawer() {
		binding.drawer.closeDrawer(GravityCompat.START);
	}

	/**
	 * Add Fragment in container
	 * @param fragment Fragment to add
	 * @param tag      for add to back stack
	 */
	public void addFragment(final Fragment fragment, final String tag, boolean isRemovedAll) {
		if (isRemovedAll) {
			Utility.removeAllFragment(HomeActivity.this);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					addFragment(fragment, tag, false);
				}
			}, 300);
		} else {
			Utility.addFragment(this, fragment, tag, R.id.container);
		}
	}

	/**
	 * Set navigation item list
	 */
	private void setNavigationItemList() {
		ArrayList<DrawerItem> drawerItems = createNavigationItemList();
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				LinearLayout mLinearLayout = (LinearLayout) obj;
				switch (position) {
					case Constant.HOME:
						closeDrawer();
						binding.appBarLayout.home.setImageResource(R.drawable.home_hover3x);
						binding.appBarLayout.location.setImageResource(R.drawable.neaer_me3x);
						binding.appBarLayout.rating.setImageResource(R.drawable.ic_star);
						binding.appBarLayout.notification.setImageResource(R.drawable.notifications3x);
						binding.appBarLayout.user.setImageResource(R.drawable.me3x);
						addFragment(StoreFragment.newInstance(GetStoreListModel.TYPE_GROCERIES), "StoreFragment",
								true);
						break;
					case Constant.PARCHASES:
						if (mLinearLayout != null && isFirst) {
							addLayout(mLinearLayout);
							isFirst = false;
						} else {
							mLinearLayout.removeAllViews();
							isFirst = true;
						}
						break;
					case Constant.FAVORITES:
						closeDrawer();
						binding.appBarLayout.home.setImageResource(R.drawable.home3x);
						binding.appBarLayout.location.setImageResource(R.drawable.neaer_me3x);
						binding.appBarLayout.rating.setImageResource(R.drawable.ic_star_accent);
						binding.appBarLayout.notification.setImageResource(R.drawable.notifications3x);
						binding.appBarLayout.user.setImageResource(R.drawable.me3x);
						addFragment(FavoritesFragment.newInstance(GetStoreListModel.TYPE_FAVORITES), "FavoritesTypeFragment",
								false);
						break;

					case Constant.GIFTS:
						closeDrawer();
						Intent intent = new Intent(HomeActivity.this, MyGiftsActivity.class);
						startActivity(intent);
						break;

				}
			}
		};

		NavigationAdapterFirst navigationAdapter = new NavigationAdapterFirst(HomeActivity.this, drawerItems,
				onItemClickListener);
		binding.recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
		binding.recyclerView.setAdapter(navigationAdapter);
	}

	private void addLayout(LinearLayout mLinearLayout) {
		mLinearLayout.removeAllViews();
		for (int i = 0; i < title.length; i++) {
			View layout2 = LayoutInflater.from(this).inflate(R.layout.copy_row_navigation_item, mLinearLayout, false);
			TextView title_copy = (TextView) layout2.findViewById(R.id.title_copy);
			LinearLayout copy_ll = layout2.findViewById(R.id.copy_ll);
			title_copy.setText(title[i]);
			final int finalI = i;
			copy_ll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					closeDrawer();
					if (title[finalI].equals(getString(R.string.my_upcoming_purchase))) {
						Intent intent = new Intent(HomeActivity.this, UpcomingPurchaseActivity.class);
						intent.putExtra("position", finalI);
						startActivity(intent);
					} else if (title[finalI].equals(getString(R.string.my_past_purchase))) {
						Intent intent = new Intent(HomeActivity.this, PastPurchaseActivity.class);
						intent.putExtra("position", finalI);
						startActivity(intent);
					} else if (title[finalI].equals(getString(R.string.points_rewards))) {
						Intent intent = new Intent(HomeActivity.this, PointsRewardActivity.class);
						startActivity(intent);
					} else if (title[finalI].equals(getString(R.string.subscription))) {
						Intent intent = new Intent(HomeActivity.this, SubscriptionActivity.class);
						startActivity(intent);
					} /*else if (title[finalI].equals(getString(R.string.gift_history))) {
                        Intent intent = new Intent(HomeActivity.this, GiftHistoryActivity.class);
                        startActivity(intent);
                    }*/else {

					}
				}
			});
			mLinearLayout.addView(layout2);
		}
	}

	/**
	 * Set navigation item list
	 */
	private void setNavigationItemList2() {
		ArrayList<DrawerItem> drawerItems = createNavigationItemList2();
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				closeDrawer();
				switch (position) {
					case Constant.MEMBERSHIP:
						startActivity(new Intent(HomeActivity.this, PackagesActivity.class));
						break;
					case Constant.INVITE:
						try {
							Intent sendIntent = new Intent();
							sendIntent.setAction(Intent.ACTION_SEND);
							sendIntent.putExtra(Intent.EXTRA_TEXT, "http:\\/\\/www.maksabapp" +
									".com\\/develop\\/?ref=42771518078426132");
							sendIntent.setType("text/plain");
							startActivity(sendIntent);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case Constant.PARTNER:
						startActivity(new Intent(HomeActivity.this, BecomePartnerActivity.class));

						break;
					case Constant.CAREERS:
						startActivity(new Intent(HomeActivity.this, CareerActivity.class));
						break;
					case Constant.CONTACT_US:
						startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
						break;
				}
			}
		};

		NavigationAdapter navigationAdapter = new NavigationAdapter(HomeActivity.this, drawerItems, onItemClickListener);
		binding.recyclerView2.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
		binding.recyclerView2.setAdapter(navigationAdapter);
	}

	/**
	 * Set navigation item list
	 */
	private void setNavigationItemList3() {
		ArrayList<DrawerItem> drawerItems = createNavigationItemList3();
		OnItemClickListener onItemClickListener = new OnItemClickListener() {
			@Override
			public void onClick(int position, Object obj) {
				closeDrawer();
				switch (position) {
					case Constant.FAQ:
						startActivity(new Intent(HomeActivity.this, FaqActivity.class));
						break;
					case Constant.TC:
						startActivity(new Intent(HomeActivity.this, TCActivity.class));
						break;
					case Constant.PP:
						startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
						break;
					case Constant.LOGOUT:
						showLogoutPopup();
						break;
				}
			}
		};
		NavigationAdapter navigationAdapter = new NavigationAdapter(HomeActivity.this, drawerItems, onItemClickListener);
		binding.recyclerView3.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
		binding.recyclerView3.setAdapter(navigationAdapter);
	}

	/**
	 * Create list for navigation
	 * @return DrawerItem ArrayList
	 */
	private ArrayList<DrawerItem> createNavigationItemList() {
		ArrayList<DrawerItem> drawerItems = new ArrayList<>();
		DrawerItem drawerItemHome = new DrawerItem(getString(R.string.home));
		DrawerItem drawerItemParcheses = new DrawerItem(getString(R.string.purchases));
		DrawerItem drawerItemFavorites = new DrawerItem(getString(R.string.favorites));
		DrawerItem drawerItemGifts = new DrawerItem(getString(R.string.my_gifts));

		drawerItems.add(drawerItemHome);
		if (PreferenceConnector.readBoolean(HomeActivity.this, PreferenceConnector.IS_LOGIN, false)) {
			drawerItems.add(drawerItemParcheses);
			drawerItems.add(drawerItemFavorites);
			drawerItems.add(drawerItemGifts);

		}
		return drawerItems;
	}

	/**
	 * Create list for navigation
	 * @return DrawerItem ArrayList
	 */
	private ArrayList<DrawerItem> createNavigationItemList2() {
		ArrayList<DrawerItem> drawerItems = new ArrayList<>();
		DrawerItem drawerItemMembership = new DrawerItem(getString(R.string.membership));
		DrawerItem drawerItemInvite = new DrawerItem(getString(R.string.invite));
		DrawerItem drawerItemPartner = new DrawerItem(getString(R.string.become_partner));
		DrawerItem drawerItemCareers = new DrawerItem(getString(R.string.careers));
		DrawerItem drawerItemContactUs = new DrawerItem(getString(R.string.contact_us));

		drawerItems.add(drawerItemMembership);
		drawerItems.add(drawerItemInvite);
		drawerItems.add(drawerItemPartner);
		drawerItems.add(drawerItemCareers);
		drawerItems.add(drawerItemContactUs);
		return drawerItems;
	}

	/**
	 * Create list for navigation
	 * @return DrawerItem ArrayList
	 */
	private ArrayList<DrawerItem> createNavigationItemList3() {
		ArrayList<DrawerItem> drawerItems = new ArrayList<>();
		DrawerItem drawerItemFAQ = new DrawerItem(getString(R.string.faq));
		DrawerItem drawerItemTC = new DrawerItem(getString(R.string.terms_condition));
		DrawerItem drawerItemPP = new DrawerItem(getString(R.string.privacy_policy));
		DrawerItem drawerItemLogout = new DrawerItem(getString(R.string.logout));

		drawerItems.add(drawerItemFAQ);
		drawerItems.add(drawerItemTC);
		drawerItems.add(drawerItemPP);
        /*if (PreferenceConnector.readBoolean(HomeActivity.this, PreferenceConnector.IS_LOGIN, false))
            drawerItems.add(drawerItemLogout);*/
		return drawerItems;
	}

	/**Show Guest Login Alert */
	private void userGuestDialog() {
		Utility.setDialog(HomeActivity.this, getString(R.string.alert), getString(R.string.guest_login_alert),
				getString(R.string.no), getString(R.string.yes), new DialogListener() {
					@Override
					public void onNegative(DialogInterface dialog) {
						dialog.dismiss();
					}

					@Override
					public void onPositive(DialogInterface dialog) {
						dialog.dismiss();
						Intent intent = new Intent(HomeActivity.this, IntroActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(intent);
						finish();
					}
				});
	}

	/**
	 * Show logout confirmation diagog
	 */
	private void showLogoutPopup() {
		Utility.setDialog(HomeActivity.this, getString(R.string.alert), getString(R.string.logout_message),
				getString(R.string.no), getString(R.string.yes), new DialogListener() {
					@Override
					public void onNegative(DialogInterface dialog) {
						dialog.dismiss();
					}

					@Override
					public void onPositive(DialogInterface dialog) {
						dialog.dismiss();
						getLogout();
					}
				});
	}

	/**
	 * Logout user and open splash screen
	 */
	private void doLogout() {
		PreferenceConnector.writeBoolean(HomeActivity.this, PreferenceConnector.IS_LOGIN, false);
		Intent intent = new Intent(HomeActivity.this, IntroActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		//finish();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		if (requestCode == Utility.REQUEST_CODE_LOCATION) {
			for (Integer grantResult : grantResults) {
				if (grantResult != PackageManager.PERMISSION_GRANTED) {
					if (nearByFragment != null) nearByFragment.showLocationAlert();
					return;
				}
			}

			if (nearByFragment != null) nearByFragment.setMyLocationEnabled();

			return;
		}

		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	public void UpdateProfilePic() {
		binding.appBarHeader.userName.setText(getString(R.string.welcome) + ", " + Utility.getUserName(HomeActivity.this));
		AQuery aQuery = new AQuery(binding.appBarHeader.ivUserAvatar);
		aQuery.id(binding.appBarHeader.ivUserAvatar).image(Utility.getUserPic(HomeActivity.this), true, true, 50, R.drawable
				.logo_small);
	}

	public void onFirstVisible(boolean target) {
		intFirstVisible = target;
	}

	private void getLogout() {
		ProgressDialog.getInstance().showProgressDialog(HomeActivity.this);
		LogoutModel logoutModel = new LogoutModel();
		logoutModel.setUserId(Utility.getUserId(HomeActivity.this));
		logoutModel.setLanguage(Utility.getLanguage(HomeActivity.this));
		logoutModel.setDeviceType("android");
		Api api = APIClient.getClient().create(Api.class);
		final Call<SuccessfulResponse> responseCall = api.logout(logoutModel);
		responseCall.enqueue(new Callback<SuccessfulResponse>() {
			@Override
			public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse> response) {
				ProgressDialog.getInstance().dismissDialog();
				SuccessfulResponse myResponse = response.body();
				if (myResponse != null) {
					if (myResponse.getResponseCode() != null && myResponse.getResponseCode().equals(Api.SUCCESS)) {
						doLogout();
					} else {
						Utility.showToast(HomeActivity.this, myResponse.getMessage() + "");
					}
				}
			}

			@Override
			public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
				ProgressDialog.getInstance().dismissDialog();
				Utility.showToast(HomeActivity.this, t+"");
			}
		});
	}

}
