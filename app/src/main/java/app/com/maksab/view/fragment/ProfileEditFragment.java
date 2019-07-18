package app.com.maksab.view.fragment;

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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.LoginResponse;
import app.com.maksab.api.dao.ProfileResponse;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.DialogOtpBinding;
import app.com.maksab.databinding.FragmentProfileEditBinding;
import app.com.maksab.imagepicker.PickerBuilder;
import app.com.maksab.util.*;
import app.com.maksab.view.activity.HomeActivity;
import app.com.maksab.view.viewmodel.UpdateProfileDataModel;
import app.com.maksab.view.viewmodel.UserIdMobileModel;
import app.com.maksab.view.viewmodel.UserIdOTPModel;
import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileEditFragment extends Fragment {
    public ProfileEditFragment() {
        // Required empty public constructor
    }
    private FragmentProfileEditBinding fragmentBinding;
    private final static String STORE_TYPE_ID = "store_type_id";
    private ProfileResponse profileResponse = null;
    String selectedFilePath = "";
    private Dialog dialog;
    private DialogOtpBinding dialogOtpBinding;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private int PICK_IMAGE_REQUEST = 1;
    private int ckPosition = -1;

    public static ProfileEditFragment newInstance(ProfileResponse profileResponse) {
        Bundle args = new Bundle();
        ProfileEditFragment fragment = new ProfileEditFragment();
        args.putParcelable(STORE_TYPE_ID, profileResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Bundle bundle = getArguments();
        if (bundle != null) {
            profileResponse = bundle.getParcelable(STORE_TYPE_ID);
        } else {
            Utility.showToast(getActivity(), getString(R.string.wrong));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_profile_edit,
                container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.setFragment(this);
        fragmentBinding.setModel(profileResponse);
        try {
            if (profileResponse.getMobileVerify().equalsIgnoreCase("1"))
                fragmentBinding.verifyMobile.setText(R.string.verified);
            if (profileResponse.getEmailVerify().equalsIgnoreCase("1"))
                fragmentBinding.verifyEmail.setText(R.string.verified);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        Utility.showToast(getActivity(), "Need permissions.");
                    }
                }).execute(getActivity());
    }

    public void profilePic() {
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder
                (getActivity());
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
        new PickerBuilder(getActivity(), from)
                .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                    @Override
                    public void onImageReceived(Uri imageUri) {
                        String imageTempName = "";
                        try {
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                            fragmentBinding.ivUserAvatar.setImageBitmap(bitmap);
                            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                            Uri tempUri = getImageUri(getActivity(), bitmap, imageTempName);
                            // CALL THIS METHOD TO GET THE FILE PATH FROM THE URI
                            selectedFilePath = getRealPathFromURI(tempUri);
                            profilePicUpdate();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
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
                .setImageFolderName("Maksab")
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
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void profilePicUpdate() {
        if (!TextUtils.isEmpty(selectedFilePath)) {
            ProgressDialog.getInstance().showProgressDialog(getActivity());
            RequestBody storeId = RequestBody.create(MediaType.parse("text/plain"), Utility.getUserId(getActivity()));
            RequestBody language = RequestBody.create(MediaType.parse("text/plain"), Utility.getLanguage(getActivity()));
            File file = new File(selectedFilePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image"), file);
            MultipartBody.Part user_img = MultipartBody.Part.createFormData("user_img", file.getName(), mFile);
            Api api = APIClient.getClient().create(Api.class);
            Call<LoginResponse> fileUpload = api.editProfilePic(user_img,language ,storeId);
            fileUpload.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        handleResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    Toast.makeText(getActivity(), t + "", Toast.LENGTH_SHORT).show();
                    Log.d("", "Error " + t.getMessage());
                }
            });
        }
    }

    private void handleResponse(LoginResponse profileResponse) {
        if (profileResponse != null) {
            Toast.makeText(getActivity(), profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
            if (profileResponse.getResponseCode() != null && profileResponse.getResponseCode().equals(Api.SUCCESS)) {
                PreferenceConnector.writeString(getActivity(), PreferenceConnector.USER_PIC,
                        profileResponse.getProfilePic());
                PreferenceConnector.writeString(getActivity(), PreferenceConnector.USER_NAME,
                        profileResponse.getUserName());
                ((HomeActivity)getActivity()).UpdateProfilePic();
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
        if (isEmpty(fragmentBinding.name.getText().toString()) || isEmpty(fragmentBinding.mobile.getText().toString())
                || isEmpty(fragmentBinding.emailEdt.getText().toString())) {
            if (fragmentBinding.name.getText().toString().isEmpty()) {
                fragmentBinding.nameInputLayout.setError(getText(R.string.error_f_name));
            }
            if (fragmentBinding.emailEdt.getText().toString().isEmpty()) {
                fragmentBinding.emailInputLayout.setError(getText(R.string.error_email));
            }
            if (fragmentBinding.mobile.getText().toString().isEmpty()) {
                fragmentBinding.mobileInputLayout.setError(getText(R.string.error_phone));
            }
            return false;
        } else if (!extension.executeStrategy(getActivity(), fragmentBinding.emailEdt.getText().toString(),
                ValidationTemplate.EMAIL)) {
            Utility.showToast(getActivity(), getString(R.string.invalid_email));
            return false;
        } else if (!extension.executeStrategy(getActivity(), "", ValidationTemplate.INTERNET)) {
            Utility.setNoInternetPopup(getActivity());
            return false;
        } else {
            return true;
        }
    }

    public void UpdateProfileData(final ProfileResponse profileResponse) {
        if (validate(profileResponse)) {
            ProgressDialog.getInstance().showProgressDialog(getActivity());
            UpdateProfileDataModel updateProfileDataModel = new UpdateProfileDataModel();
            updateProfileDataModel.setUserId(Utility.getUserId(getActivity()));
            updateProfileDataModel.setLanguage(Utility.getLanguage(getActivity()));
            updateProfileDataModel.setUserName(fragmentBinding.name.getText().toString());
            updateProfileDataModel.setEmail(fragmentBinding.emailEdt.getText().toString());
            updateProfileDataModel.setMobile(fragmentBinding.mobile.getText().toString());
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> responseCall = api.editProfile(updateProfileDataModel);
            responseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
                        response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        handleResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        Utility.showToast(getActivity(), t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void handleResponse(SuccessfulResponse successfulResponse) {
        try {
            if (successfulResponse != null) {
                Utility.showToast(getActivity(), successfulResponse.getMessage() + "");
                if (successfulResponse.getResponseCode() != null && successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                    getActivity().onBackPressed();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Send Mobile For Verification*/
    public void VerifyMobileNumber(final ProfileResponse profileResponse) {
        if (profileResponse.getMobile().isEmpty()) {
            fragmentBinding.mobileInputLayout.setError(getText(R.string.error_phone));
        }else {
            ProgressDialog.getInstance().showProgressDialog(getActivity());
            UserIdMobileModel userIdMobileModel = new UserIdMobileModel();
            userIdMobileModel.setUserId(Utility.getUserId(getActivity()));
            userIdMobileModel.setLanguage(Utility.getLanguage(getActivity()));
            userIdMobileModel.setMobileNumber(profileResponse.getMobile());
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> responseCall = api.sendMobileVerification(userIdMobileModel);
            responseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
                        response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        handleResponseVerifyMobile(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        Utility.showToast(getActivity(), t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void handleResponseVerifyMobile(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
            if (successfulResponse.getResponseCode() != null && successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(getActivity(), successfulResponse.getMessage() + "");
                onClickOTP();
            }
        }
    }

    public void onClickOTP() {
        dialog = new Dialog(getActivity());
        dialogOtpBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
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
            ProgressDialog.getInstance().showProgressDialog(getActivity());
            UserIdOTPModel userIdOTPModel = new UserIdOTPModel();
            userIdOTPModel.setUserId(Utility.getUserId(getActivity()));
            userIdOTPModel.setLanguage(Utility.getLanguage(getActivity()));
            userIdOTPModel.setOTP(sOTP);
            Api api = APIClient.getClient().create(Api.class);
            final Call<SuccessfulResponse> responseCall = api.checkVerificationCode(userIdOTPModel);
            responseCall.enqueue(new Callback<SuccessfulResponse>() {
                @Override
                public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
                        response) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        handleResponseVerifyMobileOTP(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                    ProgressDialog.getInstance().dismissDialog();
                    if (getActivity() != null && isVisible()) {
                        Utility.showToast(getActivity(), t + "");
                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void handleResponseVerifyMobileOTP(SuccessfulResponse successfulResponse) {
        if (successfulResponse != null) {
            if (successfulResponse.getResponseCode() != null && successfulResponse.getResponseCode().equals(Api.SUCCESS)) {
                Utility.showToast(getActivity(), successfulResponse.getMessage() + "");
                dialog.dismiss();
            }
        }
    }
}

