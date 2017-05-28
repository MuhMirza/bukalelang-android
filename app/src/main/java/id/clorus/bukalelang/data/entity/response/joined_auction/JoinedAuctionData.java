
package id.clorus.bukalelang.data.entity.response.joined_auction;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.clorus.bukalelang.data.entity.response.auctions.Auction;

public class JoinedAuctionData {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;
    @SerializedName("auctionsJoined")
    @Expose
    private List<Auction> auctionsJoined = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<Auction> getAuctionsJoined() {
        return auctionsJoined;
    }

    public void setAuctionsJoined(List<Auction> auctionsJoined) {
        this.auctionsJoined = auctionsJoined;
    }

}
