package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.content.Context;

import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.data.entity.response.bids.BidHistoryData;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class BidHistoryPresenter {

    BidHistoryView view;
    Context context;

    AppPreference appPreference;

    public BidHistoryPresenter(BidHistoryView view,Context context){
        this.view = view;
        this.context = context;
        appPreference = Esperandro.getPreferences(AppPreference.class, context);
    }

    public void bidHistoryRequest(String auctionId){

        RestService.Factory.getInstance().getBidsHistory("auctions/"+auctionId+"/bid-history").enqueue(new Callback<BidHistoryData>() {
            @Override
            public void onResponse(Call<BidHistoryData> call, Response<BidHistoryData> response) {
                view.onBidHistoryLoaded(response.body());
            }

            @Override
            public void onFailure(Call<BidHistoryData> call, Throwable t) {

            }
        });

    }

}
