package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
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
import app.com.maksab.api.dao.FavoritePartnerListResponse;
import app.com.maksab.databinding.RowFavoritesAllPartnersBinding;
import app.com.maksab.databinding.RowFavoritesPartnersBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import app.com.maksab.view.viewmodel.UserPartnerIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class FavoritesAllPartnersAdapter extends RecyclerView.Adapter<FavoritesAllPartnersAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<FavoritePartnerListResponse.BrandDataList> resultLists;
    private OnItemClickListener onItemClickListener;

    public FavoritesAllPartnersAdapter(Activity context, ArrayList<FavoritePartnerListResponse.BrandDataList> resultLists,
                                       OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowFavoritesAllPartnersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_favorites_all_partners,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        holder.binding.progressBar.setVisibility(View.GONE);
        if (resultLists.get(position).getFavStatus().equalsIgnoreCase("1"))
            holder.binding.favImage.setBackgroundResource(R.drawable.favorites_hover3x);
        else
            holder.binding.favImage.setBackgroundResource(R.drawable.favorites3x);




        holder.binding.favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceConnector.readBoolean(context, PreferenceConnector.IS_LOGIN, false)) {
                    ProgressDialog.getInstance().showProgressDialog(context);
                    UserPartnerIdModel userPartnerIdModel = new UserPartnerIdModel();
                    userPartnerIdModel.setUserId(Utility.getUserId(context));
                    userPartnerIdModel.setLanguage(Utility.getLanguage(context));
                    userPartnerIdModel.setPartnerId(resultLists.get(position).getBrandId());
                    Api api = APIClient.getClient().create(Api.class);
                    final Call<AddFavoritesResponse> responseCall = api.addPartnerInWishlist(userPartnerIdModel);
                    responseCall.enqueue(new Callback<AddFavoritesResponse>() {
                        @Override
                        public void onResponse(Call<AddFavoritesResponse> call, retrofit2.Response<AddFavoritesResponse>
                                response) {
                            ProgressDialog.getInstance().dismissDialog();
                            if (response.body().getFavStatus().equalsIgnoreCase("1"))
                                holder.binding.favImage.setBackgroundResource(R.drawable.favorites_hover3x);
                            else
                                holder.binding.favImage.setBackgroundResource(R.drawable.favorites3x);
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
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowFavoritesAllPartnersBinding binding;
        public ViewHolder(RowFavoritesAllPartnersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onItemClick(final FavoritePartnerListResponse.BrandDataList category) {
        onItemClickListener.onClick(resultLists.indexOf(category), category);
    }
}
