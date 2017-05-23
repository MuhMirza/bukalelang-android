package id.clorus.bukalelang.presentation.ui.home;

import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;

/**
 * Created by mirza on 23/05/17.
 */

public interface HomeView {

    void onAllAuctionLoaded(AuctionData data);
    void onItemSelected(Auction data);

}
