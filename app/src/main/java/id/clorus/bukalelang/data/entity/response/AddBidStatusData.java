
package id.clorus.bukalelang.data.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBidStatusData {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("auctionId")
    @Expose
    private int auctionId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bidding_time")
    @Expose
    private String biddingTime;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("current_price")
    @Expose
    private int currentPrice;
    @SerializedName("minimum_next_bidding")
    @Expose
    private int minimumNextBidding;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(String biddingTime) {
        this.biddingTime = biddingTime;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getMinimumNextBidding() {
        return minimumNextBidding;
    }

    public void setMinimumNextBidding(int minimumNextBidding) {
        this.minimumNextBidding = minimumNextBidding;
    }
}
