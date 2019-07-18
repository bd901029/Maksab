package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.OfferDetailsResponse;
import app.com.maksab.databinding.RowReviewBinding;
import app.com.maksab.databinding.RowVenderLocationBinding;
import app.com.maksab.listener.OnItemClickListener;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OfferDetailsResponse.ReviewsList> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public ReviewAdapter(Context context, ArrayList<OfferDetailsResponse.ReviewsList> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowReviewBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .row_review, parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rowHomeBinding.setAdapter(this);
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowReviewBinding rowHomeBinding;

        private ViewHolder(RowReviewBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     *
     * @param categoryList Store object of clicked position
     */
    public void onItemClick(OfferDetailsResponse.ReviewsList categoryList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(categoryList), categoryList);
    }
}