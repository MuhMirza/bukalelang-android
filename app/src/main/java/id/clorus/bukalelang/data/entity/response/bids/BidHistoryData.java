
package id.clorus.bukalelang.data.entity.response.bids;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BidHistoryData {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("auction_detail")
    @Expose
    private AuctionDetail auctionDetail;
    @SerializedName("bid_history")
    @Expose
    private List<BidHistory> bidHistory = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuctionDetail getAuctionDetail() {
        return auctionDetail;
    }

    public void setAuctionDetail(AuctionDetail auctionDetail) {
        this.auctionDetail = auctionDetail;
    }

    public List<BidHistory> getBidHistory() {
        return bidHistory;
    }

    public void setBidHistory(List<BidHistory> bidHistory) {
        this.bidHistory = bidHistory;
    }

}
