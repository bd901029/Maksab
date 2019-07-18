package app.com.maksab.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import app.com.maksab.R;
import app.com.maksab.util.Utility;
import app.com.maksab.util.WebAppInterface;

public class ActivityPaymentWebview extends AppCompatActivity {
    private WebView mWebview ;
    private String s_user_id,s_user_email,s_user_password,sClientId,projId,sCustom_hours,sCalculatedAmount;
    private String planId = "", paymentAmount = "", userId = "", couponCodeId = "", cardHolder = "", cardNumber = "",
            cvv = "", expiryMonth = "", expiryYear = "", language = "";
    android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            planId = extras.getString(PaymentActivity.planId);
            paymentAmount = extras.getString(PaymentActivity.planAmount);
            userId = Utility.getUserId(ActivityPaymentWebview.this);
            couponCodeId = extras.getString(PaymentActivity.couponCodeId);
            cardNumber = extras.getString(PaymentActivity.cardNumber);
            cardHolder = extras.getString(PaymentActivity.cardHolder);
            expiryMonth = extras.getString(PaymentActivity.expiredMonth);
            expiryYear = extras.getString(PaymentActivity.expiredYear);
            cvv = extras.getString(PaymentActivity.cvvCode);
            /*
                        String expiryDate = extras.getString(PaymentActivity.expiredDate);
            try {
                expiryMonth = (expiryDate.charAt(0)+"")+(expiryDate.charAt(1)+"");
                expiryYear = (expiryDate.charAt(3)+"")+(expiryDate.charAt(4)+"");
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            //expiryMonth = extras.getString(PaymentActivity.expiredDate);
            //expiryYear = extras.getString(PaymentActivity.expiredDate);

            language = Utility.getLanguage(ActivityPaymentWebview.this);
            Log.e("planId",planId+"");
            Log.e("paymentAmount",paymentAmount+"");
            Log.e("userId",userId+"");
            Log.e("couponCodeId",couponCodeId+"");
            Log.e("cardHolder",cardHolder+"");
            Log.e("cardNumber",cardNumber+"");
            Log.e("cvv",cvv+"");
            Log.e("userId",userId+"");
            Log.e("planId",planId+"");
            Log.e("expiryMonth",expiryMonth+"");
            Log.e("expiryYear",expiryYear+"");
        }

        mWebview  = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebview.addJavascriptInterface(new WebAppInterface(ActivityPaymentWebview.this), "Android");
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setLoadWithOverviewMode(true);


        String paymentURL = "http://www.maksabapp.com/develop_api/home/app_payment_1?plan_id="+planId+
                "&payment_amount="+paymentAmount+
                "&user_id="+userId+
                "&coupon_code_id="+couponCodeId+
                "&card_holder="+cardHolder+
                "&card_number="+cardNumber+
                "&cvv="+cvv+
                "&expiry_month="+expiryMonth+
                "&expiry_year="+expiryYear+
                "&language="+language+
                "&device_type="+"android";

        /*mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });*/
        progressDialog = new android.app.ProgressDialog(ActivityPaymentWebview.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(ActivityPaymentWebview.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        mWebview .loadUrl(paymentURL);




    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        //Toast.makeText(ActivityPaymentWebview.this, toast+" Click", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(ActivityPaymentWebview.this, PackagesActivity.class);
        // JavaScriptInterface.this.startActivity(mainIntent);
        ActivityPaymentWebview.this.startActivity(mainIntent);
    }



    @Override
    public  void onBackPressed() {
        super.onBackPressed();
    }
}
