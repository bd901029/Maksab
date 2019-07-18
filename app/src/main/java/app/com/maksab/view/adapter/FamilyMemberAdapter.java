package app.com.maksab.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.AddFavoritesResponse;
import app.com.maksab.api.dao.GetFamilyMembers;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ItemFamilyMembersBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.activity.HomeActivity;
import app.com.maksab.view.viewmodel.MemberIdModel;
import app.com.maksab.view.viewmodel.UserOfferIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.ViewHolder> {

    private Activity context;
    private OnItemClickListener onItemClickListener;
    ArrayList<GetFamilyMembers.FamMember> famMemberArrayList;

    public FamilyMemberAdapter(Activity context ,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        famMemberArrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFamilyMembersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_family_members, parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(famMemberArrayList.get(position));
        if (famMemberArrayList.get(position).getStatus().equalsIgnoreCase("1")){
            holder.binding.llActive.setVisibility(View.VISIBLE);
            holder.binding.llDeActive.setVisibility(View.GONE);
        }else {
            holder.binding.llActive.setVisibility(View.GONE);
            holder.binding.llDeActive.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return famMemberArrayList.size();
    }

    public void setList(ArrayList<GetFamilyMembers.FamMember> famMembers) {
        this.famMemberArrayList = famMembers;
    notifyDataSetChanged();
}

public class ViewHolder extends RecyclerView.ViewHolder {
    private ItemFamilyMembersBinding binding;
    public ViewHolder(ItemFamilyMembersBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
    public void onClickDelete(final GetFamilyMembers.FamMember famMember){
        onItemClickListener.onClick(famMemberArrayList.indexOf(famMember), famMember);

    }
}
