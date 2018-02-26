package id.clorus.bukalelang.presentation.ui.my_auctions;

import android.content.Context;
import android.util.Log;

import id.clorus.bukalelang.data.entity.response.my_auction.MyAuctionData;
import id.clorus.bukalelang.data.net.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class MyAuctionPresenter {

    Context context;
    MyAucionView view;

    public MyAuctionPresenter(MyAucionView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void getMyAuction(String id){
        RestService.Factory.getInstance().getMyAuctions("users/"+id+"/my-auctions").enqueue(new Callback<MyAuctionData>() {
            @Override
            public void onResponse(Call<MyAuctionData> call, Response<MyAuctionData> response) {
                Log.d("request","processed");
                view.onAllAuctionLoaded(response.body().getMyAuctions());
            }

            @Override
            public void onFailure(Call<MyAuctionData> call, Throwable t) {
                Log.d("request fail",t.getMessage());
            }
        });

    }

}