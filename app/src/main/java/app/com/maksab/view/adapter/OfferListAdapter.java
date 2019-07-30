package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.OfferListResponse;
import app.com.maksab.databinding.RowOfferListBinding;
import app.com.maksab.engine.offer.Offer;
import app.com.maksab.listener.OnItemClickListener;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.ViewHolder> {

	private Context context;
	private ArrayList<Offer> offers;
	private OnItemClickListener onItemClickListener;

	public OfferListAdapter(Context context, ArrayList<Offer> offers, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.offers = offers;
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowOfferListBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater
				.from(context), R.layout.row_offer_list, parent, false);
		return new ViewHolder(rowCategoryHomeBinding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.rowHomeBinding.setAdapter(this);
		holder.rowHomeBinding.setModel(offers.get(position));
		if (offers.get(position).reaming.equalsIgnoreCase("0")
				|| offers.get(position).reaming.equalsIgnoreCase("Unlimited")) {
			holder.rowHomeBinding.remaining.setVisibility(View.GONE);
		}else {
			holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
		}

		if (offers.get(position).bought.equalsIgnoreCase("0")
				|| offers.get(position).bought.equalsIgnoreCase("Unlimited")) {
			holder.rowHomeBinding.bought.setVisibility(View.GONE);
		} else {
			holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
		}

		holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

	}

	@Override
	public int getItemCount() {
		return offers != null ? offers.size() : 0;
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private RowOfferListBinding rowHomeBinding;

		private ViewHolder(RowOfferListBinding rowHomeBinding) {
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
		onItemClickListener.onClick(offers.indexOf(offer), offer);
	}
}