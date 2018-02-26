
package id.clorus.bukalelang.data.entity.response.win_auctions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("auctionsJoinedCount")
    @Expose
    private int auctionsJoinedCount;
    @SerializedName("wonAuctionsCount")
    @Expose
    private int wonAuctionsCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getAuctionsJoinedCount() {
        return auctionsJoinedCount;
    }

    public void setAuctionsJoinedCount(int auctionsJoinedCount) {
        this.auctionsJoinedCount = auctionsJoinedCount;
    }

    public int getWonAuctionsCount() {
        return wonAuctionsCount;
    }

    public void setWonAuctionsCount(int wonAuctionsCount) {
        this.wonAuctionsCount = wonAuctionsCount;
    }

}
