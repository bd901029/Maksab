package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.OfferListResponse;
import app.com.maksab.api.dao.SearchListResponse;
import app.com.maksab.databinding.RowOfferListBinding;
import app.com.maksab.databinding.RowSearchOfferListBinding;
import app.com.maksab.listener.OnItemClickListener;

public class SearchOfferListAdapter extends RecyclerView.Adapter<SearchOfferListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchListResponse.OfferData> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public SearchOfferListAdapter(Context context, ArrayList<SearchListResponse.OfferData> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowSearchOfferListBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater
                .from(context), R.layout.row_search_offer_list, parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rowHomeBinding.setAdapter(this);
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));
        holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowSearchOfferListBinding rowHomeBinding;

        private ViewHolder(RowSearchOfferListBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     * @param offerList Store object of clicked position
     */
    public void onItemClick(SearchListResponse.OfferData offerList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(offerList), offerList);
    }
}