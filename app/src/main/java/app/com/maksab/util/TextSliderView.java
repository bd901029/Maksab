package app.com.maksab.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import app.com.maksab.R;

/**
 * Created by RWS-DESIGNER on 10/25/2018.
 */

public class TextSliderView extends BaseSliderView {
    private static Typeface font = null;
    private Context context ;
    public TextSliderView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView)v.findViewById(R.id.description);
        description.setText(getDescription());
        try {
            if(font == null){
                //font = Typeface.createFromAsset(context.getAssets(), "fonts/Al-Jazeera-Arabic-Regular.ttf");
                font = Typeface.createFromAsset(context.getAssets(), "fonts/"+context.getResources().getString(R.string
                .regular)+context.getResources().getString(R.string.ext));
            }
            description.setTypeface(font);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}