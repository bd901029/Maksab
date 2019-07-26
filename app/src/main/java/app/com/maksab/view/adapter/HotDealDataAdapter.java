package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AddFavoritesResponse;
import app.com.maksab.databinding.RowHotDealDataBinding;
import app.com.maksab.engine.offer.Offer;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class HotDealDataAdapter extends RecyclerView.Adapter<HotDealDataAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Offer> hotDeals;
    private OnItemClickListener onItemClickListener;

    public HotDealDataAdapter(Context context, ArrayList<Offer> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.hotDeals = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowHotDealDataBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_hot_deal_data, parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.rowHomeBinding.setAdapter(this);

        hotDeals.get(position).discountRate = hotDeals.get(position).discountRate + " " + context.getResources().getString(R.string.off);

        holder.rowHomeBinding.setModel(hotDeals.get(position));
        if (hotDeals.get(position).reaming.equalsIgnoreCase("0")|| hotDeals.get(position)
                .reaming.equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.remaining.setVisibility(View.INVISIBLE);
        }else {
            holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
        }

        if (hotDeals.get(position).bought.equalsIgnoreCase("0")|| hotDeals.get(position)
                .bought.equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.bought.setVisibility(View.INVISIBLE);
        }else {
            holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
        }

        holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        if (hotDeals.get(position).favStatus.equalsIgnoreCase("1"))
            holder.rowHomeBinding.favImage.setBackgroundResource(R.drawable.favorites_hover3x);
        else
            holder.rowHomeBinding.favImage.setBackgroundResource(R.drawable.favorites3x);

        holder.rowHomeBinding.favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceConnector.readBoolean(context, PreferenceConnector.IS_LOGIN, false)) {
                    ProgressDialog.getInstance().showProgressDialog(context);
                    UserOfferIdModel userOfferIdModel = new UserOfferIdModel();
                    userOfferIdModel.setUserId(Utility.getUserId(context));
                    userOfferIdModel.setLanguage(Utility.getLanguage(context));
                    userOfferIdModel.setOfferId(hotDeals.get(position).id);

                    Api api = APIClient.getClient().create(Api.class);
                    final Call<AddFavoritesResponse> responseCall = api.addOfferInWishlist(userOfferIdModel);
                    responseCall.enqueue(new Callback<AddFavoritesResponse>() {
                        @Override
                        public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
                                response) {
                            ProgressDialog.getInstance().dismissDialog();
                            if (response.body().getFavStatus().equalsIgnoreCase("1"))
                                holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable.favorites_hover3x));
                            else
                                holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable.favorites3x));
                            // notifyItemChanged(position);
                        }

                        @Override
                        public void onFailure(Call<AddFavoritesResponse> call, Throwable t) {
                            ProgressDialog.getInstance().dismissDialog();
                            // Utility.showToast(context, t + "");
                            Log.e("", "onFailure: " + t.getLocalizedMessage());
                        }
                    });
                } else {
                    // userGuestDialog();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return hotDeals != null ? hotDeals.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowHotDealDataBinding rowHomeBinding;

        private ViewHolder(RowHotDealDataBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     *
     * @param hotDeal Store object of clicked position
     */
    public void onItemClick(Offer hotDeal) {
        onItemClickListener.onClick(hotDeals.indexOf(hotDeal), hotDeal);
    }
}