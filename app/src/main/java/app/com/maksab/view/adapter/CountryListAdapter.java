package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.com.maksab.R;
import app.com.maksab.databinding.RowDialogListBinding;
import app.com.maksab.engine.country.City;
import app.com.maksab.engine.country.Country;
import app.com.maksab.listener.OnItemClickListener;

import java.util.ArrayList;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {

	private Activity context;
	private ArrayList<Country> countries;
	private OnItemClickListener onItemClickListener;

	public CountryListAdapter(Activity context, ArrayList<Country> countries, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.countries = countries;
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowDialogListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_dialog_list, parent, false);
		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		holder.binder.setAdapter(this);
		holder.binder.setModel(countries.get(position));
		// holder.binder.name.setText(countries.get(position).getName());
		holder.binder.countryStatus.setVisibility(View.GONE);

		if (countries.get(position).isSelected) {
			holder.binder.countryStatus.setVisibility(View.VISIBLE);
		} else {
			holder.binder.countryStatus.setVisibility(View.GONE);
		}

		if (countries.get(position).cities != null && countries.get(position).cities.size() != 0) {
			OnItemClickListener onItemClickListener2 = new OnItemClickListener() {
				@Override
				public void onClick(int position2, Object obj) {
					City city = (City) obj;

					onItemClickListener.onClick(position2, obj);
					city.setSelected(true);
					countries.get(position).setSelected(true);
					notifyDataSetChanged();
				}
			};
			holder.binder.recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
			holder.binder.recyclerView.setAdapter(new CityListAdapter(context, countries.get(position).cities, onItemClickListener2));
		}
	}

	@Override
	public int getItemCount() {
		return countries.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private RowDialogListBinding binder;

		public ViewHolder(RowDialogListBinding binding) {
			super(binding.getRoot());
			this.binder = binding;
		}
	}

}
