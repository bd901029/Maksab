package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.databinding.RowNavigationItemFirstBinding;
import app.com.maksab.listener.OnItemClickListener;

public class NavigationAdapterFirst extends RecyclerView.Adapter<NavigationAdapterFirst.ViewHolder> {

    private ArrayList<DrawerItem> drawerItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public NavigationAdapterFirst(Context context, ArrayList<DrawerItem> drawerItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.drawerItems = drawerItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowNavigationItemFirstBinding rowNavigationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R
                .layout
                .row_navigation_item_first, parent, false);
        return new ViewHolder(rowNavigationItemBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.rowNavigationItemBinding.setAdapter(this);
        holder.rowNavigationItemBinding.setPosition(position);
        holder.rowNavigationItemBinding.setModel(drawerItems.get(position));


        if (position == 1){
            holder.rowNavigationItemBinding.arrow.setVisibility(View.VISIBLE);
        }
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

        private RowNavigationItemFirstBinding rowNavigationItemBinding;

        private ViewHolder(RowNavigationItemFirstBinding rowNavigationItemBinding) {
            super(rowNavigationItemBinding.getRoot());
            this.rowNavigationItemBinding = rowNavigationItemBinding;
        }

    }

    public void onItemClick(int position) {
        onItemClickListener.onClick(position, null);
    }
}
