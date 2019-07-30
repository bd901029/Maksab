package app.com.maksab.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.databinding.RowCategoryDataBinding;
import app.com.maksab.engine.category.Category;
import app.com.maksab.engine.offer.Offer;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.view.activity.OfferDetailsActivity;

public class CategoryDataAdapter extends RecyclerView.Adapter<CategoryDataAdapter.ViewHolder> {

	private Activity context;
	private ArrayList<Category> categories;
	private OnItemClickListener onItemClickListener;

	public CategoryDataAdapter(Activity context, ArrayList<Category> categoryListArrayList, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.categories = categoryListArrayList;
		//this.categories.addAll(categories);
		this.onItemClickListener = onItemClickListener;
	}
	//CategorySubItemAdapter
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RowCategoryDataBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_category_data, parent, false);
		return new ViewHolder(rowCategoryHomeBinding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.rowHomeBinding.setAdapter(this);
		holder.rowHomeBinding.setModel(categories.get(position));
		if (categories.get(position).offers != null && categories.get(position).offers.size() != 0) {
			OnItemClickListener onItemClickListener = new OnItemClickListener() {
				@Override
				public void onClick(int position, Object obj) {
					Offer offerList = (Offer) obj;
					Intent intent = new Intent(context, OfferDetailsActivity.class);
					intent.putExtra(Constant.OFFER_ID, offerList.id);
					context.startActivityForResult(intent, 1);
				}
			};
			holder.rowHomeBinding.recyclerView.setAdapter(new CategorySubItemAdapter(context, categories.get(position).offers, onItemClickListener));
		}
	}

	@Override
	public int getItemCount() {
		return categories != null ? categories.size() : 0;
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private RowCategoryDataBinding rowHomeBinding;

		private ViewHolder(RowCategoryDataBinding rowHomeBinding) {
			super(rowHomeBinding.getRoot());
			this.rowHomeBinding = rowHomeBinding;
			rowHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
		}
	}

	/**
	 * On Item click listener method
	 * @param categoryData Store object of clicked position
	 */
	public void onClickViewAll(Category categoryData) {
		onItemClickListener.onClick(categories.indexOf(categoryData), categoryData);
	}
}