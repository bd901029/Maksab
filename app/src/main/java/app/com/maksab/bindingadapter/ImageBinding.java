package app.com.maksab.bindingadapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.androidquery.AQuery;
import app.com.maksab.R;

public class ImageBinding {
    @BindingAdapter({"bind:imageUrl"})
    public static void setImage(ImageView imageView, String url) {
        AQuery aQuery = new AQuery(imageView.getContext());
        aQuery.id(imageView).image(url, true, true, 300, R.drawable.logo_small);
    }


    @BindingAdapter({"bind:profileImageUrl"})
    public static void setProfileImage(ImageView imageView, String url) {
        AQuery aQuery = new AQuery(imageView.getContext());
        aQuery.id(imageView).image(url, true, true, 200, R.drawable.logo_small);
    }

    @BindingAdapter({"bind:imageDrawable"})
    public static void setImageFromDrawable(ImageView imageView, int drawable) {
        AQuery aQuery = new AQuery(imageView.getContext());
        aQuery.id(imageView).image(drawable);
    }
}
