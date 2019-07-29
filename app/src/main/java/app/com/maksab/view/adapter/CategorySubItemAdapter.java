package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AddFavoritesResponse;
import app.com.maksab.databinding.RowCategorySubItemBinding;
import app.com.maksab.engine.offer.Offer;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;

public class CategorySubItemAdapter extends RecyclerView.Adapter<CategorySubItemAdapter.ViewHolder> {

	private Context context;
	private ArrayList<Offer> offers;
	private OnItemClickListener onItemClickListener;

	public CategorySubItemAdapter(Context context, ArrayList<Offer> offers, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.offers = offers;
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowCategorySubItemBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_category_sub_item, parent, false);
		return new ViewHolder(rowCategoryHomeBinding);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		holder.rowHomeBinding.setAdapter(this);
		holder.rowHomeBinding.setModel(offers.get(position));

		Picasso.with(context).load(offers.get(position).image).into(holder.rowHomeBinding.image);

		if (offers.get(position).reaming.equalsIgnoreCase("0")|| offers.get(position)
				.reaming.equalsIgnoreCase("Unlimited")){
			holder.rowHomeBinding.remaining.setVisibility(View.INVISIBLE);
		}else {
			holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
		}

		if (offers.get(position).bought.equalsIgnoreCase("0")|| offers.get(position)
				.bought.equalsIgnoreCase("Unlimited")){
			holder.rowHomeBinding.bought.setVisibility(View.INVISIBLE);
		}else {
			holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
		}

		holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


		if (offers.get(position).favStatus.equalsIgnoreCase("1"))
			holder.rowHomeBinding.favImage.setBackgroundResource(R.drawable.favorites_hover3x);
		else
			holder.rowHomeBinding.favImage.setBackgroundResource(R.drawable.favorites3x);

		holder.rowHomeBinding.favImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (PreferenceConnector.readBoolean(context, PreferenceConnector.IS_LOGIN, false)) {
					ProgressDialog.getInstance().showProgressDialog(context);
					UserOfferIdModel userOfferIdModel = new UserOfferIdModel();
					userOfferIdModel.setUserId(Utility.getUserId(context));
					userOfferIdModel.setLanguage(Utility.getLanguage(context));
					userOfferIdModel.setOfferId(offers.get(position).id);

					Api api = APIClient.getClient().create(Api.class);
					final Call<AddFavoritesResponse> responseCall = api.addOfferInWishlist(userOfferIdModel);
					responseCall.enqueue(new Callback<AddFavoritesResponse>() {
						@Override
						public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
								response) {
							ProgressDialog.getInstance().dismissDialog();
							if (response.body().getFavStatus().equalsIgnoreCase("1"))
								holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable
										.favorites_hover3x));
							else
								holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable
										.favorites3x));

							holder.rowHomeBinding.favImage.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									if (PreferenceConnector.readBoolean(context, PreferenceConnector.IS_LOGIN, false)) {
										ProgressDialog.getInstance().showProgressDialog(context);
										UserOfferIdModel userOfferIdModel = new UserOfferIdModel();
										userOfferIdModel.setUserId(Utility.getUserId(context));
										userOfferIdModel.setLanguage(Utility.getLanguage(context));
										userOfferIdModel.setOfferId(offers.get(position).id);

										Api api = APIClient.getClient().create(Api.class);
										final Call<AddFavoritesResponse> responseCall = api.addOfferInWishlist(userOfferIdModel);
										responseCall.enqueue(new Callback<AddFavoritesResponse>() {
											@Override
											public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
													response) {
												ProgressDialog.getInstance().dismissDialog();
												if (response.body().getFavStatus().equalsIgnoreCase("1"))
													holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable.favorites_hover3x));
												else
													holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable.favorites3x));
												// notifyItemChanged(position);
											}

											@Override
											public void onFailure(Call<AddFavoritesResponse> call, Throwable t) {
												ProgressDialog.getInstance().dismissDialog();
												// Utility.showToast(context, t + "");
												Log.e("", "onFailure: " + t.getLocalizedMessage());
											}
										});
									} else {
										// userGuestDialog();
									}

								}
							});
						}

						@Override
						public void onFailure(Call<AddFavoritesResponse> call, Throwable t) {
							ProgressDialog.getInstance().dismissDialog();
							// Utility.showToast(context, t + "");
							Log.e("", "onFailure: " + t.getLocalizedMessage());
						}
					});
				} else {
					// userGuestDialog();
				}

			}
		});

	}

	@Override
	public int getItemCount() {
		return offers != null ? offers.size() : 0;
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private RowCategorySubItemBinding rowHomeBinding;

		private ViewHolder(RowCategorySubItemBinding rowHomeBinding) {
			super(rowHomeBinding.getRoot());
			this.rowHomeBinding = rowHomeBinding;
		}
	}

	/**
	 * On Item click listener method
	 *
	 * @param offer Store object of clicked position
	 */
	public void onItemClick(Offer offer) {
		onItemClickListener.onClick(this.offers.indexOf(offer), offer);
	}
}