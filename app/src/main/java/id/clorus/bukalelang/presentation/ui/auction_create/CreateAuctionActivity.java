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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.karthyks.runtimepermissions.Permission;
import com.github.karthyks.runtimepermissions.PermissionActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.CreateAuctionData;
import id.clorus.bukalelang.data.entity.response.UploadImageData;
import id.clorus.bukalelang.data.entity.response.categories.Category;
import id.clorus.bukalelang.presentation.config.AppConfig;
import id.clorus.bukalelang.presentation.model.AuctionPhoto;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.HomeActivity;
import id.clorus.bukalelang.presentation.ui.select_category.SelectCategoryActivity;
import id.clorus.bukalelang.presentation.utils.ImageCaptureUtil;
import id.clorus.bukalelang.presentation.utils.ImageCompressorUtil;
import id.clorus.bukalelang.presentation.utils.StringUtil;

/**
 * Created by mirza on 24/05/17.
 */

public class CreateAuctionActivity extends DefaultActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener,CreateAuctionView {

    @BindView(R.id.rg_product_condition)
    RadioGroup productCondition;

    @BindView(R.id.date_time_view)
    TextView dateTimeView;

    @BindView(R.id.input_title)
    TextView inputTitle;

    @BindView(R.id.input_description)
    TextView inputDescription;

    @BindView(R.id.input_weight)
    TextView inputWeight;

    @BindView(R.id.input_open_bid_price)
    TextView inputPriceOpenBid;

    @BindView(R.id.input_bin_price)
    TextView inputPriceBin;

    @BindView(R.id.input_kelipatan_bid)
    EditText inputKelipatanBid;


    @BindView(R.id.category_name)
    TextView tvCategoryName;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private AuctionPhotoAdapter adapter;
    private LinearLayoutManager layoutManager;
    ArrayList<AuctionPhoto> photos;

    static String date;
    static String time;

    CreateAuctionPresenter presenter;
    private int PERMISSION_CODE = 111;
    private int SELECT_CATEGORY_CODE = 212;

    String arrayPhotos;
    String dateTime;
    String isNew;

    List<String> imageIdList;
    List<String> imagePathList;

    Category categorySelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_create);
        ButterKnife.bind(this);

        presenter = new CreateAuctionPresenter(this,this);
        imageIdList = new ArrayList<>();
        imagePathList = new ArrayList<>();
        categorySelected = new Category();

        photos = new ArrayList<>();
        initRecyclerView();

        int status = productCondition.getCheckedRadioButtonId();
        switch (status){
            case R.id.rb_new :
                isNew = "true";
                break;
            case R.id.rb_second :
                isNew = "false";
                break;
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year ;

        pickTime();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        time = hourString+":"+minuteString;
        dateTimeView.setText(date+time);
        StringBuilder str = new StringBuilder();
        str.append(date).append(time);
        dateTime = str.toString();
    }


    @OnClick(R.id.btn_set_date)
    public void pickDate(){

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setMinDate(now);
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
//        tpd.setMinTime(now.getTimeInMillis());
        tpd.vibrate(true);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.show(getFragmentManager(),"");
    }

    @OnClick(R.id.btn_select_category)
    public void selectCategory(){
        Intent intent = new Intent(CreateAuctionActivity.this, SelectCategoryActivity.class);
        startActivityForResult(intent, SELECT_CATEGORY_CODE);

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

    @OnClick(R.id.btn_publish)
    public void publish(){

        if ((StringUtil.isNullOrEmpty(inputTitle.getText().toString())) || (StringUtil.isNullOrEmpty(inputDescription.getText().toString())) || (StringUtil.isNullOrEmpty(inputWeight.getText().toString()))
                || (StringUtil.isNullOrEmpty(inputPriceOpenBid.getText().toString())) || (StringUtil.isNullOrEmpty(inputPriceBin.getText().toString())) || (StringUtil.isNullOrEmpty(inputKelipatanBid.getText().toString())) )
        {
            showToast("Tolong isi form dengan lengkap!");
            return;
        }

        if (inputDescription.getText().toString().length() < 30){
            showToast("Tolong berikan deskripsi lebih dari 30 kata");
            return;
        }

        if ((Integer.parseInt(inputKelipatanBid.getText().toString()) % 1000) != 0  ){
            showToast("Pastikan Kelipatan Bid Rp.1000 ");
            return;
        }

        if (categorySelected.getId() == 0){
            showToast("Tolong pilih kategori terlebih dahulu");
            return;
        }

        if (StringUtil.isNullOrEmpty(arrayPhotos)){
            showToast("Tolong upload photo barang terlebih dahulu");
            return;
        }

        if (StringUtil.isNullOrEmpty(dateTime)){
            showToast("Tolong masukan batas akhir lelang");
            return;
        }

        try {

            presenter.createAuctionRequest(inputTitle.getText().toString().toUpperCase(),categorySelected.getId(),isNew,Integer.parseInt(inputWeight.getText().toString()),
                    inputDescription.getText().toString(),Integer.parseInt(inputPriceOpenBid.getText().toString()),Integer.parseInt(inputPriceBin.getText().toString()),Integer.parseInt(inputKelipatanBid.getText().toString()),arrayPhotos,dateTime);


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new AuctionPhotoAdapter(this, photos,this);
        recyclerView.setAdapter(adapter);

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

                ImageCompressorUtil compressorUtil = new ImageCompressorUtil(this,pictureLocation);
                compressorUtil.setCallback(new ImageCompressorUtil.Callback() {
                    @Override
                    public void onFinishCompress(Uri imageUri) {
                        String path = getRealPathFromURI(imageUri);
                        Log.d("path photo",path);
                        presenter.uploadPhoto(path);
                    }
                });

            } else {
                bitmap = ImageCaptureUtil.getBitmapFromResultData(this, requestCode, data);
                pictureLocation = ImageCaptureUtil.saveImageToInternalStorage(bitmap, AppConfig.PROFILE_PICTURE_NAME, this);
                presenter.uploadPhoto(pictureLocation);
            }



        }

        if (requestCode == SELECT_CATEGORY_CODE) {

            try {

                String categoryName = data.getStringExtra("categoryName");
                int categoryId = data.getIntExtra("categoryId",0);
                String categoryUrl = data.getStringExtra("categoryUrl");

                categorySelected.setName(categoryName);
                categorySelected.setId(categoryId);
                categorySelected.setUrl(categoryUrl);
                tvCategoryName.setText(categorySelected.getName());

            } catch (Exception e){
                e.printStackTrace();
            }

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

    }

    @Override
    public void onFinishUploadImage(AuctionPhoto data) {

        adapter.addItem(data,photos.size());
        StringBuilder str = new StringBuilder();
        for (AuctionPhoto photo : photos){
            str.append(photo.getId()).append(",");
        }

        arrayPhotos = str.toString();
        Log.d("photos",str.toString());

    }


    @Override
    public void onCreateAuctionComplete(CreateAuctionData data) {

        showToast(data.getMessage());

        Log.d("message",data.getMessage());

        Intent intent = new Intent(CreateAuctionActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();


    }

    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
