package app.com.maksab.api;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private APIClient() {
    }

    private static volatile Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (APIClient.class) {
                // Double check
                if (retrofit == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger
                            () {
                        @Override
                        public void log(String message) {
                            Log.e("OK HTTP", message);
                        }
                    });
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .readTimeout(1800, TimeUnit.SECONDS)
                            .connectTimeout(1800, TimeUnit.SECONDS)
                            .writeTimeout(1800, TimeUnit.SECONDS)
                            .build();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Api.BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                }
            }
        }
        return retrofit;
    }


   /* private APIClient() {
    }
    private static volatile Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (Extension.class) {
                // Double check
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Api.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }*/
}