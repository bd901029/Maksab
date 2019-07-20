package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.com.maksab.R;
import app.com.maksab.api.dao.CountryCityListResponse;
import app.com.maksab.databinding.RowArDialogListBinding;
import app.com.maksab.listener.OnItemClickListener;

import java.util.ArrayList;

public class DialogArListAdapter extends RecyclerView.Adapter<DialogArListAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<CountryCityListResponse.ArCountry> resultLists;
    private OnItemClickListener onItemClickListener;

    public DialogArListAdapter(Activity context, ArrayList<CountryCityListResponse.ArCountry> resultLists,
                               OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowArDialogListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_ar_dialog_list,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        holder.binding.countryStatus.setVisibility(View.GONE);
        if (resultLists.get(position).isStatusCountry()) {
            holder.binding.countryStatus.setVisibility(View.VISIBLE);
        }else
            holder.binding.countryStatus.setVisibility(View.GONE);

        if (resultLists.get(position).getCitys() != null && resultLists.get(position).getCitys().size() != 0) {
            OnItemClickListener onItemClickListener2 = new OnItemClickListener() {
                @Override
                public void onClick(int position2, Object obj) {
                    CountryCityListResponse.CityList cityList = (CountryCityListResponse.CityList) obj;
                    cityList.setCountryId(resultLists.get(position).getCountryId());
                    cityList.setCountryName(resultLists.get(position).getCountryName());
                    cityList.setCountryCode(resultLists.get(position).getCountryCode());
                    cityList.setCountryFlag(resultLists.get(position).getCountryFlag());
                    onItemClickListener.onClick(position2, obj);
                    cityList.setStatus(true);
                    resultLists.get(position).setStatusCountry(true);
                    notifyDataSetChanged();
                }
            };
            holder.binding.recyclerView.setLayoutManager(new GridLayoutManager
                    (context, 1));
//            holder.binding.recyclerView.setAdapter(new CityListAdapter
//                    (context, resultLists.get(position).getCitys(), onItemClickListener2));
        }
    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowArDialogListBinding binding;
        public ViewHolder(RowArDialogListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
