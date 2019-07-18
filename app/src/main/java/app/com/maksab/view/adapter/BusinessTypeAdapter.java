package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.api.dao.NotificationFilterResponse;
import app.com.maksab.databinding.RowBusinessTypeBinding;
import app.com.maksab.listener.OnItemClickListener;

public class BusinessTypeAdapter extends RecyclerView.Adapter<BusinessTypeAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<NotificationFilterResponse.CategoryList> resultLists;
    private OnItemClickListener onItemClickListener;

    public BusinessTypeAdapter(Activity context, ArrayList<NotificationFilterResponse.CategoryList> resultLists, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowBusinessTypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context)
                , R.layout.row_business_type,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowBusinessTypeBinding binding;

        public ViewHolder(RowBusinessTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onClickItem(final NotificationFilterResponse.CategoryList serviceTypesList) {
        onItemClickListener.onClick(resultLists.indexOf(serviceTypesList),serviceTypesList);
    }
}
