package id.clorus.bukalelang.presentation.ui.auction_create;

import id.clorus.bukalelang.data.entity.response.CreateAuctionData;
import id.clorus.bukalelang.data.entity.response.UploadImageData;
import id.clorus.bukalelang.presentation.model.AuctionPhoto;

/**
 * Created by mirza on 24/05/17.
 */

public interface CreateAuctionView {

    void onCreateAuctionComplete(CreateAuctionData data);
    void onFinishUploadImage(UploadImageData data, String imagePath);
    void onFinishUploadImage(AuctionPhoto data);
}
