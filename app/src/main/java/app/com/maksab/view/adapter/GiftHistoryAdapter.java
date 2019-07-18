package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.databinding.ItemGiftHistoryBinding;
import app.com.maksab.view.viewmodel.GiftHistoryModel;

public class GiftHistoryAdapter extends RecyclerView.Adapter<GiftHistoryAdapter.ViewHolder> {

    private Context context;
    ArrayList<GiftHistoryModel.Gift> arrayList;
    public GiftHistoryAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGiftHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_gift_history, parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        Glide.with(context).load(arrayList.get(position).getOfferImg()).into(holder.binding.productImage);
        holder.binding.setModel(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setList(ArrayList<GiftHistoryModel.Gift> giftList) {
        this.arrayList = giftList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemGiftHistoryBinding binding;

        public ViewHolder(ItemGiftHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
