package app.com.maksab.view.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.api.dao.UpcomingPurchaseModel;
import app.com.maksab.databinding.ItemsUpcomingPurchaseBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.viewmodel.OrderIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class UpcomingPurchaseAdapter extends RecyclerView.Adapter<UpcomingPurchaseAdapter.ViewHolder> {

    private Activity context;
    int mposition;
    ArrayList<UpcomingPurchaseModel.OfferList> offerListArrayList;

    public UpcomingPurchaseAdapter(Activity context, int prosition) {
        this.context = context;
        this.mposition = prosition;
        offerListArrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemsUpcomingPurchaseBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                        .items_upcoming_purchase, parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        Glide.with(context).load(offerListArrayList.get(position).getOfferImg()).into(holder.binding.offerImg);
        Glide.with(context).load(offerListArrayList.get(position).getPartner_img()).into(holder.binding.noPicImage);
        holder.binding.setModel(offerListArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return offerListArrayList.size();
    }

    public void setList(ArrayList<UpcomingPurchaseModel.OfferList> offerList) {
        this.offerListArrayList = offerList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemsUpcomingPurchaseBinding binding;

        public ViewHolder(ItemsUpcomingPurchaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onClickDelete(final UpcomingPurchaseModel.OfferList offerList) {
        Utility.setDialog(context, context.getString(R.string.alert), context.getString(R.string.remove_favourite_message),
                context.getString(R.string.no), context.getString(R.string.yes), new DialogListener() {
                    @Override
                    public void onNegative(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onPositive(DialogInterface dialog) {
                        dialog.dismiss();
                        ProgressDialog.getInstance().showProgressDialog(context);
                        OrderIdModel model = new OrderIdModel();
                        model.setOrderId(offerList.getOrderId());
                        model.setLanguage(Utility.getLanguage(context));
                        Api api = APIClient.getClient().create(Api.class);
                        final Call<SuccessfulResponse> responseCall;
                        responseCall = api.deleteUpcomingPurchase(model);
                        responseCall.enqueue(new Callback<SuccessfulResponse>() {
                            @Override
                            public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
                                    response) {
                                if (response.body().getResponseCode().equals(Api.SUCCESS)) {
                                    ProgressDialog.getInstance().dismissDialog();
                                    Utility.showToast(context, response.body().getMessage() + "");
                                    int position = offerListArrayList.indexOf(offerList);
                                    offerListArrayList.remove(offerList);
                                    //offerListArrayList.remove(position);
                                    notifyItemRemoved(position);
                                }
                            }

                            @Override
                            public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                                ProgressDialog.getInstance().dismissDialog();
                                Utility.showToast(context, t + "");
                            }
                        });

                    }
                });


    }


}
