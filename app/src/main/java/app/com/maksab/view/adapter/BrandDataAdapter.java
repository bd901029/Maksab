package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.HomeDataResponse;
import app.com.maksab.databinding.RowBrandDataBinding;
import app.com.maksab.listener.OnItemClickListener;

public class BrandDataAdapter extends RecyclerView.Adapter<BrandDataAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HomeDataResponse.BrandData> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public BrandDataAdapter(Context context, ArrayList<HomeDataResponse.BrandData> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowBrandDataBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_brand_data, parent, false);
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

        private RowBrandDataBinding rowHomeBinding;

        private ViewHolder(RowBrandDataBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     *
     * @param brandData Store object of clicked position
     */
    public void onItemClick(HomeDataResponse.BrandData brandData) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(brandData), brandData);
    }
}