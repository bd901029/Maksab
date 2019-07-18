package app.com.maksab.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressWarnings("deprecation")
public class PreferenceConnector {
    public static final String PREF_NAME = "minimall";
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String USER_DATA = "user_data";
    public static final String USER_ID = "user_id";
    public static final String STORE_ID = "user_id";
    public static final String FCM_ID = "fcm_id";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String IS_STORE_LOGIN = "IS_STORE_LOGIN";
    public static final String APP_LANGUAGE = "app_language";
    public static final String IS_FIRST_TIME = "IS_FIRST_TIME";
    public static final String DEVICE_HEIGHT = "device_height";
    public static final String DEVICE_WIDTH = "device_width";
    public static final String STORE_LOGIN = "STORE_LOGIN";
    public static final String CART_COUNT = "cart_count";
    public static final String CITY = "city";
    public static final String CITY_NAME = "city_name";

    public static final String COUNTRY = "country";
    public static final String COUNTRY_CODE = "country_code";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRY_FLAG = "country_flag";

    public static final String LOGIN_TYPE = "login_type";

    public static final String USER_NAME = "user_name";
    public static final String USER_PIC = "user_pic";
    public static final String IS_MEMBER = "is_member";
    public static final String MAX_DIVICE_COUNT = "max_device_count";




    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);

    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void remove(Context context, String key) {
        getEditor(context).remove(key);

    }

    public static void clear(Context context) {
        getEditor(context).clear().commit();
        getEditor(context).clear().commit();

    }
}
