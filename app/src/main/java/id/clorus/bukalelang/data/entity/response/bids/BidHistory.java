
package id.clorus.bukalelang.data.entity.response.bids;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BidHistory {

    @SerializedName("name_of_bidder")
    @Expose
    private String nameOfBidder;
    @SerializedName("bid_nominal")
    @Expose
    private int bidNominal;
    @SerializedName("bidding_time")
    @Expose
    private String biddingTime;

    public String getNameOfBidder() {
        return nameOfBidder;
    }

    public void setNameOfBidder(String nameOfBidder) {
        this.nameOfBidder = nameOfBidder;
    }

    public int getBidNominal() {
        return bidNominal;
    }

    public void setBidNominal(int bidNominal) {
        this.bidNominal = bidNominal;
    }

    public String getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(String biddingTime) {
        this.biddingTime = biddingTime;
    }

}
