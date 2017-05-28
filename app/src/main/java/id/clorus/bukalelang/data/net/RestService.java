package id.clorus.bukalelang.data.net;

import id.clorus.bukalelang.data.entity.response.AddBidStatusData;
import id.clorus.bukalelang.data.entity.response.CreateAuctionData;
import id.clorus.bukalelang.data.entity.response.CurrentBidData;
import id.clorus.bukalelang.data.entity.response.ItemAuctionData;
import id.clorus.bukalelang.data.entity.response.TimeLeftData;
import id.clorus.bukalelang.data.entity.response.UploadImageData;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.data.entity.response.auth.AuthData;
import id.clorus.bukalelang.data.entity.response.bids.BidHistoryData;
import id.clorus.bukalelang.data.entity.response.joined_auction.JoinedAuctionData;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by mirza on 27/11/16.
 */

public interface RestService {

    String BASE_URL = "http://studio.tealinuxos.org:3000/";

    String REGISTER_URL = "auth/register";
    String LOGIN_URL = "auth/login";

    String ADD_NEW_BID = "bids";
    String CREATE_AUCTION = "auctions";

    String BASE_BUKALAPAK_URL = "https://api.bukalapak.com/v2/";
    String UPLOAD_IMAGE = "images.json";


    @GET("")
    Call<AuctionData> getAllAuctions(@Url String url);
    //auctions?limit=5&&page=1

    @GET("")
    Call<JoinedAuctionData> getJoinedAuctions(@Url String url);
    //users/:id/auctions-joined

    @GET("auctions?limit=5&&page=1")
    Call<AuctionData> getAuctions();
    //auctions?limit=5&&page=1

    @GET("")
    Call<ItemAuctionData> getItemAuctionById(@Url String idAuction, @Header("userId") String userid,@Header("token") String token);
    //auctions/id

    @GET("")
    Call<Auction> getItemAuctionBySlug(@Url String idAuction);
    //auctions/slug/:slug

    @GET("")
    Call<AuctionData> searchAuction(@Url String url);
    //auctions/search?query=candi

    @GET("")
    Call<BidHistoryData> getBidsHistory(@Url String url);
    //auctions/:id/bid-history

    @GET("")
    Call<CurrentBidData> getCurrentBid(@Url String url);
    //auctions/id/current-price

    @GET("")
    Call<TimeLeftData> getAuctionTimeLeft(@Url String url);
    //auctions/id/time-left

    @POST(ADD_NEW_BID)
    @FormUrlEncoded
    Call<AddBidStatusData> addNewBid(@Field("auctionId") int auctionId, @Field("nextBid") int bid, @Header("userId") int userid, @Header("token") String token);

    @POST(LOGIN_URL)
    @FormUrlEncoded
    Call<AuthData> login(@Field("username") String username, @Field("password") String password);

    @POST(CREATE_AUCTION)
    @FormUrlEncoded
    Call<CreateAuctionData> createAuction(@Field("userId") int userId, @Field("bukalapakId") int bukalapakId,
                                          @Field("token") String token, @Field("title") String title,
                                          @Field("categoryId") int categoryId,
                                          @Field("new") String isNew, @Field("weight") int weight,
                                          @Field("description") String description, @Field("min_price") int min_price,
                                          @Field("max_price") int max_price, @Field("kelipatan_bid") int kelipatan_bid,
                                          @Field("imagesId") String imagesId, @Field("endDateFromAndroid") String end_date);

    @POST(REGISTER_URL)
    @FormUrlEncoded
    Call<AuthData> register(@Field("name") String name,@Field("email") String email,
                         @Field("username") String username, @Field("password") String password);


    @Multipart
    @POST(UPLOAD_IMAGE)
    Call<UploadImageData> uploadImage(@Header("Authorization") String basicToken,
                                      @Part MultipartBody.Part File);

    class Factory {

        private static RestService service;
        public static RestService getInstance(){

            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                        .build();

                service = retrofit.create(RestService.class);
                return service;
            }
            else return service;
        }
    }

    class FactoryBukalapak {

        private static RestService service;
        public static RestService getInstance(){

            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_BUKALAPAK_URL).addConverterFactory(GsonConverterFactory.create())
                        .build();

                service = retrofit.create(RestService.class);
                return service;
            }
            else return service;
        }
    }



}
