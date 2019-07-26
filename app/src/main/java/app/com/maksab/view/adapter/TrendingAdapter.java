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
import app.com.maksab.databinding.RowTrendingBinding;
import app.com.maksab.engine.offer.Offer;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {

	private Context context;
	private ArrayList<Offer> trends;
	private OnItemClickListener onItemClickListener;

	public TrendingAdapter(Context context, ArrayList<Offer> trends, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.trends = trends;
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowTrendingBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_trending, parent, false);
		return new ViewHolder(rowCategoryHomeBinding);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder,final int position) {
		holder.rowHomeBinding.setAdapter(this);
		trends.get(position).discountRate = trends.get(position).discountRate + " " + context.getResources().getString(R.string.off);
		holder.rowHomeBinding.setModel(trends.get(position));

		if (trends.get(position).reaming.equalsIgnoreCase("0") || trends.get(position).reaming.equalsIgnoreCase("Unlimited")){
			holder.rowHomeBinding.remaining.setVisibility(View.INVISIBLE);
		}else {
			holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
		}

		if (trends.get(position).bought.equalsIgnoreCase("0")||trends.get(position)
				.bought.equalsIgnoreCase("Unlimited")){
			holder.rowHomeBinding.bought.setVisibility(View.INVISIBLE);
		}else {
			holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
		}

		holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


		if (trends.get(position).favStatus.equals("1"))
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
					userOfferIdModel.setOfferId(trends.get(position).id);

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
	public int getItemCount() {
		return trends != null ? trends.size() : 0;
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private RowTrendingBinding rowHomeBinding;

		private ViewHolder(RowTrendingBinding rowHomeBinding) {
			super(rowHomeBinding.getRoot());
			this.rowHomeBinding = rowHomeBinding;
		}
	}

	/**
	 * On Item click listener method
	 *
	 * @param trend Store object of clicked position
	 */
	public void onItemClick(Offer trend) {
		onItemClickListener.onClick(trends.indexOf(trend), trend);
	}
}