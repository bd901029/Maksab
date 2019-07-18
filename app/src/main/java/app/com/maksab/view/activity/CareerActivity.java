package app.com.maksab.view.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityCareerBinding;
import app.com.maksab.imagepicker.PickerBuilder;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CareerActivity extends AppCompatActivity {

    ActivityCareerBinding mBinding;
    Context context;
    private int mYear, mMonth, mDay;
    String selectedFilePath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_career);
        mBinding.setActivity(this);
        initViews();
    }

    public void onClickBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initViews() {
        mBinding.dobEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        mBinding.submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    submitCareer();
                }
            }
        });
    }

    private boolean isValid() {
        if(TextUtils.isEmpty(mBinding.nameEdt.getText().toString())){
            Utility.showToast(this,"Please enter name");
            return false;
        }else if(TextUtils.isEmpty(mBinding.emailEdt.getText().toString())){
            Utility.showToast(this,"Please enter email");
            return false;
        }else if(TextUtils.isEmpty(mBinding.mobileEdt.getText().toString())){
            Utility.showToast(this,"Please enter mobile number");
            return false;
        }else if(TextUtils.isEmpty(mBinding.nationalityEdt.getText().toString())){
            Utility.showToast(this,"Please enter your nationality");
            return false;
        }else if(TextUtils.isEmpty(mBinding.dobEdt.getText().toString())){
            Utility.showToast(this,"Please enter your date of birth");
            return false;
        }else if(TextUtils.isEmpty(mBinding.jobTitleEdt.getText().toString())){
            Utility.showToast(this,"Please enter job title");
            return false;
        }else{
            return true;
        }
    }

    public void showDatePickerDialog(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mBinding.dobEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void submitCareer() {
        ProgressDialog.getInstance().showProgressDialog(this);
        RequestBody language = RequestBody.create(MediaType.parse("text/plain"), Utility.getLanguage(CareerActivity.this));
        RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), mBinding.nameEdt.getText().toString());
        RequestBody nationality = RequestBody.create(MediaType.parse("text/plain"), mBinding.nationalityEdt.getText().toString());
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), mBinding.dobEdt.getText().toString());
        RequestBody job_title = RequestBody.create(MediaType.parse("text/plain"), mBinding.jobTitleEdt.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mBinding.emailEdt.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), mBinding.mobileEdt.getText().toString());

        MultipartBody.Part fileImg = null;
        if (!TextUtils.isEmpty(selectedFilePath)) {
            File file = new File(selectedFilePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            fileImg = MultipartBody.Part.createFormData("user_img", file.getName(), mFile);
        }

        Api api = APIClient.getClient().create(Api.class);
        final Call<SuccessfulResponse> responseCall = api.careerRequest(fileImg, language, fullname, nationality, dob,
                job_title, email, phone);
        responseCall.enqueue(new Callback<SuccessfulResponse>() {
            @Override
            public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse> response) {
                ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()) {
                    Utility.showToast(CareerActivity.this, response.message() + "");
                }
            }

            @Override
            public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                if (!isDestroyed()) {
                    Utility.showToast(CareerActivity.this, t + "");
                    Log.e("", "onFailure: " + t.getLocalizedMessage());
                }
            }
        });
    }

    public void onClickProfilePic() {
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CareerActivity.this);
        alertDialogBuilder.setTitle(R.string.profile_photos);
        alertDialogBuilder.setMessage(R.string.choose_image_source);
        alertDialogBuilder.setPositiveButton(R.string.gallery, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                pickPhoto(PickerBuilder.SELECT_FROM_GALLERY);
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.camera, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // startActivityForResult(cameraIntent, CAMERA_REQUEST);
                pickPhoto(PickerBuilder.SELECT_FROM_CAMERA);

            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Pick photo
     */
    private void pickPhoto(int from) {
        new PickerBuilder(CareerActivity.this, from)
                .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                    @Override
                    public void onImageReceived(Uri imageUri) {
                        String imageTempName = "";
                        try {
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                            mBinding.profileIv.setImageBitmap(bitmap);
                            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                            Uri tempUri = getImageUri(CareerActivity.this, bitmap, imageTempName);
                            // CALL THIS METHOD TO GET THE FILE PATH FROM THE URI
                            selectedFilePath = getRealPathFromURI(tempUri);
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
                                            /*i Would like to inform you that Wednesday is our festival onam. Kindly allow me take one day leave.*/
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
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
