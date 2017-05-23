package id.clorus.bukalelang.presentation.ui.home;

import android.content.Context;

import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.data.net.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class HomePresenter {

    Context context;
    HomeView view;

    public HomePresenter(HomeView view, Context context){
        this.view = view;
        this.context = context;

    }

    public void getAllAuctions(int page, int limit){
        RestService.Factory.getInstance().getAllAuctions("auctions?limit="+limit+"&&page="+page).enqueue(new Callback<AuctionData>() {
            @Override
            public void onResponse(Call<AuctionData> call, Response<AuctionData> response) {
                view.onAllAuctionLoaded(response.body());
            }

            @Override
            public void onFailure(Call<AuctionData> call, Throwable t) {

            }
        });
    }

}
