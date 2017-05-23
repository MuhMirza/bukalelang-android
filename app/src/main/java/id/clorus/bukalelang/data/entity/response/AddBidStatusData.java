
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
    private Object id;
    @SerializedName("auctionId")
    @Expose
    private Object auctionId;
    @SerializedName("username")
    @Expose
    private Object username;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("bidding_time")
    @Expose
    private Object biddingTime;
    @SerializedName("categoryId")
    @Expose
    private Object categoryId;
    @SerializedName("current_price")
    @Expose
    private Object currentPrice;
    @SerializedName("minimum_next_bidding")
    @Expose
    private Object minimumNextBidding;

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

    public void setId(Object id) {
        this.id = id;
    }

    public Object getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Object auctionId) {
        this.auctionId = auctionId;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(Object biddingTime) {
        this.biddingTime = biddingTime;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    public Object getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Object currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Object getMinimumNextBidding() {
        return minimumNextBidding;
    }

    public void setMinimumNextBidding(Object minimumNextBidding) {
        this.minimumNextBidding = minimumNextBidding;
    }

}
