package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.com.maksab.R;
import app.com.maksab.api.dao.CountryCityListResponse;
import app.com.maksab.databinding.RowDialogCityBinding;
import app.com.maksab.listener.OnItemClickListener;

import java.util.ArrayList;

public class DialogCityAdapter extends RecyclerView.Adapter<DialogCityAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<CountryCityListResponse.CityList> resultLists;
    private OnItemClickListener onItemClickListener;

    public DialogCityAdapter(Activity context, ArrayList<CountryCityListResponse.CityList> resultLists,
                             OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowDialogCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_dialog_city,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));

        if (resultLists.get(position).isStatus()) {
            holder.binding.cityStatus.setVisibility(View.VISIBLE);
            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    "fonts/CALIBRIB BOLD.TTF");
            holder.binding.name.setTypeface(face);
        }else {
            holder.binding.cityStatus.setVisibility(View.GONE);
            holder.binding.name.setTypeface(null, Typeface.NORMAL);
        }

    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowDialogCityBinding binding;

        public ViewHolder(RowDialogCityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onItemClick(final CountryCityListResponse.CityList resultList) {
        /*if (resultList.isStatus()) {
            resultLists.get(resultLists.indexOf(resultList)).setStatus(false);
        } else {
            resultLists.get(resultLists.indexOf(resultList)).setStatus(true);
        }
        resultLists.set(resultLists.indexOf(resultList), resultList);
        notifyItemChanged(resultLists.indexOf(resultList));*/
        onItemClickListener.onClick(resultLists.indexOf(resultList), resultList);
    }
}
