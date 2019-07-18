package app.com.maksab.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import app.com.maksab.view.activity.PackagesActivity;

/**
 * Created by User on 27-04-2017.
 */

public class WebAppInterface {
    Activity mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Activity c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Log.e("Toast",toast+"");
        if (toast.equalsIgnoreCase("continue")) {
            Intent mainIntent = new Intent(mContext, PackagesActivity.class);
           // mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(mainIntent);
            mContext.finish();
        }else {
            //Intent mainIntent = new Intent(mContext, SubmitCreditCardActivity.class);
           //CDVFESrvFEC crFW cdrfw mContext.startActivity(mainIntent);
            mContext.finish();
        }

    }
    private void startActivity(Intent mainIntent) {
        // TODO Auto-generated method stub

    }
}