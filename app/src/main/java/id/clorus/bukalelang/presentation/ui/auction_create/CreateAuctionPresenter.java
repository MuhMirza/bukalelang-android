package id.clorus.bukalelang.presentation.ui.auction_create;

import android.content.Context;

import java.io.File;

import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.data.entity.response.CreateAuctionData;
import id.clorus.bukalelang.data.entity.response.UploadImageData;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.model.AuctionPhoto;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 24/05/17.
 */

public class CreateAuctionPresenter {

    CreateAuctionView view;
    Context context;
    AppPreference appPreference;


    public CreateAuctionPresenter(CreateAuctionView view,Context context){
        this.view = view;
        this.context = context;
        appPreference = Esperandro.getPreferences(AppPreference.class, context);
    }

    public void createAuctionRequest(String title,int categoryId,String isNew,int weight,String description,
                                     int minPrice,int maxPrice,int kelipatanBid,String imageId,String endDate){

        RestService.Factory.getInstance().createAuction(appPreference.id(),appPreference.bukalapakId(),appPreference.accessToken(),
                title,categoryId,isNew,weight,description,minPrice,maxPrice,kelipatanBid,imageId,endDate).enqueue(new Callback<CreateAuctionData>() {
            @Override
            public void onResponse(Call<CreateAuctionData> call, Response<CreateAuctionData> response) {
                view.onCreateAuctionComplete(response.body());
            }

            @Override
            public void onFailure(Call<CreateAuctionData> call, Throwable t) {

            }
        });


    }

    public void uploadPhoto(final String path){

        //String imagePath = "";
        File photo = new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photo);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", photo.getName(), requestFile);

        RestService.FactoryBukalapak.getInstance().uploadImage(appPreference.basicToken(),body).enqueue(new Callback<UploadImageData>() {
            @Override
            public void onResponse(Call<UploadImageData> call, Response<UploadImageData> response) {

                AuctionPhoto data = new AuctionPhoto();
                data.setId(response.body().getId());
                data.setPath(path);
                view.onFinishUploadImage(data);
            }

            @Override
            public void onFailure(Call<UploadImageData> call, Throwable t) {

            }
        });


    }


}
