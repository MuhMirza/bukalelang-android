
package id.clorus.bukalelang.data.entity.response.auctions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Auction {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("min_price")
    @Expose
    private int minPrice;
    @SerializedName("max_price")
    @Expose
    private int maxPrice;
    @SerializedName("kelipatan_bid")
    @Expose
    private int kelipatanBid;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("new")
    @Expose
    private boolean _new;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("isRunning")
    @Expose
    private int isRunning;
    @SerializedName("running")
    @Expose
    private boolean running;
    @SerializedName("small_images")
    @Expose
    private List<String> smallImages = null;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("current_price")
    @Expose
    private int currentPrice;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("time_left")
    @Expose
    private int timeLeft;
    @SerializedName("bidderCount")
    @Expose
    private int bidderCount;
    private String imageString;

    public boolean is_new() {
        return _new;
    }

    public void set_new(boolean _new) {
        this._new = _new;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getBidderCount() {
        return bidderCount;
    }

    public void setBidderCount(int bidderCount) {
        this.bidderCount = bidderCount;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isNew() {
        return _new;
    }

    public void setNew(boolean _new) {
        this._new = _new;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(int isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public List<String> getSmallImages() {
        return smallImages;
    }

    public void setSmallImages(List<String> smallImages) {
        this.smallImages = smallImages;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

}
