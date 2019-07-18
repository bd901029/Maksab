package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.HomeDataResponse;
import app.com.maksab.databinding.RowCategoryDataBinding;
import app.com.maksab.databinding.RowSubCategoryDataBinding;
import app.com.maksab.listener.OnItemClickListener;

public class SubCategoryDataAdapter extends RecyclerView.Adapter<SubCategoryDataAdapter
        .ViewHolder> {

    private Context context;
    private ArrayList<HomeDataResponse.SubcategoryData> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public SubCategoryDataAdapter(Context context, ArrayList<HomeDataResponse.SubcategoryData>
            categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    //CategorySubItemAdapter
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowSubCategoryDataBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater
                .from(context), R.layout.row_sub_category_data, parent, false);
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
                }
            };
            holder.rowHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
            holder.rowHomeBinding.recyclerView.setAdapter(new CategorySubItemAdapter(context,
                    categoryListArrayList.get(position).getOffers(), onItemClickListener));
        }

    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowSubCategoryDataBinding rowHomeBinding;

        private ViewHolder(RowSubCategoryDataBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     * @param subcategoryData Store object of clicked position
     */
    public void onClickViewAll(HomeDataResponse.SubcategoryData subcategoryData) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(subcategoryData), subcategoryData);
    }
}