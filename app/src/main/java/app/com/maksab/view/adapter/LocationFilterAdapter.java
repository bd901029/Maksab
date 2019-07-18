package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.OfferFilterResponse;
import app.com.maksab.databinding.RowCategoriesFilterBinding;
import app.com.maksab.databinding.RowLocationFilterBinding;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;

public class LocationFilterAdapter extends RecyclerView.Adapter<LocationFilterAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<OfferFilterResponse.LocationList> resultLists;
    private OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener;

    public LocationFilterAdapter(Activity context, ArrayList<OfferFilterResponse.LocationList> resultLists, OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickCheckUnCheckListener = onItemClickCheckUnCheckListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowLocationFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context)
                , R.layout.row_location_filter,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));

        if (resultLists.get(position).isStatus()) {
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

        private RowLocationFilterBinding binding;

        public ViewHolder(RowLocationFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onClickItem(final OfferFilterResponse.LocationList locationList) {
        if (resultLists.get(resultLists.indexOf(locationList)).isStatus()) {
            locationList.setStatus(false);
            onItemClickCheckUnCheckListener.onRemove(resultLists.indexOf(locationList),locationList);
        }
        else {
            locationList.setStatus(true);
            onItemClickCheckUnCheckListener.onAdd(resultLists.indexOf(locationList),locationList);
        }
        resultLists.set(resultLists.indexOf(locationList), locationList);
        notifyItemChanged(resultLists.indexOf(locationList));
    }
}
