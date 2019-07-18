package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.FavoritePartnerListResponse;
import app.com.maksab.databinding.ItemFaqBinding;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.view.viewmodel.FaqModel;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FaqModel.FaqList> resultLists;
    private OnItemClickListener onItemClickListener;
    int selectedposition = -1;

    public FaqAdapter(Context context, ArrayList<FaqModel.FaqList> faqList) {
        this.context = context;
        this.resultLists = faqList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFaqBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_faq,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));

        holder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedposition = position;
                notifyDataSetChanged();
            }
        });

        if(selectedposition==position){
            holder.binding.faqIv.setImageResource(R.drawable.faq_open);
            holder.binding.card.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            holder.binding.bottomLl.setBackground(context.getResources().getDrawable(R.drawable.rectangle_yellow));
            holder.binding.bottomLl.setVisibility(View.VISIBLE);
        }else{
            holder.binding.faqIv.setImageResource(R.drawable.faq_closed);
            holder.binding.card.setBackground(context.getResources().getDrawable(R.drawable.rectangle_yellow));
            holder.binding.bottomLl.setVisibility(View.GONE);
        }

        //if (resultLists.get(position).get)
    }

    @Override
    public int getItemCount() {
       return resultLists.size();
    }

    public void setList(ArrayList<FaqModel.FaqList> faqList) {
        this.resultLists = faqList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFaqBinding binding;
        public ViewHolder(ItemFaqBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onItemClick(final FavoritePartnerListResponse.BrandDataList category) {
        onItemClickListener.onClick(resultLists.indexOf(category), category);
    }
}
