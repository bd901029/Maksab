package app.com.maksab.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import app.com.maksab.R;
import app.com.maksab.listener.OnItemClickListener;
import com.androidquery.AQuery;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;

/**
 * Created by Android on 2/13/2018.
 */

public class PostPagerZoomAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> resultLists;
    private OnItemClickListener onItemClickListener;

    public PostPagerZoomAdapter(Context mContext, ArrayList<String> resultLists, OnItemClickListener onItemClickListener) {
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.row_pager_zoom_item, container, false);
        ZoomageView imageView = (ZoomageView) itemView.findViewById(R.id.img_pager_item);
        //SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)itemView.findViewById(R.id.img_pager_item);
        AQuery aQuery = new AQuery(imageView);
        aQuery.id(imageView).image(resultLists.get(position), true, true, 1500, R.drawable.ic_launcher);
        //                  .image(allUrls.get(position), false, true, 0, 0, placeholder, Constants.FADE_IN);

        ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(itemView.getContext());
        imageView.setOnTouchListener(imageMatrixTouchHandler);
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