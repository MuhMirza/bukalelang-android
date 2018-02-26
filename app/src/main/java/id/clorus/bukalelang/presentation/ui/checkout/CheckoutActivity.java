package id.clorus.bukalelang.presentation.ui.checkout;

import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.CheckOutResultData;
import id.clorus.bukalelang.data.entity.response.checkout.CheckOutData;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.HomeActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 06/06/17.
 */

public class CheckoutActivity extends DefaultActivity {

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.product_name)
    TextView productName;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.area)
    TextView area;

    @BindView(R.id.city)
    TextView city;

    @BindView(R.id.province)
    TextView province;

    @BindView(R.id.post_code)
    TextView postalCode;

    @BindView(R.id.shipping)
    TextView shipping;

    @BindView(R.id.product_price)
    TextView productPrice;

    @BindView(R.id.shipping_price)
    TextView shippingPrice;

    @BindView(R.id.total_price)
    TextView totalPrice;

    Bundle bundle;

    int addressId;
    int courierFee;
    String courierService;
    int auctionId;
    AppPreference appPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        appPreference = Esperandro.getPreferences(AppPreference.class,this);

        initcheckout(bundle.getInt("id"));
        auctionId = bundle.getInt("id");


    }

    public void initcheckout(int auctionID){
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        RestService.Factory.getInstance().getCheckout("auctions/"+String.valueOf(auctionID)+"/checkout-information").enqueue(new Callback<CheckOutData>() {
            @Override
            public void onResponse(Call<CheckOutData> call, Response<CheckOutData> response) {
                name.setText(response.body().getWinnerName());
                productName.setText(response.body().getAuction().getTitle());


                for (int i = 0; i < response.body().getAddresses().size();i++){
                    if (response.body().getAddresses().get(i).getIsPrimary() == 1){
                        address.setText(response.body().getAddresses().get(i).getAddressAttributes().getAddress());
                        area.setText(response.body().getAddresses().get(i).getAddressAttributes().getArea());
                        city.setText(response.body().getAddresses().get(i).getAddressAttributes().getCity());
                        province.setText(response.body().getAddresses().get(i).getAddressAttributes().getProvince());
                        postalCode.setText(response.body().getAddresses().get(i).getAddressAttributes().getPostCode());

                        addressId = response.body().getAddresses().get(i).getId();

                    }

                }

                int totalPriceInt = response.body().getShipping().get(0).getCouriers().get(0).getPrice() + response.body().getFinalPrice();
                totalPrice.setText(formatRupiah.format(totalPriceInt));
                shipping.setText(response.body().getShipping().get(0).getCouriers().get(0).getService()+" ("+response.body().getShipping().get(0).getCouriers().get(0).getEta()+" HARI KERJA) "+formatRupiah.format(response.body().getShipping().get(0).getCouriers().get(0).getPrice()));
                productPrice.setText(formatRupiah.format(response.body().getFinalPrice()));
                shippingPrice.setText(formatRupiah.format(response.body().getShipping().get(0).getCouriers().get(0).getPrice()));

                courierFee = response.body().getShipping().get(0).getCouriers().get(0).getPrice();
                courierService = response.body().getShipping().get(0).getCouriers().get(0).getService();


            }

            @Override
            public void onFailure(Call<CheckOutData> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btn_ship_now)
    public void checkOutProcess(){
        RestService.Factory.getInstance().checkout(appPreference.id(),appPreference.bukalapakId(),appPreference.accessToken(),auctionId,courierService,courierFee,addressId).enqueue(new Callback<CheckOutResultData>() {
            @Override
            public void onResponse(Call<CheckOutResultData> call, Response<CheckOutResultData> response) {
                showToast(response.body().getMessage());
                changeActivity(HomeActivity.class);
            }

            @Override
            public void onFailure(Call<CheckOutResultData> call, Throwable t) {

            }
        });
    }
}
