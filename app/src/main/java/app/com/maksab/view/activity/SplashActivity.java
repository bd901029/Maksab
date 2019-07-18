package app.com.maksab.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import java.util.Locale;

import app.com.maksab.R;
import app.com.maksab.databinding.ActivitySplashBinding;
import app.com.maksab.util.Constant;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.Utility;


public class SplashActivity extends AppCompatActivity {
    Handler handler;
    public static int SPLASHTIME = 3000;
    Message msg;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setActivity(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PreferenceConnector.readBoolean(SplashActivity.this, PreferenceConnector.IS_LOGIN, false)) {
                    if (Utility.getLanguage(SplashActivity.this).equals(Constant.LANGUAGE_ARABIC)) {
                        Locale locale = new Locale(Constant.LANGUAGE_ARABIC);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    }
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else {
                    if (!TextUtils.isEmpty(Utility.getCountry(SplashActivity.this)) &&  !TextUtils.isEmpty(Utility
                            .getLanguage(SplashActivity.this))) {
                        if (Utility.getLanguage(SplashActivity.this).equals(Constant.LANGUAGE_ARABIC)) {
                            Locale locale = new Locale(Constant.LANGUAGE_ARABIC);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                        }
                        startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                        finish();
                    }else {
                        String lang= Locale.getDefault().getLanguage();
                        Log.e("lang",lang+"");
                        switch (lang){
                            case Constant.LANGUAGE_ARABIC:
                                PreferenceConnector.writeString(SplashActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
                                        .LANGUAGE_ARABIC);
                                break;
                            case Constant.LANGUAGE_ENGLISH:
                                PreferenceConnector.writeString(SplashActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
                                        .LANGUAGE_ENGLISH);
                                break;
                            case Constant.LANGUAGE_TURKYCE:
                                PreferenceConnector.writeString(SplashActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
                                        .LANGUAGE_ENGLISH);
                                break;
                            default:
                                PreferenceConnector.writeString(SplashActivity.this, PreferenceConnector.APP_LANGUAGE, Constant
                                        .LANGUAGE_ENGLISH);
                                break;
                        }




                        Intent intent = new Intent(SplashActivity.this, CountryLanguageActivity.class);
                        intent.putExtra(Constant.FROM_ACTIVITY, Constant.FROM_ACTIVITY_SPLASH);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean isAlreadyLogin() {
        return PreferenceConnector.readBoolean(SplashActivity.this, PreferenceConnector.IS_LOGIN, false);
    }
}
