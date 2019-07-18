package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.FavoritePartnerListResponse;
import app.com.maksab.api.dao.SearchListResponse;
import app.com.maksab.databinding.RowFavoritesPartnersBinding;
import app.com.maksab.databinding.RowSearchBrandBinding;
import app.com.maksab.listener.OnItemClickListener;

public class SearchBrandAdapter extends RecyclerView.Adapter<SearchBrandAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<SearchListResponse.BrandData> resultLists;
    private OnItemClickListener onItemClickListener;

    public SearchBrandAdapter(Activity context, ArrayList<SearchListResponse.BrandData> resultLists,
                              OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowSearchBrandBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                        .row_search_brand,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        holder.binding.progressBar.setVisibility(View.GONE);
        //if (resultLists.get(position).get)
    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowSearchBrandBinding binding;
        public ViewHolder(RowSearchBrandBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onItemClick(final SearchListResponse.BrandData category) {
        onItemClickListener.onClick(resultLists.indexOf(category), category);
        /*Intent intent = new Intent(context, StoreDetailsActivity.class);
        intent.putExtra("STORE_NAME", resultLists.get(position).getStoreName());
        intent.putExtra("STORE_ID", resultLists.get(position).getStoreId());
        context.startActivity(intent);*/
    }
}
