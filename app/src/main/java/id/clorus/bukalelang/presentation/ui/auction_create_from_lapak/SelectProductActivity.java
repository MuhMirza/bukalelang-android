package id.clorus.bukalelang.presentation.ui.auction_create_from_lapak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.lapak.LapakData;
import id.clorus.bukalelang.data.entity.response.lapak.Product;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 04/06/17.
 */

public class SelectProductActivity extends DefaultActivity implements SelectProductView{

    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.header)
    TextView header;

    private SelectProductAdapter adapter;
    private LinearLayoutManager layoutManager;
    List<Product> products;
    SelectProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapak_select);
        appPreference = Esperandro.getPreferences(AppPreference.class, this);
        ButterKnife.bind(this);

        presenter = new SelectProductPresenter(this,this);

        products = new ArrayList<>();
        initRecyclerView();

        loadLapak(String.valueOf(appPreference.id()));

    }

    public void loadLapak(String userId){
        RestService.Factory.getInstance().getLapak("users/"+userId+"/existing-products-from-lapak").enqueue(new Callback<LapakData>() {
            @Override
            public void onResponse(Call<LapakData> call, Response<LapakData> response) {
                Log.d("lapak","processed");
                if (response.body().getProducts().size() > 0) {
                    header.setText("Pilih (klik) lapak untuk dijadikan lelang");
                } else header.setText("Anda tidak memiliki lapak yang sedang aktif");

             for (int i = 0;i<response.body().getProducts().size();i++){
                 adapter.addItem(response.body().getProducts().get(i),products.size());
             }

            }

            @Override
            public void onFailure(Call<LapakData> call, Throwable t) {
                Log.d("lapak failed",t.getMessage());
            }
        });
    }

    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new SelectProductAdapter(this, products,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLapakLoadded(List<Product> data) {
        Log.d("lapak 1",data.get(0).getName());
        products.addAll(data);
        adapter.notifyDataSetChanged();
//        for (int i = 0;i<data.size();i++){
//            adapter.addItem(data.get(i),data.size());
//        }
    }

    @Override
    public void onLapakSelected(Product data) {
        Bundle bundle = new Bundle();
        bundle.putString("id", data.getId());
        bundle.putString("name",data.getName());
        bundle.putString("categoryName",data.getCategory());
        bundle.putString("description",data.getDesc());
        bundle.putString("condition",data.getCondition());
        bundle.putInt("weight",data.getWeight());
        bundle.putInt("bin",data.getPrice());
        String images = data.getImages().toString();
        images = images.substring(1,images.length()-1);
        bundle.putString("images",images);
        Log.d("images",images);
        Intent intent = new Intent(SelectProductActivity.this, CreateAuctionFromLapakActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
