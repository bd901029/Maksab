package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.NotificationFilterResponse;
import app.com.maksab.api.dao.OfferFilterResponse;
import app.com.maksab.databinding.RowCategoriesFilterBinding;
import app.com.maksab.databinding.RowNotificationFilterBinding;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;
import app.com.maksab.listener.OnItemClickListener;

public class CategoriesFilterAdapter extends RecyclerView.Adapter<CategoriesFilterAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<OfferFilterResponse.SubCategoryList> resultLists;
    private OnItemClickListener onItemClickListener;

    public CategoriesFilterAdapter(Activity context, ArrayList<OfferFilterResponse.SubCategoryList> resultLists,
                                   OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowCategoriesFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context)
                , R.layout.row_categories_filter,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        if (resultLists.get(position).isStatus()) {
            holder.binding.imgCheckbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check));
        } else {
            holder.binding.imgCheckbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.checkbox));
        }

    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowCategoriesFilterBinding binding;

        public ViewHolder(RowCategoriesFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onClickItem(final OfferFilterResponse.SubCategoryList subCategoryList) {
        if (resultLists.get(resultLists.indexOf(subCategoryList)).isStatus()) {
            subCategoryList.setStatus(false);
            onItemClickListener.onClick(resultLists.indexOf(subCategoryList), subCategoryList);
        } else {
            subCategoryList.setStatus(true);
            onItemClickListener.onClick(resultLists.indexOf(subCategoryList), subCategoryList);
        }
        resultLists.set(resultLists.indexOf(subCategoryList), subCategoryList);
        notifyItemChanged(resultLists.indexOf(subCategoryList));
    }
}
