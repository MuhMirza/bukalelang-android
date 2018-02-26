
package id.clorus.bukalelang.data.entity.response.checkout;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping {

    @SerializedName("courier_name")
    @Expose
    private String courierName;
    @SerializedName("couriers")
    @Expose
    private List<Courier> couriers = null;

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }

}
