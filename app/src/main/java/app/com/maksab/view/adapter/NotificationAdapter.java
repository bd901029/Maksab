package app.com.maksab.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.com.maksab.R;
import app.com.maksab.api.dao.NotificationResponse;
import app.com.maksab.databinding.RowNotificationBinding;
import app.com.maksab.listener.OnItemClickListener;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<NotificationResponse.NotificationList> resultLists;
    private OnItemClickListener onItemClickListener;

    public NotificationAdapter(Activity context, ArrayList<NotificationResponse.NotificationList> resultLists, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowNotificationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R
                        .layout.row_notification, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(resultLists.get(position));
        if (resultLists.get(position).getNotificationImg().equalsIgnoreCase(""))
        holder.binding.llImage.setVisibility(View.GONE);
        else
            holder.binding.llImage.setVisibility(View.VISIBLE);

        holder.binding.offerDescription.setText(Html.fromHtml(resultLists.get(position).getNotificationDescription()));
        holder.binding.progressBar.setVisibility(View.GONE);

        String date = resultLists.get(position).getNotificationDate();
        SimpleDateFormat spf=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("MMM dd, yyyy");
        date = spf.format(newDate);
        System.out.println(date);

        holder.binding.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowNotificationBinding binding;

        public ViewHolder(RowNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void onItemClick(final NotificationResponse.NotificationList notificationList) {
        onItemClickListener.onClick(resultLists.indexOf(notificationList), notificationList);
    }
}
