package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.databinding.RowNavigationItemBinding;
import app.com.maksab.listener.OnItemClickListener;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private ArrayList<DrawerItem> drawerItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public NavigationAdapter(Context context, ArrayList<DrawerItem> drawerItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.drawerItems = drawerItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowNavigationItemBinding rowNavigationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_navigation_item, parent, false);
        return new ViewHolder(rowNavigationItemBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.rowNavigationItemBinding.setAdapter(this);
        holder.rowNavigationItemBinding.setPosition(position);
        holder.rowNavigationItemBinding.setModel(drawerItems.get(position));


        holder.rowNavigationItemBinding.rowLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(position,holder.rowNavigationItemBinding.bottomLl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawerItems != null ? drawerItems.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowNavigationItemBinding rowNavigationItemBinding;

        private ViewHolder(RowNavigationItemBinding rowNavigationItemBinding) {
            super(rowNavigationItemBinding.getRoot());
            this.rowNavigationItemBinding = rowNavigationItemBinding;
        }

    }

    public void onItemClick(int position) {
        onItemClickListener.onClick(position, null);
    }
}
