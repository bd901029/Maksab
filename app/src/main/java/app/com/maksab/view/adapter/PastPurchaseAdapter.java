package app.com.maksab.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.api.dao.UpcomingPurchaseModel;
import app.com.maksab.databinding.DialogRateItBinding;
import app.com.maksab.databinding.ItemsOldPurchaseBinding;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.RateReviewModel;
import retrofit2.Call;
import retrofit2.Callback;

public class PastPurchaseAdapter extends RecyclerView.Adapter<PastPurchaseAdapter.ViewHolder> {

    private Context context;
    int mposition;
    ArrayList<UpcomingPurchaseModel.OfferList> offerLists;
    String sRating = "";

    public PastPurchaseAdapter(Context context, int prosition) {
        this.context = context;
        this.mposition = prosition;
        offerLists = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemsOldPurchaseBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.items_old_purchase, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.binding.setAdapter(this);
        Glide.with(context).load(offerLists.get(position).getOfferImg()).into(holder.binding.offerImg);
        Glide.with(context).load(offerLists.get(position).getPartner_img()).into(holder.binding.noPicImage);
        holder.binding.setModel(offerLists.get(position));
        if(mposition==0){
            holder.binding.ratingLl.setVisibility(View.GONE);
        }else{
            holder.binding.ratingLl.setVisibility(View.VISIBLE);
        }

        if (offerLists.get(position).getRates().equalsIgnoreCase("")||offerLists.get(position).getRates()
                .equalsIgnoreCase("0")){
            holder.binding.rateIt.setVisibility(View.VISIBLE);
            holder.binding.ratingLl.setVisibility(View.GONE);
        }else {
            holder.binding.rateIt.setVisibility(View.GONE);
            holder.binding.ratingLl.setVisibility(View.VISIBLE);
        }


        holder.binding.rateIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Dialog dialogRedeem = new Dialog(context);
              final DialogRateItBinding dialogRateItBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.dialog_rate_it, null, false);
                dialogRedeem.setContentView(dialogRateItBinding.getRoot());
                dialogRedeem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                /*dialogRedeem.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);*/

                dialogRateItBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {
                        sRating = String.valueOf(rating);

                    }
                });

                dialogRateItBinding.close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogRedeem.dismiss();
                    }
                });
                dialogRateItBinding.btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressDialog.getInstance().showProgressDialog(context);
                        RateReviewModel rateReviewModel = new RateReviewModel();
                        rateReviewModel.setCityId(Utility.getCity(context));
                        rateReviewModel.setUserId(Utility.getUserId(context));
                        rateReviewModel.setLanguage(Utility.getLanguage(context));
                        rateReviewModel.setOfferId(offerLists.get(position).getOfferId());
                        rateReviewModel.setOrderId(offerLists.get(position).getOrderId());
                        rateReviewModel.setRate(sRating);
                        rateReviewModel.setReview(dialogRateItBinding.etReview.getText().toString());

                        Api api = APIClient.getClient().create(Api.class);
                        final Call<SuccessfulResponse> responseCall = api.setReviewRate(rateReviewModel);
                        responseCall.enqueue(new Callback<SuccessfulResponse>() {
                            @Override
                            public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
                                    response) {
                                ProgressDialog.getInstance().dismissDialog();
                                    dialogRedeem.dismiss();
                                holder.binding.rateIt.setVisibility(View.GONE);
                                holder.binding.ratingLl.setVisibility(View.VISIBLE);
                                holder.binding.rates.setText(sRating);
                            }

                            @Override
                            public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                                ProgressDialog.getInstance().dismissDialog();
                                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                            }
                        });
                    }
                });
                dialogRedeem.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerLists.size();
    }

    public void setList(ArrayList<UpcomingPurchaseModel.OfferList> offerList) {
        this.offerLists = offerList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemsOldPurchaseBinding binding;

        public ViewHolder(ItemsOldPurchaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
