package app.com.maksab.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.FavoriteOfferListResponse;
import app.com.maksab.databinding.RowDealsBinding;
import app.com.maksab.listener.On2ItemClickListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;
import app.com.maksab.view.activity.OfferDetailsActivity;

public class FavoriteOfferListAdapter extends RecyclerView.Adapter<FavoriteOfferListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<FavoriteOfferListResponse.OfferList> resultLists;
    private On2ItemClickListener onItemClickListener;

    public FavoriteOfferListAdapter(Activity context, ArrayList<FavoriteOfferListResponse.OfferList> resultLists,
                                    On2ItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowDealsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R
                        .layout.row_deals, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);

        holder.binding.setModel(resultLists.get(position));
        if (resultLists.get(position).getFavStatus().equals("1")){
           // holder.binding.bookmark
        }

        if (resultLists.get(position).getReaming().equalsIgnoreCase("0")||resultLists.get(position)
                .getReaming().equalsIgnoreCase("Unlimited")){
            holder.binding.remaining.setVisibility(View.GONE);
        }else {
            holder.binding.remaining.setVisibility(View.VISIBLE);
        }

        if (resultLists.get(position).getBought().equalsIgnoreCase("0")||resultLists.get(position)
                .getBought().equalsIgnoreCase("Unlimited")){
            holder.binding.bought.setVisibility(View.GONE);
        }else {
            holder.binding.bought.setVisibility(View.VISIBLE);
        }

        holder.binding.beforeAmount.setPaintFlags(holder.binding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


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

        private RowDealsBinding binding;

        public ViewHolder(RowDealsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onClickFavorites(final FavoriteOfferListResponse.OfferList offerList) {
        onItemClickListener.onClickTwo(resultLists.indexOf(offerList), offerList);

    }

        public void onItemClick(final FavoriteOfferListResponse.OfferList offerList) {
        onItemClickListener.onClickOne(resultLists.indexOf(offerList), offerList);
        /*Intent intent = new Intent(context, StoreDetailsActivity.class);
        intent.putExtra("STORE_NAME", resultLists.get(position).getStoreName());
        intent.putExtra("STORE_ID", resultLists.get(position).getStoreId());
        context.startActivity(intent);*/
    }
}
