package app.com.maksab.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.LoginResponse;
import app.com.maksab.api.dao.ProfileResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityProfileEditBinding;
import app.com.maksab.databinding.DialogOtpBinding;
import app.com.maksab.databinding.FragmentProfileEditBinding;
import app.com.maksab.imagepicker.PickerBuilder;
import app.com.maksab.util.Extension;
import app.com.maksab.util.PreferenceConnector;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.util.ValidationTemplate;
import app.com.maksab.view.viewmodel.UpdateProfileDataModel;
import app.com.maksab.view.viewmodel.UserIdMobileModel;
import app.com.maksab.view.viewmodel.UserIdOTPModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileEditActivity extends AppCompatActivity {

    public ProfileEditActivity() {
        // Required empty public constructor
    }

    private ActivityProfileEditBinding fragmentBinding;
    private final static String STORE_TYPE_ID = "store_type_id";
    private ProfileResponse profileResponse = null;
    String selectedFilePath = "";
    private Dialog dialog;
    private DialogOtpBinding dialogOtpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        fragmentBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit);
        fragmentBinding.setActivity(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void onClickProfilePic() {
        new Permissive.Request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        profilePic();
                    }
                })
                .whenPermissionsRefused(new PermissionsRefusedListener() {
                    @Override
                    public void onPermissionsRefused(String[] permissions) {
                        Utility.showToast(ProfileEditActivity.this, "Need permissions.");
                    }
                }).execute(ProfileEditActivity.this);
    }

    public void profilePic() {
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder
                (ProfileEditActivity.this);
        alertDialogBuilder.setTitle(R.string.profile_photos);
        alertDialogBuilder.setMessage(R.string.choose_image_source);
        alertDialogBuilder.setPositiveButton(R.string.gallery, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                pickPhoto(PickerBuilder.SELECT_FROM_GALLERY);

            }
        });

        alertDialogBuilder.setNegativeButton(R.string.camera, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    pickPhoto(PickerBuilder.SELECT_FROM_CAMERA);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void pickPhoto(int from) {
        new PickerBuilder(ProfileEditActivity.this, from)
                .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                    @Override
                    public void onImageReceived(Uri imageUri) {
                        String imageTempName = "";
                        try {
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                            fragmentBinding.ivUserAvatar.setImageBitmap(bitmap);

                            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                            Uri tempUri = getImageUri(ProfileEditActivity.this, bitmap, imageTempName);
                            // CALL THIS METHOD TO GET THE FILE PATH FROM THE URI
                            selectedFilePath = getRealPathFromURI(tempUri);

                            profilePicUpdate();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //selectedFilePath = Utility.encodeImage(bitmap);

                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //activityBinding.imageViewProfile.setImageBitmap(bitmap);
                                            //setProfilePicture(new Gson().toJson(new SetProfilePictureModel(
                                            //  strImagePath, strUserId)));
                                        }
                                    });
                                }
                            }).start();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setImageName("image")
                .setImageFolderName("MiniMall")
                .withTimeStamp(true)
                .setAspectRatio(1, 1)
                .start();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = ProfileEditActivity.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void profilePicUpdate() {
        if (!TextUtils.isEmpty(selectedFilePath)) {
            ProgressDialog.getInstance().showProgressDialog(ProfileEditActivity.this);
            RequestBody storeId = RequestBody.create(MediaType.parse("text/plain"), Utility.getUserId(ProfileEditActivity.this));
            RequestBody language = RequestBody.create(MediaType.parse("text/plain"), Utility.getLanguage(ProfileEditActivity.this));
            File file = new File(selectedFilePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image"), file);
            MultipartBody.Part user_img = MultipartBody.Part.createFormData("user_img", file.getName(), mFile);
            Api api = APIClient.getClient().create(Api.class);
            Call<LoginResponse> fileUpload = api.editProfilePic(user_img,language ,storeId);
            fileUpload.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        handleResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    Toast.makeText(ProfileEditActivity.this, t + "", Toast.LENGTH_SHORT).show();
                    Log.d("", "Error " + t.getMessage());
                }
            });
        }
    }

    private void handleResponse(LoginResponse profileResponse) {
        if (profileResponse != null) {
            Toast.makeText(ProfileEditActivity.this, profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
            if (profileResponse.getResponseCode() != null && profileResponse.getResponseCode().equals(Api.SUCCESS)) {
                PreferenceConnector.writeString(ProfileEditActivity.this, PreferenceConnector.USER_PIC,
                        profileResponse.getProfilePic());
                PreferenceConnector.writeString(ProfileEditActivity.this, PreferenceConnector.USER_NAME,
                        profileResponse.getUserName());
               // ((HomeActivity)ProfileEditActivity.this).UpdateProfilePic();
            }
        }
    }

    /**
     * Check string empty or not
     * @param str string to check
     * @return true if empty else false
     */
    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * Validate registration form
     * @param profileResponse Registration model
     * @return true if valid else false
     */
    private boolean validate(ProfileResponse profileResponse) {
        Extension extension = Extension.getInstance();
        if (isEmpty(profileResponse.getUserName()) || isEmpty(profileResponse.getEmail()) || isEmpty(profileResponse
                .getMobile())) {
            if (profileResponse.getUserName().isEmpty()) {
                fragmentBinding.nameInputLayout.setError(getText(R.string.error_f_name));
            }
            if (profileResponse.getEmail().isEmpty()) {
                fragmentBinding.emailInputLayout.setError(getText(R.string.error_email));
            }
            if (profileResponse.getMobile().isEmpty()) {
                fragmentBinding.mobileInputLayout.setError(getText(R.string.error_phone));
            }
            return false;
        } else if (!extension.executeStrategy(ProfileEditActivity.this, profileResponse.getEmail(),
                ValidationTemplate.EMAIL)) {
            Utility.showToast(ProfileEditActivity.this, getString(R.string.invalid_email));
            return false;
        } else if (!extension.executeStrategy(ProfileEditActivity.this, "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(ProfileEditActivity.this);
            return false;
        } else {
            return true;
        }
    }

    public void UpdateProfileData(final ProfileResponse profileResponse) {
        if (validate(profileResponse)) {
            ProgressDialog.getInstance().showProgressDialog(ProfileEditActivity.this);
            UpdateProfileDataModel updateProfileDataModel = new UpdateProfileDataModel();
            updateProfileDataModel.setUserId(Utility.getUserId(ProfileEditActivity.this));
            updateProfileDataModel.setLanguage(Utility.getLanguage(ProfileEditActivity.this));
            updateProfileDataModel.setUserName(profileResponse.getUserName());
            updateProfileDataModel.setEmail(profileResponse.getEmail());
            updateProfileDataModel.setMobile(profileResponse.getMobile());

            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> responseCall = api.editProfile(updateProfileDataModel);
            responseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse>
                        response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        handleResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        Utility.showToast(ProfileEditActivity.this, t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }

    }

    private void handleResponse(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
            if (successfulResponse.getResponseCode() != null && successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(ProfileEditActivity.this, successfulResponse.getMessage() + "");
                ProfileEditActivity.this.onBackPressed();
            }
        }
    }

    /*Send Mobile For Verification*/
    public void VerifyMobileNumber(final ProfileResponse profileResponse) {
        if (profileResponse.getMobile().isEmpty()) {
            fragmentBinding.mobileInputLayout.setError(getText(R.string.error_phone));
        }else {
            ProgressDialog.getInstance().showProgressDialog(ProfileEditActivity.this);
            UserIdMobileModel userIdMobileModel = new UserIdMobileModel();
            userIdMobileModel.setUserId(Utility.getUserId(ProfileEditActivity.this));
            userIdMobileModel.setLanguage(Utility.getLanguage(ProfileEditActivity.this));
            userIdMobileModel.setMobileNumber(profileResponse.getMobile());
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> responseCall = api.sendMobileVerification(userIdMobileModel);
            responseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse>
                        response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        handleResponseVerifyMobile(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        Utility.showToast(ProfileEditActivity.this, t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }

    }

    private void handleResponseVerifyMobile(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
            if (successfulResponse.getResponseCode() != null && successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(ProfileEditActivity.this, successfulResponse.getMessage() + "");
                onClickOTP();
            }
        }
    }

    public void onClickOTP() {
        dialog = new Dialog(ProfileEditActivity.this);
        dialogOtpBinding = DataBindingUtil.inflate(LayoutInflater.from(ProfileEditActivity.this),
                R.layout.dialog_otp, null, false);
        dialog.setContentView(dialogOtpBinding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager
                .LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogOtpBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogOtpBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogOtpBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogOtpBinding.etOTP.getText().toString().isEmpty()) {
                    dialogOtpBinding.etOTP.setError(getText(R.string.hint_note));
                } else {
                    VerifyMobileOTP(dialogOtpBinding.etOTP.getText().toString());
                }
            }
        });
        dialog.show();
    }


    /*Check OPT*/
    public void VerifyMobileOTP(String sOTP) {
        if (profileResponse.getUserName().isEmpty()) {
            fragmentBinding.nameInputLayout.setError(getText(R.string.error_f_name));
        }else {
            ProgressDialog.getInstance().showProgressDialog(ProfileEditActivity.this);
            UserIdOTPModel userIdOTPModel = new UserIdOTPModel();
            userIdOTPModel.setUserId(Utility.getUserId(ProfileEditActivity.this));
            userIdOTPModel.setLanguage(Utility.getLanguage(ProfileEditActivity.this));
            userIdOTPModel.setOTP(sOTP);
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> responseCall = api.checkVerificationCode(userIdOTPModel);
            responseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse>
                        response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        handleResponseVerifyMobileOTP(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (!isDestroyed()) {
                        Utility.showToast(ProfileEditActivity.this, t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }

    }

    private void handleResponseVerifyMobileOTP(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
            if (successfulResponse.getResponseCode() != null && successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(ProfileEditActivity.this, successfulResponse.getMessage() + "");
                dialog.dismiss();
            }
        }
    }


}

