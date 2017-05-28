package id.clorus.bukalelang.presentation.ui.profile;

import android.content.Context;
import android.util.Log;

import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.data.entity.response.joined_auction.JoinedAuctionData;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.ui.home.HomeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class JoinedAuctionPresenter {

    Context context;
    JoinedAuctionView view;

    public JoinedAuctionPresenter(JoinedAuctionView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void getJoinedAuction(String userId){
        RestService.Factory.getInstance().getJoinedAuctions("users/"+userId+"/auctions-joined").enqueue(new Callback<JoinedAuctionData>() {
            @Override
            public void onResponse(Call<JoinedAuctionData> call, Response<JoinedAuctionData> response) {
                Log.d("request","processed");
                view.onAllAuctionLoaded(response.body());
            }

            @Override
            public void onFailure(Call<JoinedAuctionData> call, Throwable t) {
                Log.d("request fail",t.getMessage());
            }
        });
    }

}