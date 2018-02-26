
package id.clorus.bukalelang.data.entity.response.bids;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuctionDetail {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("bid_count")
    @Expose
    private int bidCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

}
