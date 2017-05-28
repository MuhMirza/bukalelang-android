package id.clorus.bukalelang.presentation.ui.search_auction;

import android.content.Context;
import android.util.Log;

import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.ui.home.HomeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class SearchAuctionPresenter {

    Context context;
    HomeView view;

    public SearchAuctionPresenter(HomeView view, Context context){
        this.view = view;
        this.context = context;

    }

    public void searchAuctions(String query){
        RestService.Factory.getInstance().searchAuction("auctions/search?query="+query).enqueue(new Callback<AuctionData>() {
            @Override
            public void onResponse(Call<AuctionData> call, Response<AuctionData> response) {
                Log.d("request","processed");
                view.onAllAuctionLoaded(response.body());
            }

            @Override
            public void onFailure(Call<AuctionData> call, Throwable t) {
                Log.d("request fail",t.getMessage());
            }
        });
    }

}