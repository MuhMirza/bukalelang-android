
package id.clorus.bukalelang.data.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinStatusData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isWin")
    @Expose
    private Integer isWin;
    @SerializedName("isCheckedOut")
    @Expose
    private Integer isCheckedOut;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
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

    public Integer getIsWin() {
        return isWin;
    }

    public void setIsWin(Integer isWin) {
        this.isWin = isWin;
    }

    public Integer getIsCheckedOut() {
        return isCheckedOut;
    }

    public void setIsCheckedOut(Integer isCheckedOut) {
        this.isCheckedOut = isCheckedOut;
    }

}
