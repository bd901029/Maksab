package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.com.maksab.R;
import app.com.maksab.databinding.RowDialogCityBinding;
import app.com.maksab.engine.country.City;
import app.com.maksab.listener.OnItemClickListener;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

	private Activity context;
	private ArrayList<City> cities;
	private OnItemClickListener onItemClickListener;

	public CityListAdapter(Activity context, ArrayList<City> cities, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.cities = cities;
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowDialogCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_dialog_city, parent, false);
		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.binding.setAdapter(this);
		holder.binding.setModel(cities.get(position));

		if (cities.get(position).isSelected()) {
			holder.binding.cityStatus.setVisibility(View.VISIBLE);
			Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/CALIBRIB BOLD.TTF");
			holder.binding.name.setTypeface(face);
		} else {
			holder.binding.cityStatus.setVisibility(View.GONE);
			holder.binding.name.setTypeface(null, Typeface.NORMAL);
		}
	}

	@Override
	public int getItemCount() {
		return cities.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private RowDialogCityBinding binding;

		public ViewHolder(RowDialogCityBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
	}

	public void onItemClick(final City city) {
        /*if (resultList.isStatus()) {
            cities.get(cities.indexOf(resultList)).setStatus(false);
        } else {
            cities.get(cities.indexOf(resultList)).setStatus(true);
        }
        cities.set(cities.indexOf(resultList), resultList);
        notifyItemChanged(cities.indexOf(resultList));*/
		onItemClickListener.onClick(cities.indexOf(city), city);
	}
}
