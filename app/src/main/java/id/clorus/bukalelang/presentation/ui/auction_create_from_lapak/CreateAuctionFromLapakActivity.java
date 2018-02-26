package id.clorus.bukalelang.presentation.ui.auction_create_from_lapak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.karthyks.runtimepermissions.PermissionActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.CreateAuctionData;
import id.clorus.bukalelang.data.entity.response.UploadImageData;
import id.clorus.bukalelang.data.entity.response.categories.Category;
import id.clorus.bukalelang.presentation.model.AuctionPhoto;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.HomeActivity;
import id.clorus.bukalelang.presentation.utils.ImageCaptureUtil;
import id.clorus.bukalelang.presentation.utils.StringUtil;

/**
 * Created by mirza on 24/05/17.
 */

public class CreateAuctionFromLapakActivity extends DefaultActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener,CreateAuctionView {

    @BindView(R.id.date_time_view)
    TextView dateTimeView;

    @BindView(R.id.input_open_bid_price)
    EditText inputPriceOpenBid;

    @BindView(R.id.input_kelipatan_bid)
    EditText inputKelipatanBid;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.product_name)
    TextView productName;

    @BindView(R.id.category_name)
    TextView categoryName;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.condition)
    TextView condition;

    @BindView(R.id.weight)
    TextView productWeight;

    @BindView(R.id.bin_price)
    TextView productPriceBin;

    private AuctionPhotoFromLapakAdapter adapter;
    private LinearLayoutManager layoutManager;
    ArrayList<AuctionPhoto> photos;

    static String date;
    static String time;

    CreateAuctionFromLapakPresenter presenter;
    private int PERMISSION_CODE = 111;
    private int SELECT_CATEGORY_CODE = 212;

    String dateTime;

    List<String> listPhotos;

    Category categorySelected;

    Bundle bundle;

    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_create_from_lapak);
        ButterKnife.bind(this);

        presenter = new CreateAuctionFromLapakPresenter(this,this);
//        listPhotos = new ArrayList<>();

        photos = new ArrayList<>();
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        bundle = getIntent().getExtras();
        if (bundle != null){
            productName.setText(bundle.getString("name"));
            categoryName.setText(bundle.getString("categoryName"));
            description.setText(bundle.getString("description"));
            condition.setText(bundle.getString("condition"));
            productWeight.setText(String.valueOf(bundle.getInt("weight")));

            productPriceBin.setText(formatRupiah.format(bundle.getInt("bin")));
            productId = bundle.getString("id");

            String images = bundle.getString("images");
            images = images.replaceAll("\\s", "");
            String[] items = images.split(",");
            listPhotos = Arrays.asList(items);
            for (String photo : listPhotos) {
                Log.d("photo", photo);
            }
        }


        initRecyclerView();


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



    @OnClick(R.id.btn_publish)
    public void publish(){

        if ((StringUtil.isNullOrEmpty(inputPriceOpenBid.getText().toString())) || (StringUtil.isNullOrEmpty(inputKelipatanBid.getText().toString())) )
        {
            showToast("Tolong isi form dengan lengkap!");
            return;
        }

        if ((Integer.parseInt(inputKelipatanBid.getText().toString()) % 1000) != 0  ){
            showToast("Pastikan Kelipatan Bid Rp.1000 ");
            return;
        }

        if (StringUtil.isNullOrEmpty(dateTime)){
            showToast("Tolong masukan batas akhir lelang");
            return;
        }

        try {
            presenter.createAuctionRequest(productId,Integer.parseInt(inputPriceOpenBid.getText().toString()),Integer.parseInt(inputKelipatanBid.getText().toString()),dateTime );
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
        adapter = new AuctionPhotoFromLapakAdapter(this, listPhotos,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

    }


    @Override
    public void onCreateAuctionComplete(CreateAuctionData data) {

        showToast(data.getMessage());

        Log.d("message",data.getMessage());

        Intent intent = new Intent(CreateAuctionFromLapakActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();


    }

    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
