
package id.clorus.bukalelang.data.entity.response.lapak;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("average_rate")
    @Expose
    private int averageRate;
    @SerializedName("user_count")
    @Expose
    private int userCount;

    public int getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(int averageRate) {
        this.averageRate = averageRate;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

}
