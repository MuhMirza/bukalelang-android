
package id.clorus.bukalelang.data.entity.response.checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Courier {

    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("eta")
    @Expose
    private String eta;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

}
