package id.clorus.bukalelang.presentation.ui.profile;

import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.data.entity.response.joined_auction.JoinedAuctionData;

/**
 * Created by mirza on 28/05/17.
 */

public interface JoinedAuctionView {


    void onAllAuctionLoaded(JoinedAuctionData data);
    void onItemSelected(Auction data);
}
