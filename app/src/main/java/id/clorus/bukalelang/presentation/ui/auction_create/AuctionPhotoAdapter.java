package id.clorus.bukalelang.presentation.ui.auction_create;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.bids.BidHistory;
import id.clorus.bukalelang.presentation.model.AuctionPhoto;
import id.clorus.bukalelang.presentation.ui.auction_detail.BidHistoryView;


/**
 * Created by mirza on 07/04/16.
 */
public class AuctionPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private ArrayList<AuctionPhoto> mDataset;
    //private static MyClickListener myClickListener;
    private Context mContext;
    static String imageUrls;
    public static String[] thumbnailUrl;
    private LayoutInflater inflater;
    private CreateAuctionView mView;

    public AuctionPhotoAdapter(Context context, ArrayList<AuctionPhoto> mDataset, CreateAuctionView view) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.mView = view;

    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_auction, parent, false);


        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        mDataset.size();

        try {

            Uri uri=Uri.fromFile(new File(mDataset.get(position).getPath()));

            Picasso.with(mContext)
                    .load(uri)
                    .error(R.color.grey_dark)
                    .placeholder(R.color.grey_dark)
                    .into(((DataObjectHolder) holder).imageView);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void addItem(AuctionPhoto dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    // Clean all elements of the recycler
    public void clear() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<AuctionPhoto> list) {
        mDataset.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            this.title = (TextView) itemView.findViewById(R.id.auction_title);
            this.imageView = (ImageView) itemView.findViewById(R.id.action_image);

        }


    }



}




