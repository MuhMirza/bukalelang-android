
package id.clorus.bukalelang.data.entity.response.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAddress {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("primary")
    @Expose
    private boolean primary;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address_attributes")
    @Expose
    private AddressAttributes addressAttributes;
    @SerializedName("should_tfa")
    @Expose
    private boolean shouldTfa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressAttributes getAddressAttributes() {
        return addressAttributes;
    }

    public void setAddressAttributes(AddressAttributes addressAttributes) {
        this.addressAttributes = addressAttributes;
    }

    public boolean isShouldTfa() {
        return shouldTfa;
    }

    public void setShouldTfa(boolean shouldTfa) {
        this.shouldTfa = shouldTfa;
    }

}
