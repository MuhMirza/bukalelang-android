package id.clorus.bukalelang.presentation.ui.auction_detail;

import id.clorus.bukalelang.data.entity.response.TimeLeftData;
import id.clorus.bukalelang.data.entity.response.WinStatusData;

/**
 * Created by mirza on 24/05/17.
 */

public interface AuctionDetailView {

    void onTimeLeftLoaded(TimeLeftData data);
    void onWinStatusLoaded(WinStatusData data);
}
