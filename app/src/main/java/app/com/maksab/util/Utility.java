package app.com.maksab.util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.com.maksab.MyApplication;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.com.maksab.R;
import app.com.maksab.api.dao.LoginResponse;
import app.com.maksab.listener.DialogListener;


/**
 * Created by RWS 6 on 1/2/2017.
 */
public class Utility {

    public static int REQUEST_CODE_LOCATION = 101;

    /**
     * Get new date after converting in local time.
     * @param serverDate Server date.
     * @return Return new date according to device time zone.
     * @throws ParseException Throw parsing exception.
     */
    public static String convertInLocalTime(String serverDate) throws ParseException {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
        try {
            TimeZone utcZone = TimeZone.getTimeZone("UTC");
            sdf.setTimeZone(utcZone);// Set UTC time zone
            Date myDate = sdf.parse(serverDate);
            sdf.setTimeZone(TimeZone.getDefault());// Set device time zone
            strDate = sdf.format(myDate);
            return strDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * Show toast
     * @param context Context object
     * @param message Display message
     */
    public static void showToast(Activity context, String message) {
        Toast ToastMessage = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        ToastMessage.show();
    }

    /**
     * Get login user detail
     *
     * @param context Context object
     * @return Return login user detail
     */
    public static LoginResponse getLoginUserDetail(Context context) {
        String json = PreferenceConnector.readString(context, PreferenceConnector.USER_DATA, "");
        return new Gson().fromJson(json, LoginResponse.class);
    }

    public static String getUserId(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.USER_ID, "");
    }

    public static String getUserName(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.USER_NAME, "");
    }
    public static String getUserPic(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.USER_PIC, "");
    }

    public static String getLoginType(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.LOGIN_TYPE, "");
    }

    public static String getCartCount(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.CART_COUNT, "");
    }

    public static String getCityId(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.CITY, "");
    }
    public static String getCityName(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.CITY_NAME, "");
    }

    public static String getCountry(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.COUNTRY, "");
    }
    public static String getCountryNmae(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.COUNTRY_NAME, "");
    }
    public static String getCountryCode(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.COUNTRY_CODE, "");
    }
    public static String getCountryFlag(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.COUNTRY_FLAG, "");
    }

    public static String getLanguage(Context context) {
        return LanguageUtil.sharedInstance().getLanguage();
    }

    public static String getIsMember(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.IS_MEMBER, "");
    }

    public static String getMaxDiviceCount(Context context) {
        return PreferenceConnector.readString(context, PreferenceConnector.MAX_DIVICE_COUNT, "");
    }

    /* public static boolean isGooglePlayServicesAvailable(Activity activity) {
         GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
         int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
         if (status != ConnectionResult.SUCCESS) {
             if (googleApiAvailability.isUserResolvableError(status)) {
                 googleApiAvailability.getErrorDialog(activity, status, 2404).show();
             }
             return false;
         }
         return true;
     }*/
    public static boolean isEmailAddressValid(String email) {
        boolean isEmailValid = false;
        String strExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern objPattern = Pattern.compile(strExpression, Pattern.CASE_INSENSITIVE);
        Matcher objMatcher = objPattern.matcher(inputStr);
        if (objMatcher.matches()) {
            isEmailValid = true;
        }
        return isEmailValid;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phoneNumber)) {
            if (phoneNumber.length() < 6 || phoneNumber.length() > 13) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);
        return encImage;
    }

    public static Fragment getVisibleFragment(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public static Fragment findFragmentByTag(AppCompatActivity activity, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        return fragmentManager.findFragmentByTag(tag);
    }

    public static void addFragment(AppCompatActivity activity, Fragment fragment, String tag, int id) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment1 = fragmentManager.findFragmentByTag(tag);
        if (fragment1 == null) {
            fragment1 = fragment;
            fragmentTransaction.addToBackStack(tag);
        }
        //  fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right,
        // R.anim.exit_to_left);
        fragmentTransaction.replace(id, fragment1, tag);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public static void removeAllFragment(AppCompatActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    /* public static void addFragment(AppCompatActivity activity, Fragment fragment, String tag, int id) {
         FragmentManager fragmentManager = activity.getSupportFragmentManager();
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         Fragment fragment1 = fragmentManager.findFragmentByTag(tag);
         if (fragment1 == null) {
             fragment1 = fragment;
             fragmentTransaction.addToBackStack(tag);
         }
         //  fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim
         .enter_from_right, R.anim.exit_to_left);
         fragmentTransaction.replace(id, fragment1, tag);
         //FragmentTransaction.commitNow();
         fragmentTransaction.commit();
         fragmentManager.executePendingTransactions();
     }*/
    public static void addFragmentWithBackStack(AppCompatActivity activity, Fragment fragment, String tag, int id, String
            title) {
        // titles.add(title);
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment1 = fragmentManager.findFragmentByTag(tag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left,
        // R.anim.slide_to_right);
        //fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right, R.anim.slide_from_right,
        // R.anim.slide_to_left);
        if (fragment1 != null) {
          /*  if (fragment1 instanceof HomeMainFragment) {
                fragmentTransaction.replace(id, fragment1);
                fragmentTransaction.commit();
            } else if (fragment1 instanceof EditProfileFragment) {
                fragmentTransaction.replace(id, fragment1);
                fragmentTransaction.commit();
            } else {
                fragmentTransaction.replace(id, fragment);
                //    fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            }*/
        } else {
            fragmentTransaction.replace(id, fragment);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        }
    }


    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        /*AQuery aq = new AQuery(this);
// Image URL to download
String url = "http://www.vikispot.com/z/images/vikispot/android-w.png";
ImageOptions options = new ImageOptions();
options.round = 15;
aq.id(R.id.image).image(url, options);*/
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage) throws IOException {
        int MAX_HEIGHT = 512;
        int MAX_WIDTH = 512;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        try {
            img = rotateImageIfRequired(img, selectedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static void setNoInternetPopup(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button button = (Button) dialog.findViewById(R.id.okBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public static void showMsg(final Activity context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public static int getAgeFromDOB(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(date);
        if (dob.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return age;
    }

    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    public static Bitmap fastblur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    public static void setMessage(TextView textView, String msg, int visibility) {
        textView.setText(msg);
        textView.setVisibility(visibility);
    }

    public static void setDialog(Context appContext, String titleStr, String msgStr, String leftStr, String rightStr,
                                 final DialogListener dialogListener) {
        final Dialog dialog = new Dialog(appContext);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView msg = (TextView) dialog.findViewById(R.id.msg);

        title.setText(titleStr);
        msg.setText(msgStr);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cancel_action:
                        dialog.dismiss();
                        dialogListener.onNegative(dialog);
                        break;
                    case R.id.send_action:
                        dialog.dismiss();
                        dialogListener.onPositive(dialog);
                        break;
                }
            }
        };

        Button left = (Button) dialog.findViewById(R.id.cancel_action);
        Button right = (Button) dialog.findViewById(R.id.send_action);

        left.setText(leftStr);
        right.setText(rightStr);

        dialog.findViewById(R.id.cancel_action).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.send_action).setOnClickListener(onClickListener);
        dialog.show();
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static int getDrawableHeight(Activity activity, int drawableId) {
        BitmapDrawable bd = (BitmapDrawable) ContextCompat.getDrawable(activity, drawableId);
        return bd.getBitmap().getHeight();
    }

    public static int getDrawableWidth(Activity activity, int drawableId) {
        BitmapDrawable bd = (BitmapDrawable) ContextCompat.getDrawable(activity, drawableId);
        return bd.getBitmap().getWidth();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void closeKeyboard(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openKeyboard(Context context) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void email(Context context, String emailTo, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String aEmailList[] = {emailTo};
        // String aEmailList[] = {"user@fakehost.com", "user2@fakehost.com"};
        //   String aEmailCCList[] = { "user3@fakehost.com","user4@fakehost.com"};
        //    String aEmailBCCList[] = { "user5@fakehost.com" };

        emailIntent.putExtra(Intent.EXTRA_EMAIL, aEmailList);
        // emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
        //  emailIntent.putExtra(android.content.Intent.EXTRA_BCC, aEmailBCCList);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        try {
            context.startActivity(emailIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "No app found.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void dialNumber(Context context, String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));
        try {
            context.startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "No app found.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void printHashKey(Activity pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo("com.bikespot.android", PackageManager
                    .GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("", "printHashKey()", e);
        }
    }

    public static String getAddressFromLatLng(Context context, double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to
            // returned, by documents it recommended 1 to 5
            if (addresses != null && addresses.size() != 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only,
                // check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                //     String state = addresses.get(0).getAdminArea();
                //    String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                //  String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                StringBuilder stringBuilder = new StringBuilder();
                if (address != null) {
                    stringBuilder.append(address);
                    stringBuilder.append(" ");
                } else if (city != null) {
                    stringBuilder.append(city);
                    stringBuilder.append(" ");
                } else if (postalCode != null) {
                    stringBuilder.append(postalCode);
                }

                return stringBuilder.toString();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isLogin(Context context) {
        if (PreferenceConnector.readString(context, PreferenceConnector.USER_ID, "").isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * check location enabled or not.
     *
     * @param context
     * @return
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure
                    .LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static URL ConvertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveDeviceHeightWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        PreferenceConnector.writeInteger(activity, PreferenceConnector.DEVICE_HEIGHT, height);
        PreferenceConnector.writeInteger(activity, PreferenceConnector.DEVICE_WIDTH, width);
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public static String getUniqueIMEIId() {
        Context context = MyApplication.sharedInstance();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }

            String imei = telephonyManager.getDeviceId();
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "not_found";
    }

    public static String getDeviceToken() {
        return Settings.Secure.getString(MyApplication.sharedInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}