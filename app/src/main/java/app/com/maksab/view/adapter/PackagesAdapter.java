package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.com.maksab.util.PreferenceConnector;
import com.androidquery.AQuery;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.PackagesResponse;
import app.com.maksab.databinding.RowPackagesBinding;
import app.com.maksab.listener.OnItemClickListener;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<PackagesResponse.PkgPlans> categoryListArrayList;
    private OnItemClickListener onItemClickListener;

    public PackagesAdapter(Activity context, ArrayList<PackagesResponse.PkgPlans> categoryListArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowPackagesBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_packages,
                parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.rowHomeBinding.setAdapter(this);
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));

        int width = (PreferenceConnector.readInteger(context,PreferenceConnector.DEVICE_WIDTH,150)/2);
        ViewGroup.LayoutParams params = holder.rowHomeBinding.llImage.getLayoutParams();
        params.height =width-12;
       // params.width = width;
        holder.rowHomeBinding.llImage.setLayoutParams(params);


        if (categoryListArrayList.get(position).getPlanStatus().equalsIgnoreCase("1")){
            holder.rowHomeBinding.subscribed.setVisibility(View.VISIBLE);
        }else{
            holder.rowHomeBinding.subscribed.setVisibility(View.GONE);
        }

        if (categoryListArrayList.get(position).getPlanImg().equals(""))
        holder.rowHomeBinding.image.setBackgroundColor(Color.parseColor(categoryListArrayList.get(position).planColor));
        else{
             AQuery aQuery = new AQuery(holder.rowHomeBinding.image);
             aQuery.id(holder.rowHomeBinding.image).image(categoryListArrayList.get(position).getPlanImg(), true, true, 300, R
                     .drawable.logo_small);
        }

        holder.rowHomeBinding.beforeAmount.setPaintFlags(holder.rowHomeBinding.beforeAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (categoryListArrayList.get(position).getFacilitysArrayList() != null && categoryListArrayList.get(position).getFacilitysArrayList()
                .size() != 0) {
            OnItemClickListener onItemClickListener2 = new OnItemClickListener() {
                @Override
                public void onClick(int position2, Object obj) {
                    onItemClick(categoryListArrayList.get(holder.getAdapterPosition()));
                }
            };
            holder.rowHomeBinding.recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
            holder.rowHomeBinding.recyclerView.setAdapter(new SubPackagesAdapter(context, categoryListArrayList.get
                    (position).getFacilitysArrayList(), onItemClickListener2 ,categoryListArrayList.get(position)
                    .planColor));
        }
    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RowPackagesBinding rowHomeBinding;
        private ViewHolder(RowPackagesBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
        }
    }

    /**
     * On Item click listener method
     * @param categoryData Store object of clicked position
     */
    public void onItemClick(PackagesResponse.PkgPlans categoryData) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(categoryData), categoryData);
    }
}