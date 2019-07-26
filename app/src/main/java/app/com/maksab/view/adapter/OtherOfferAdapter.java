package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.OfferDetailsResponse;
import app.com.maksab.databinding.RowOtherOfferBinding;
import app.com.maksab.listener.OnItemClickListener;

public class OtherOfferAdapter extends RecyclerView.Adapter<OtherOfferAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OfferDetailsResponse.OtherOffer> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public OtherOfferAdapter(Context context, ArrayList<OfferDetailsResponse.OtherOffer> categoryListArrayList,
                             OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowOtherOfferBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater
                .from(context), R.layout.row_other_offer, parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.rowHomeBinding.setAdapter(this);

        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));
        if (categoryListArrayList.get(position).getReaming().equalsIgnoreCase("0")||categoryListArrayList.get(position)
                .getReaming().equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.remaining.setVisibility(View.GONE);
        }else {
            holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
        }

        if (categoryListArrayList.get(position).getBought().equalsIgnoreCase("0")||categoryListArrayList.get(position)
                .getBought().equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.bought.setVisibility(View.GONE);
        }else {
            holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
        }
        holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


       /* if (categoryListArrayList.get(position).getFavStatus().equalsIgnoreCase("1"))
            holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable.favorites_hover3x));
        else
            holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable.favorites3x));

        holder.rowHomeBinding.favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceConnector.readBoolean(context, PreferenceConnector.IS_LOGIN, false)) {
                    ProgressDialog.getInstance().showProgressDialog(context);
                    UserOfferIdModel userOfferIdModel = new UserOfferIdModel();
                    userOfferIdModel.setUserId(Utility.getUserId(context));
                    userOfferIdModel.setLanguage(Utility.getLanguage(context));
                    userOfferIdModel.setOfferId(categoryListArrayList.get(position).offerId);

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
        });*/
    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowOtherOfferBinding rowHomeBinding;

        private ViewHolder(RowOtherOfferBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     * @param offerList Store object of clicked position
     */
    public void onItemClick(OfferDetailsResponse.OtherOffer offerList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(offerList), offerList);
    }
}