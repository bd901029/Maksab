package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import app.com.maksab.R;
import app.com.maksab.databinding.RowBrandDataBinding;
import app.com.maksab.engine.offer.Brand;
import app.com.maksab.listener.OnItemClickListener;

import java.util.ArrayList;

public class BrandDataAdapter extends RecyclerView.Adapter<BrandDataAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Brand> brands;
    private OnItemClickListener onItemClickListener;

    public BrandDataAdapter(Context context, ArrayList<Brand> brands, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.brands = brands;
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
        holder.rowHomeBinding.setModel(brands.get(position));
    }

    @Override
    public int getItemCount() {
        return brands != null ? brands.size() : 0;
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
     * @param brand Store object of clicked position
     */
    public void onItemClick(Brand brand) {
        onItemClickListener.onClick(brands.indexOf(brand), brand);
    }
}