package app.com.maksab.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.dao.HomeDataResponse;
import app.com.maksab.api.dao.SendGiftsResponce;
import app.com.maksab.databinding.ItemGiftHistoryBinding;
import app.com.maksab.databinding.ItemGiftReceiveBinding;
import app.com.maksab.util.Constant;
import app.com.maksab.view.activity.OfferDetailsActivity;
import app.com.maksab.view.viewmodel.GiftHistoryModel;

public class GiftReceiveAdapter extends RecyclerView.Adapter<GiftReceiveAdapter.ViewHolder> {

    private Context context;
    ArrayList<SendGiftsResponce.Gift> arrayList;
    public GiftReceiveAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGiftReceiveBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_gift_receive, parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.binding.setAdapter(this);
        Glide.with(context).load(arrayList.get(position).getOfferImg()).into(holder.binding.productImage);
        holder.binding.setModel(arrayList.get(position));
        if (arrayList.get(position).getUseStatus().equalsIgnoreCase("0")){
            //holder.binding.useStatus.setText(context.getResources().getString(R.string.redeem));
            holder.binding.onClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OfferDetailsActivity.class);
                    intent.putExtra(Constant.OFFER_ID,arrayList.get(position).getOfferId());
                    context.startActivity(intent);
                }
            });
        }else {
            holder.binding.useStatus.setText(context.getResources().getString(R.string.redeemed));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setList(ArrayList<SendGiftsResponce.Gift> giftList) {
        this.arrayList = giftList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemGiftReceiveBinding binding;

        public ViewHolder(ItemGiftReceiveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
