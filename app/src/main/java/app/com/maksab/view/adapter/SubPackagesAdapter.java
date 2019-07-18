package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.PackagesResponse;
import app.com.maksab.databinding.RowSubPackagesBinding;
import app.com.maksab.listener.OnItemClickListener;

public class SubPackagesAdapter extends RecyclerView.Adapter<SubPackagesAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<PackagesResponse.Facilitys> categoryListArrayList;
    private OnItemClickListener onItemClickListener;
    private String bgColor;

    public SubPackagesAdapter(Activity context, ArrayList<PackagesResponse.Facilitys> categoryListArrayList,
                              OnItemClickListener onItemClickListener, String bgColor) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        //this.categoryListArrayList.addAll(categoryListArrayList);
        this.onItemClickListener = onItemClickListener;
        this.bgColor = bgColor;
    }
//CategorySubItemAdapter
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowSubPackagesBinding rowCategoryHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_sub_packages,
                parent, false);
        return new ViewHolder(rowCategoryHomeBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rowHomeBinding.setAdapter(this);
        holder.rowHomeBinding.setModel(categoryListArrayList.get(position));

        int borderThink = 1;
        if (categoryListArrayList.get(position).getFacilityStatus().equalsIgnoreCase("1"))
            borderThink = 1;
        else
            borderThink = 10;

                // Initialize a new GradientDrawable
        GradientDrawable gd = new GradientDrawable();

        // Set GradientDrawable shape is a rectangle
        gd.setShape(GradientDrawable.OVAL);

        // Set 3 pixels width solid blue color border
       // gd.setStroke(3, Color.BLUE);
        gd.setStroke(borderThink, Color.parseColor(bgColor));

        // Set GradientDrawable width and in pixels
        gd.setSize(18, 18); // Width 450 pixels and height 150 pixels

        // Set GradientDrawable as ImageView source image
        holder.rowHomeBinding.circleImg.setImageDrawable(gd);



    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowSubPackagesBinding rowHomeBinding;

        private ViewHolder(RowSubPackagesBinding rowHomeBinding) {
            super(rowHomeBinding.getRoot());
            this.rowHomeBinding = rowHomeBinding;
           // rowHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,
              //      false));
        }
    }

    /**
     * On Item click listener method
     * @param categoryData Store object of clicked position
     */
    public void onItemClick(PackagesResponse.Facilitys categoryData) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(categoryData), categoryData);
    }
}