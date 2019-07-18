package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.databinding.RowNearbyFilterBinding;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;

public class NearbyFilterAdapter extends RecyclerView.Adapter<NearbyFilterAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<CategoryHomeResponse.CategoryList> resultLists;
    private OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener;

    public NearbyFilterAdapter(Activity context, ArrayList<CategoryHomeResponse.CategoryList> resultLists, OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickCheckUnCheckListener = onItemClickCheckUnCheckListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowNearbyFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context)
                , R.layout.row_nearby_filter,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));

        if (resultLists.get(position).getCategoryStatus().equalsIgnoreCase("1")) {
            //holder.binding.imgCheckbox.setImageResource(R.mipmap.checkled);
            holder.binding.imgCheckbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check));
        }
        else {
            holder.binding.imgCheckbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.checkbox));
        }

        }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowNearbyFilterBinding binding;

        public ViewHolder(RowNearbyFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onClickItem(final CategoryHomeResponse.CategoryList serviceTypesList) {
        if (resultLists.get(resultLists.indexOf(serviceTypesList)).getCategoryStatus().equalsIgnoreCase("1")) {
            serviceTypesList.setCategoryStatus("0");
            onItemClickCheckUnCheckListener.onRemove(resultLists.indexOf(serviceTypesList),serviceTypesList);
        }
        else {
            serviceTypesList.setCategoryStatus("1");
            onItemClickCheckUnCheckListener.onAdd(resultLists.indexOf(serviceTypesList),serviceTypesList);
        }
        resultLists.set(resultLists.indexOf(serviceTypesList), serviceTypesList);
        notifyItemChanged(resultLists.indexOf(serviceTypesList));
    }
}
