
package id.clorus.bukalelang.data.entity.response.lapak;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("category_id")
    @Expose
    private int categoryId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("category_structure")
    @Expose
    private List<Object> categoryStructure = null;
    @SerializedName("courier")
    @Expose
    private List<Object> courier = null;
    @SerializedName("seller_username")
    @Expose
    private String sellerUsername;
    @SerializedName("seller_name")
    @Expose
    private String sellerName;
    @SerializedName("seller_id")
    @Expose
    private int sellerId;
    @SerializedName("seller_avatar")
    @Expose
    private String sellerAvatar;
    @SerializedName("seller_level")
    @Expose
    private String sellerLevel;
    @SerializedName("for_sale")
    @Expose
    private boolean forSale;
    @SerializedName("seller_voucher")
    @Expose
    private SellerVoucher sellerVoucher;
    @SerializedName("waiting_payment")
    @Expose
    private int waitingPayment;
    @SerializedName("sold_count")
    @Expose
    private int soldCount;
    @SerializedName("specs")
    @Expose
    private Specs specs;
    @SerializedName("force_insurance")
    @Expose
    private boolean forceInsurance;
    @SerializedName("free_shipping_coverage")
    @Expose
    private List<Object> freeShippingCoverage = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("image_ids")
    @Expose
    private List<Integer> imageIds = null;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("small_images")
    @Expose
    private List<String> smallImages = null;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("stock")
    @Expose
    private int stock;
    @SerializedName("favorited")
    @Expose
    private boolean favorited;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("product_sin")
    @Expose
    private List<Object> productSin = null;
    @SerializedName("rating")
    @Expose
    private Rating rating;
    @SerializedName("current_variant_name")
    @Expose
    private String currentVariantName;
    @SerializedName("current_product_sku_id")
    @Expose
    private int currentProductSkuId;
    @SerializedName("product_sku")
    @Expose
    private List<Object> productSku = null;
    @SerializedName("options")
    @Expose
    private List<Object> options = null;
    @SerializedName("interest_count")
    @Expose
    private int interestCount;
    @SerializedName("last_relist_at")
    @Expose
    private String lastRelistAt;
    @SerializedName("view_count")
    @Expose
    private int viewCount;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Object> getCategoryStructure() {
        return categoryStructure;
    }

    public void setCategoryStructure(List<Object> categoryStructure) {
        this.categoryStructure = categoryStructure;
    }

    public List<Object> getCourier() {
        return courier;
    }

    public void setCourier(List<Object> courier) {
        this.courier = courier;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerAvatar() {
        return sellerAvatar;
    }

    public void setSellerAvatar(String sellerAvatar) {
        this.sellerAvatar = sellerAvatar;
    }

    public String getSellerLevel() {
        return sellerLevel;
    }

    public void setSellerLevel(String sellerLevel) {
        this.sellerLevel = sellerLevel;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    public SellerVoucher getSellerVoucher() {
        return sellerVoucher;
    }

    public void setSellerVoucher(SellerVoucher sellerVoucher) {
        this.sellerVoucher = sellerVoucher;
    }

    public int getWaitingPayment() {
        return waitingPayment;
    }

    public void setWaitingPayment(int waitingPayment) {
        this.waitingPayment = waitingPayment;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public Specs getSpecs() {
        return specs;
    }

    public void setSpecs(Specs specs) {
        this.specs = specs;
    }

    public boolean isForceInsurance() {
        return forceInsurance;
    }

    public void setForceInsurance(boolean forceInsurance) {
        this.forceInsurance = forceInsurance;
    }

    public List<Object> getFreeShippingCoverage() {
        return freeShippingCoverage;
    }

    public void setFreeShippingCoverage(List<Object> freeShippingCoverage) {
        this.freeShippingCoverage = freeShippingCoverage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Object> getProductSin() {
        return productSin;
    }

    public void setProductSin(List<Object> productSin) {
        this.productSin = productSin;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getCurrentVariantName() {
        return currentVariantName;
    }

    public void setCurrentVariantName(String currentVariantName) {
        this.currentVariantName = currentVariantName;
    }

    public int getCurrentProductSkuId() {
        return currentProductSkuId;
    }

    public void setCurrentProductSkuId(int currentProductSkuId) {
        this.currentProductSkuId = currentProductSkuId;
    }

    public List<Object> getProductSku() {
        return productSku;
    }

    public void setProductSku(List<Object> productSku) {
        this.productSku = productSku;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

    public int getInterestCount() {
        return interestCount;
    }

    public void setInterestCount(int interestCount) {
        this.interestCount = interestCount;
    }

    public String getLastRelistAt() {
        return lastRelistAt;
    }

    public void setLastRelistAt(String lastRelistAt) {
        this.lastRelistAt = lastRelistAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

}
