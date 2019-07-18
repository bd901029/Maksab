package app.com.maksab.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.HomeDataResponse;
import app.com.maksab.databinding.RowCategoryDataBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.view.activity.OfferDetailsActivity;

public class CategoryDataAdapter extends RecyclerView.Adapter<CategoryDataAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<HomeDataResponse.CategoryData> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public CategoryDataAdapter(Activity context, ArrayList<HomeDataResponse.CategoryData> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        //this.categoryListArrayList.addAll(categoryListArrayList);
        this.onItemClickListener = onItemClickListener;
    }
//CategorySubItemAdapter
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowCategoryDataBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_category_data, parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rowHomeBinding.setAdapter(this);
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));
        if (categoryListArrayList.get(position).getOffers() != null && categoryListArrayList.get(position).getOffers().size() != 0) {
            OnItemClickListener onItemClickListener = new OnItemClickListener() {
                @Override
                public void onClick(int position, Object obj) {
                    HomeDataResponse.Offers offerList = (HomeDataResponse.Offers) obj;
                    Intent intent = new Intent(context, OfferDetailsActivity.class);
                    intent.putExtra(Constant.OFFER_ID,offerList.getOfferId());
                    context.startActivityForResult(intent, 1);
                }
            };
            holder.rowHomeBinding.recyclerView.setAdapter(new CategorySubItemAdapter(context, categoryListArrayList.get(position).getOffers(),
                    onItemClickListener));
        }

    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowCategoryDataBinding rowHomeBinding;

        private ViewHolder(RowCategoryDataBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
            rowHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    /**
     * On Item click listener method
     * @param categoryData Store object of clicked position
     */
    public void onClickViewAll(HomeDataResponse.CategoryData categoryData) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(categoryData), categoryData);
    }
}