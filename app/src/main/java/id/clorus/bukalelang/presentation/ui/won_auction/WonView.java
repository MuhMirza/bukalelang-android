package id.clorus.bukalelang.presentation.ui.won_auction;

import java.util.List;

import id.clorus.bukalelang.data.entity.response.win_auctions.AuctionsWon;

/**
 * Created by mirza on 23/05/17.
 */

public interface WonView {

    void onAllAuctionLoaded(List<AuctionsWon> data);
    void onItemSelected(AuctionsWon data);

}
