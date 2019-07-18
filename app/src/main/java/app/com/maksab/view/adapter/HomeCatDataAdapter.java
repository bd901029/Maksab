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
import app.com.maksab.api.dao.HomeDataResponse;
import app.com.maksab.databinding.RowHomeCatDataBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeCatDataAdapter extends RecyclerView.Adapter<HomeCatDataAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HomeDataResponse.NewdealData> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public HomeCatDataAdapter(Context context, ArrayList<HomeDataResponse.NewdealData> categoryListArrayList,
                              OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowHomeCatDataBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater
                .from(context), R.layout.row_home_cat_data, parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.rowHomeBinding.setAdapter(this);
        /*categoryListArrayList.get(position).setBought(context.getResources().getString(R.string.bought) +" "+
                categoryListArrayList.get(position).getBought());
        categoryListArrayList.get(position).setReaming(context.getResources().getString(R.string.remaining) +" "+
                categoryListArrayList.get(position).getReaming());*/
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));

        if (categoryListArrayList.get(position).getReaming().equalsIgnoreCase("0") || categoryListArrayList.get(position)
                .getReaming().equalsIgnoreCase("Unlimited")) {
            holder.rowHomeBinding.remaining.setVisibility(View.INVISIBLE);
        } else {
            holder.rowHomeBinding.remaining.setVisibility(View.VISIBLE);
        }

        if (categoryListArrayList.get(position).getBought().equalsIgnoreCase("0") || categoryListArrayList.get(position)
                .getBought().equalsIgnoreCase("Unlimited")) {
            holder.rowHomeBinding.bought.setVisibility(View.INVISIBLE);
        } else {
            holder.rowHomeBinding.bought.setVisibility(View.VISIBLE);
        }

        holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint
                .STRIKE_THRU_TEXT_FLAG);


        if (categoryListArrayList.get(position).getFavStatus().equalsIgnoreCase("1"))
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
                                holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable
                                        .favorites_hover3x));
                            else
                                holder.rowHomeBinding.favImage.setBackground(ContextCompat.getDrawable(context, R.drawable
                                        .favorites3x));

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
                            });
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

        private RowHomeCatDataBinding rowHomeBinding;

        private ViewHolder(RowHomeCatDataBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     *
     * @param hotdealData Store object of clicked position
     */
    public void onItemClick(HomeDataResponse.NewdealData hotdealData) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(hotdealData), hotdealData);
    }

    public void onClickFav(HomeDataResponse.NewdealData hotdealData) {
        // resultLists.remove(categoryListArrayList.indexOf(hotdealData));
        //notifyItemRemoved(categoryListArrayList.indexOf(hotdealData));
    }


}