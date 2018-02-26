package id.clorus.bukalelang.presentation.ui.won_auction;

import android.content.Context;
import android.util.Log;

import id.clorus.bukalelang.data.entity.response.win_auctions.WinAuctionsData;
import id.clorus.bukalelang.data.net.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class WonAuctionPresenter {

    Context context;
    WonView view;

    public WonAuctionPresenter(WonView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void searchAuctions(String id){
        RestService.Factory.getInstance().getWinAuctions("users/"+id+"/auctions-won").enqueue(new Callback<WinAuctionsData>() {
            @Override
            public void onResponse(Call<WinAuctionsData> call, Response<WinAuctionsData> response) {
                Log.d("request","processed");
                view.onAllAuctionLoaded(response.body().getAuctionsWon());
            }

            @Override
            public void onFailure(Call<WinAuctionsData> call, Throwable t) {
                Log.d("request fail",t.getMessage());
            }
        });
    }

}