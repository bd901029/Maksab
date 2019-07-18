package app.com.maksab.bindingadapter;

import android.databinding.BindingAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by RWS 6 on 7/28/2017.
 */

public class FontBinding {
    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(FontCache.getInstance(textView.getContext()).get(fontName));
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(Switch textView, String fontName) {
        textView.setTypeface(FontCache.getInstance(textView.getContext()).get(fontName));
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(Button textView, String fontName) {
        textView.setTypeface(FontCache.getInstance(textView.getContext()).get(fontName));
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(EditText textView, String fontName) {
        textView.setTypeface(FontCache.getInstance(textView.getContext()).get(fontName));
    }
}