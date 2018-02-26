
package id.clorus.bukalelang.data.entity.response.checkout;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOutData {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("auction")
    @Expose
    private Auction auction;
    @SerializedName("finalPrice")
    @Expose
    private int finalPrice;
    @SerializedName("winnerName")
    @Expose
    private String winnerName;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("shipping")
    @Expose
    private List<Shipping> shipping = null;

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

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Shipping> getShipping() {
        return shipping;
    }

    public void setShipping(List<Shipping> shipping) {
        this.shipping = shipping;
    }

}
