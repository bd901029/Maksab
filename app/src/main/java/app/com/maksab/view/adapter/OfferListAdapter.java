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
import app.com.maksab.listener.OnItemClickListener;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OfferListResponse.OfferList> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public OfferListAdapter(Context context, ArrayList<OfferListResponse.OfferList> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
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
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));
        if (categoryListArrayList.get(position).getReaming().equalsIgnoreCase("0")||categoryListArrayList.get(position)
                .getReaming().equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.remaining.setVisibility(View.GONE);
        }else {
            holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
        }

        if (categoryListArrayList.get(position).getBought().equalsIgnoreCase("0")||categoryListArrayList.get(position)
                .getBought().equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.bought.setVisibility(View.GONE);
        }else {
            holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
        }

        holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
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
     * @param offerList Store object of clicked position
     */
    public void onItemClick(OfferListResponse.OfferList offerList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(offerList), offerList);
    }
}