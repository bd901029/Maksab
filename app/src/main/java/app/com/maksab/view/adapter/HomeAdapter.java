package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.StoreListResponse;
import app.com.maksab.databinding.RowHomeBinding;
import app.com.maksab.listener.OnItemClickListener;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StoreListResponse.Store> storeArrayList;
    private OnItemClickListener onItemClickListener;

    public HomeAdapter(Context context, ArrayList<StoreListResponse.Store> storeArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.storeArrayList = storeArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowHomeBinding rowHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_home, parent, false);
        return new ViewHolder(rowHomeBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rowHomeBinding.setAdapter(this);
        holder.rowHomeBinding.setModel(storeArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return storeArrayList != null ? storeArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowHomeBinding rowHomeBinding;

        private ViewHolder(RowHomeBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     *
     * @param store Store object of clicked position
     */
    public void onItemClick(StoreListResponse.Store store) {
        onItemClickListener.onClick(storeArrayList.indexOf(store), store);
    }
}