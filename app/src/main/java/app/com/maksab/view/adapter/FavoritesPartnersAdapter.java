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
import app.com.maksab.databinding.RowFavoritesPartnersBinding;
import app.com.maksab.listener.On2ItemClickListener;
import app.com.maksab.listener.OnItemClickListener;

public class FavoritesPartnersAdapter extends RecyclerView.Adapter<FavoritesPartnersAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<FavoritePartnerListResponse.BrandDataList> resultLists;
    private On2ItemClickListener onItemClickListener;

    public FavoritesPartnersAdapter(Activity context, ArrayList<FavoritePartnerListResponse.BrandDataList> resultLists,
                                    On2ItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowFavoritesPartnersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_favorites_partners,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        holder.binding.progressBar.setVisibility(View.GONE);
        if (resultLists.get(position).getFavStatus().equalsIgnoreCase("1"))
            holder.binding.favImage.setBackgroundResource(R.drawable.favorites_hover3x);
        else
            holder.binding.favImage.setBackgroundResource(R.drawable.favorites3x);
    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowFavoritesPartnersBinding binding;
        public ViewHolder(RowFavoritesPartnersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onItemClick(final FavoritePartnerListResponse.BrandDataList category) {
        onItemClickListener.onClickOne(resultLists.indexOf(category), category);
    }
    public void onClickFav(final FavoritePartnerListResponse.BrandDataList category) {
        onItemClickListener.onClickTwo(resultLists.indexOf(category), category);
    }
}
