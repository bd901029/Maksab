package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.databinding.RowNearbyFilterBinding;
import app.com.maksab.engine.category.Category;
import app.com.maksab.listener.OnItemClickCheckUnCheckListener;

public class NearbyFilterAdapter extends RecyclerView.Adapter<NearbyFilterAdapter.ViewHolder> {

	private Activity context;
	private ArrayList<Category> categories;
	private OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener;

	public NearbyFilterAdapter(Activity context, ArrayList<Category> resultLists, OnItemClickCheckUnCheckListener onItemClickCheckUnCheckListener) {
		this.context = context;
		this.categories = resultLists;
		this.onItemClickCheckUnCheckListener = onItemClickCheckUnCheckListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowNearbyFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_nearby_filter, parent, false);
		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.binding.setAdapter(this);
		holder.binding.setModel(categories.get(position));

		if (categories.get(position).status.equalsIgnoreCase("1")) {
			//holder.binding.imgCheckbox.setImageResource(R.mipmap.checkled);
			holder.binding.imgCheckbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check));
		}
		else {
			holder.binding.imgCheckbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.checkbox));
		}

	}

	@Override
	public int getItemCount() {
		return categories.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private RowNearbyFilterBinding binding;

		public ViewHolder(RowNearbyFilterBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
	}
	public void onClickItem(final Category category) {
		if (categories.get(categories.indexOf(category)).status.equalsIgnoreCase("1")) {
			category.setStatus("0");
			onItemClickCheckUnCheckListener.onRemove(categories.indexOf(category),category);
		}
		else {
			category.setStatus("1");
			onItemClickCheckUnCheckListener.onAdd(categories.indexOf(category),category);
		}
		categories.set(categories.indexOf(category), category);
		notifyItemChanged(categories.indexOf(category));
	}
}
