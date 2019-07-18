package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AddFavoritesResponse;
import app.com.maksab.api.dao.OfferListResponse;
import app.com.maksab.databinding.RowOfferGridBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.activity.BrandDetailActivity;
import app.com.maksab.view.activity.OfferDetailsActivity;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferGridAdapter extends RecyclerView.Adapter<OfferGridAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OfferListResponse.OfferList> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public OfferGridAdapter(Context context, ArrayList<OfferListResponse.OfferList> categoryListArrayList,
                            OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowOfferGridBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater
                .from(context), R.layout.row_offer_grid, parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.rowHomeBinding.setAdapter(this);
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));

        int width = (PreferenceConnector.readInteger(context,PreferenceConnector.DEVICE_WIDTH,150)/2);
        ViewGroup.LayoutParams params = holder.rowHomeBinding.llImage.getLayoutParams();
        params.height =width-4;
        params.width = width;
        holder.rowHomeBinding.llImage.setLayoutParams(params);


        if (categoryListArrayList.get(position).getReaming().equalsIgnoreCase("0")||categoryListArrayList.get(position)
                .getReaming().equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.remaining.setVisibility(View.INVISIBLE);
        }else {
            holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
        }

        if (categoryListArrayList.get(position).getBought().equalsIgnoreCase("0")||categoryListArrayList.get(position)
                .getBought().equalsIgnoreCase("Unlimited")){
            holder.rowHomeBinding.bought.setVisibility(View.INVISIBLE);
        }else {
            holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
        }

        holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        if (categoryListArrayList.get(position).getFavStatus().equalsIgnoreCase("1"))
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
                    userOfferIdModel.setOfferId(categoryListArrayList.get(position).offerId);

                    Api api = APIClient.getClient().create(Api.class);
                    final Call<AddFavoritesResponse> responseCall = api.addOfferInWishlist(userOfferIdModel);
                    responseCall.enqueue(new Callback<AddFavoritesResponse>() {
                        @Override
                        public void onResponse(Call<AddFavoritesResponse> call, Response<AddFavoritesResponse>
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
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowOfferGridBinding rowHomeBinding;

        private ViewHolder(RowOfferGridBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     *
     * @param offerList Store object of clicked position
     */
    public void onItemClick(OfferListResponse.OfferList offerList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(offerList), offerList);
    }
}