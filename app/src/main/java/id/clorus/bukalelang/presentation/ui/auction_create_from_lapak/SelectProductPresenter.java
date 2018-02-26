package id.clorus.bukalelang.presentation.ui.auction_create_from_lapak;

import android.content.Context;
import android.util.Log;

import id.clorus.bukalelang.data.entity.response.lapak.LapakData;
import id.clorus.bukalelang.data.net.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 04/06/17.
 */

public class SelectProductPresenter {

    Context context;
    SelectProductView view;

    public SelectProductPresenter(Context context,SelectProductView view){
        this.context = context;
        this.view = view;
    }


    public void loadLapak(String userId){
        RestService.Factory.getInstance().getLapak("users/4/existing-products-from-lapak").enqueue(new Callback<LapakData>() {
            @Override
            public void onResponse(Call<LapakData> call, Response<LapakData> response) {
                Log.d("lapak","processed");
                view.onLapakLoadded(response.body().getProducts());
            }

            @Override
            public void onFailure(Call<LapakData> call, Throwable t) {
                Log.d("lapak failed",t.getMessage());
            }
        });
    }
}
