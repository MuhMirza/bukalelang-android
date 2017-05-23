package id.clorus.bukalelang.presentation.ui.auction_detail;

import id.clorus.bukalelang.data.entity.response.bids.BidHistoryData;

/**
 * Created by mirza on 23/05/17.
 */

public interface BidHistoryView {

    void onBidHistoryLoaded(BidHistoryData data);
}
