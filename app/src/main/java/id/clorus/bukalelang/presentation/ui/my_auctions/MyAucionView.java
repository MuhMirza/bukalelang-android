package id.clorus.bukalelang.presentation.ui.my_auctions;

import java.util.List;

import id.clorus.bukalelang.data.entity.response.my_auction.MyAuction;

/**
 * Created by mirza on 23/05/17.
 */

public interface MyAucionView {

    void onAllAuctionLoaded(List<MyAuction> data);
    void onItemSelected(MyAuction data);

}
