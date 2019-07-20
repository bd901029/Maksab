package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.databinding.RowCategoryHomeBinding;
import app.com.maksab.listener.OnItemClickListener;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoryHomeResponse.Category> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public CategoryHomeAdapter(Context context, ArrayList<CategoryHomeResponse.Category> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowCategoryHomeBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_category_home, parent, false);
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

        private RowCategoryHomeBinding rowHomeBinding;

        private ViewHolder(RowCategoryHomeBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     * @param categoryList Store object of clicked position
     */
    public void onItemClick(CategoryHomeResponse.Category categoryList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(categoryList), categoryList);
    }
}