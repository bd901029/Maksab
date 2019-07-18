package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import app.com.maksab.R;
import app.com.maksab.databinding.RowDealsBinding;
import app.com.maksab.databinding.RowPartnersBinding;

public class Items0Adapter extends RecyclerView.Adapter<Items0Adapter.ViewHolder> {

    private Context context;

    public Items0Adapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowPartnersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_partners, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowPartnersBinding binding;

        public ViewHolder(final RowPartnersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
    //final StoreResponse.Category resultList
    public void onItemClick() {
        //int position = resultLists.indexOf(resultList);
       // Intent intent = new Intent(context, ProductViewEditActivity.class);
        //intent.putExtra("STORE_NAME", resultLists.get(position).getStoreName());
        //intent.putExtra("STORE_ID", resultLists.get(position).getStoreId());
      //  context.startActivity(intent);


    }
}
