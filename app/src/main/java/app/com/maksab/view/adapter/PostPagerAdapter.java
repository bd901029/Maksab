package app.com.maksab.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.Constant;

/**
 * Created by Android on 2/13/2018.
 */

public class PostPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> resultLists;
    private OnItemClickListener onItemClickListener;

    public PostPagerAdapter(Context mContext, ArrayList<String> resultLists,  OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.resultLists = resultLists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return resultLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        AQuery aQuery = new AQuery(imageView);
        aQuery.id(imageView).image(resultLists.get(position), true, true, 1500, R.drawable.ic_launcher);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(position,resultLists.get(position));
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}