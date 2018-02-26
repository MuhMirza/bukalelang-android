package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.util.Log;

import id.clorus.bukalelang.data.entity.response.TimeLeftData;
import id.clorus.bukalelang.data.entity.response.WinStatusData;
import id.clorus.bukalelang.data.net.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 24/05/17.
 */

public class AuctionDetailPresenter {

    AuctionDetailView view;

    public AuctionDetailPresenter(AuctionDetailView view){
        this.view = view;
    }

    public void getTimeLeft(int idAuction){
        RestService.Factory.getInstance().getAuctionTimeLeft("auctions/"+idAuction+"/time-left").enqueue(new Callback<TimeLeftData>() {
            @Override
            public void onResponse(Call<TimeLeftData> call, Response<TimeLeftData> response) {
                view.onTimeLeftLoaded(response.body());
            }

            @Override
            public void onFailure(Call<TimeLeftData> call, Throwable t) {

            }
        });
    }

    public void getWinStatus(String auctionId,String userId){

        RestService.Factory.getInstance().getWinStatus("auctions/"+auctionId+"/checkout-status/"+userId).enqueue(new Callback<WinStatusData>() {
            @Override
            public void onResponse(Call<WinStatusData> call, Response<WinStatusData> response) {
                Log.d("win",String.valueOf(response.body().getIsWin()));
                view.onWinStatusLoaded(response.body());
            }

            @Override
            public void onFailure(Call<WinStatusData> call, Throwable t) {

            }
        });

    }
}
