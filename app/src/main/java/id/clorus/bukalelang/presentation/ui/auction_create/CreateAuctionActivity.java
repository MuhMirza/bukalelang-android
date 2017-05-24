package id.clorus.bukalelang.presentation.ui.auction_create;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.karthyks.runtimepermissions.Permission;
import com.github.karthyks.runtimepermissions.PermissionActivity;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.CreateAuctionData;
import id.clorus.bukalelang.data.entity.response.UploadImageData;
import id.clorus.bukalelang.presentation.config.AppConfig;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.ImageCaptureUtil;
import id.clorus.bukalelang.presentation.utils.ImageCompressorUtil;

/**
 * Created by mirza on 24/05/17.
 */

public class CreateAuctionActivity extends DefaultActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener,CreateAuctionView {

    @BindView(R.id.rg_product_condition)
    RadioGroup productCondition;

    @BindView(R.id.date_time_picker)
    TextView dateTimePicker;

    @BindView(R.id.btn_upload_img)
    ImageView imgView;

    static String date;
    static String time;

    CreateAuctionPresenter presenter;
    private int PERMISSION_CODE = 111;

    List<String> imageIdList;
    List<String> imagePathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_create);
        ButterKnife.bind(this);

        presenter = new CreateAuctionPresenter(this,this);
        imageIdList = new ArrayList<>();
        imagePathList = new ArrayList<>();

        int status = productCondition.getCheckedRadioButtonId();
        switch (status){
            case R.id.rb_new :
                break;
            case R.id.rb_second :
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year+"-"+(++monthOfYear)+"-"+dayOfMonth;
        pickTime();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        time = "T"+hourString+":"+minuteString+":"+secondString+"+07:00";

        dateTimePicker.setText(date+time);
    }

    @OnClick(R.id.date_time_picker)
    public void pickDate(){

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.vibrate(true);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.show(getFragmentManager(),"");
    }

    public void pickTime(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),true
        );
        tpd.vibrate(true);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.show(getFragmentManager(),"");
    }

    @OnClick(R.id.btn_upload_img)
    public void uploadPhoto(){

        checkPermission();

        try {
            ImageCaptureUtil.createSelectImageCaptureDialog(this,"profile");
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

                Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_STORAGE_AND_CAMERA)
                        .usingActivity(this).withRationale("This app requires access storage and camera")
                        .build();
                permission.requestPermission(PERMISSION_CODE);

            }
        }

    }


    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e)
        {
            return contentUri.getPath();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final Bitmap bitmap;

        if (resultCode == Activity.RESULT_OK) {

            String pictureLocation;

            if (requestCode == AppConfig.SELECT_PHOTO_GALLERY_REQUEST_CODE){
                pictureLocation = getRealPathFromURI(data.getData());

            } else {
                bitmap = ImageCaptureUtil.getBitmapFromResultData(this, requestCode, data);
                pictureLocation = ImageCaptureUtil.saveImageToInternalStorage(bitmap, AppConfig.PROFILE_PICTURE_NAME, this);
            }

            ImageCompressorUtil compressorUtil = new ImageCompressorUtil(this,pictureLocation);
            compressorUtil.setCallback(new ImageCompressorUtil.Callback() {
                @Override
                public void onFinishCompress(Uri imageUri) {
                    String path = getRealPathFromURI(imageUri);
                    presenter.uploadPhoto(path);
                }
            });


        }

        if (requestCode == PERMISSION_CODE) {
            switch (resultCode) {
                case PermissionActivity.PERMISSION_GRANTED:
                    ImageCaptureUtil.createSelectImageCaptureDialog(this,"profile");
                    break;
                case PermissionActivity.PERMISSION_DENIED:
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionActivity.PERMISSION_PERMANENTLY_DENIED:
                    Toast.makeText(this, "Permanently denied", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onFinishUploadImage(UploadImageData data,String imagePath) {
        showToast(String.valueOf(data.getId())+data.getMessage());
        imageIdList.add(String.valueOf(data.getId()));
        imagePathList.add(imagePath);

        Uri uri=Uri.fromFile(new File(imagePath));

        Log.d("path",imagePath);
        Picasso.with(this)
                .load(uri)
                .error(R.color.red)
                .placeholder(R.color.grey_dark)
                .into(imgView);
    }

    @Override
    public void onCreateAuctionComplete(CreateAuctionData data) {

    }


}
