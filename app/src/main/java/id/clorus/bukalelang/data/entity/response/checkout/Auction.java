
package id.clorus.bukalelang.data.entity.response.checkout;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auction {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("small_images")
    @Expose
    private List<String> smallImages = null;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("new")
    @Expose
    private boolean _new;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("current_price")
    @Expose
    private int currentPrice;
    @SerializedName("bidderCount")
    @Expose
    private int bidderCount;
    @SerializedName("min_price")
    @Expose
    private int minPrice;
    @SerializedName("max_price")
    @Expose
    private int maxPrice;
    @SerializedName("kelipatan_bid")
    @Expose
    private int kelipatanBid;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("time_left")
    @Expose
    private int timeLeft;
    @SerializedName("running")
    @Expose
    private boolean running;
    @SerializedName("isRunning")
    @Expose
    private int isRunning;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userId")
    @Expose
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getSmallImages() {
        return smallImages;
    }

    public void setSmallImages(List<String> smallImages) {
        this.smallImages = smallImages;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isNew() {
        return _new;
    }

    public void setNew(boolean _new) {
        this._new = _new;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getBidderCount() {
        return bidderCount;
    }

    public void setBidderCount(int bidderCount) {
        this.bidderCount = bidderCount;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getKelipatanBid() {
        return kelipatanBid;
    }

    public void setKelipatanBid(int kelipatanBid) {
        this.kelipatanBid = kelipatanBid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(int isRunning) {
        this.isRunning = isRunning;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
