package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.OfferFilterResponse;
import app.com.maksab.databinding.RowDialogSortListBinding;
import app.com.maksab.listener.OnItemClickListener;

public class DialogSortListAdapter extends RecyclerView.Adapter<DialogSortListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<OfferFilterResponse.SortByList> resultLists;
    private OnItemClickListener onItemClickListener;

    public DialogSortListAdapter(Activity context, ArrayList<OfferFilterResponse.SortByList> resultLists, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowDialogSortListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                        .row_dialog_sort_list,
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

        private RowDialogSortListBinding binding;

        public ViewHolder(RowDialogSortListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onItemClick(final OfferFilterResponse.SortByList resultList) {
        onItemClickListener.onClick(resultLists.indexOf(resultList), resultList);
    }
}
