package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.databinding.RowSubCategoryDataBinding;
import app.com.maksab.engine.offer.Subcategory;
import app.com.maksab.listener.OnItemClickListener;

public class SubCategoryDataAdapter extends RecyclerView.Adapter<SubCategoryDataAdapter
		.ViewHolder> {

	private Context context;
	private ArrayList<Subcategory> subcategories;
	private OnItemClickListener onItemClickListener;

	public SubCategoryDataAdapter(Context context, ArrayList<Subcategory> subcategories, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.subcategories = subcategories;
		this.onItemClickListener = onItemClickListener;
	}

	//CategorySubItemAdapter
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowSubCategoryDataBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater
				.from(context), R.layout.row_sub_category_data, parent, false);
		return new ViewHolder(rowCategoryHomeBinding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.rowHomeBinding.setAdapter(this);
		holder.rowHomeBinding.setModel(subcategories.get(position));
		if (subcategories.get(position).offers != null && subcategories.get(position).offers.size() != 0) {
			OnItemClickListener onItemClickListener = new OnItemClickListener() {
				@Override
				public void onClick(int position, Object obj) {
				}
			};
			holder.rowHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context,
					LinearLayoutManager.HORIZONTAL, false));
			holder.rowHomeBinding.recyclerView.setAdapter(new CategorySubItemAdapter(context,
					subcategories.get(position).offers, onItemClickListener));
		}

	}

	@Override
	public int getItemCount() {
		return subcategories != null ? subcategories.size() : 0;
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private RowSubCategoryDataBinding rowHomeBinding;

		private ViewHolder(RowSubCategoryDataBinding rowHomeBinding) {
			super(rowHomeBinding.getRoot());
			this.rowHomeBinding = rowHomeBinding;
		}
	}

	/**
	 * On Item click listener method
	 * @param subcategory Store object of clicked position
	 */
	public void onClickViewAll(Subcategory subcategory) {
		onItemClickListener.onClick(subcategories.indexOf(subcategory), subcategory);
	}
}