package app.com.maksab.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;

import app.com.maksab.R;

/**
 * Created by RWS-DESIGNER on 10/10/2018.
 */

public class ImageViewPagerAdapter extends PagerAdapter {
    private ArrayList<String> resultLists;
    private static final float DEFAULT_SCALE_THRESHOLD = 1.2f;
    private float scaleThreshold;



    public ImageViewPagerAdapter(ArrayList<String> resultLists) {
        this.resultLists = resultLists;
        this.scaleThreshold = DEFAULT_SCALE_THRESHOLD;

    }

    /**
     * <p>Sets the scale threshold.</p>
     * @param scaleThreshold
     */
    public void setScaleThreshold(float scaleThreshold) {
        this.scaleThreshold = scaleThreshold;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.page_image, null);
        container.addView(view);
        ImageView imageView = view.findViewById(R.id.image);
        //SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)view.findViewById(id.imageView);

        AQuery aQuery = new AQuery(imageView);
        aQuery.id(imageView).image(resultLists.get(position), true, true, 300, R.drawable.ic_launcher);
        ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(context);
        imageView.setOnTouchListener(imageMatrixTouchHandler);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;

        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(0);

        container.removeView(view);
    }

    @Override
    public int getCount() {
        return resultLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition (Object object) {
        return POSITION_NONE;
    }
}
