package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import app.com.maksab.R;
import app.com.maksab.api.dao.AlphabetResponse;
import app.com.maksab.databinding.RowAlphabetBinding;
import app.com.maksab.listener.OnItemClickListener;

import java.util.ArrayList;

public class AlphabetItemAdapter extends RecyclerView.Adapter<AlphabetItemAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<AlphabetResponse> resultLists;
    private OnItemClickListener onItemClickListener;

    public AlphabetItemAdapter(Activity context, ArrayList<AlphabetResponse> resultLists,  OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowAlphabetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_alphabet,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        Log.e("getAlphabet",resultLists.get(position).getAlphabet());
    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowAlphabetBinding binding;

        public ViewHolder(RowAlphabetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onItemClick(final AlphabetResponse resultList) {
        onItemClickListener.onClick(resultLists.indexOf(resultList), resultList);

//        int position = resultLists.indexOf(resultList);
//        Intent intent = new Intent(context, DemoNotifActivity.class);
//        intent.putExtra("CALL_FRAGMENT", "OPTHER_PROFILE");
//        intent.putExtra("OTHER_USER_ID", resultLists.get(position).getLikeUserId());
//        context.startActivity(intent);


    }
}
