package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.bids.BidHistory;
import id.clorus.bukalelang.presentation.ui.home.HomeView;


/**
 * Created by mirza on 07/04/16.
 */
public class BidHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private ArrayList<BidHistory> mDataset;
    //private static MyClickListener myClickListener;
    private Context mContext;
    static String imageUrls;
    public static String[] thumbnailUrl;
    private LayoutInflater inflater;
    private BidHistoryView mView;

    public BidHistoryAdapter(Context context, ArrayList<BidHistory> mDataset, BidHistoryView view) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.mView = view;

    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bid_history, parent, false);


        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        mDataset.size();

        try {

            ((DataObjectHolder) holder).username.setText(mDataset.get(holder.getAdapterPosition()).getNameOfBidder());
            ((DataObjectHolder) holder).nominalBid.setText(String.valueOf(mDataset.get(holder.getAdapterPosition()).getBidNominal()));


        } catch (Exception e){
            e.printStackTrace();
        }

        /*
            Picasso.with(mContext)
                    .load(mDataset.get(position).getImages())
                    .error(R.color.grey_dark)
                    .placeholder(R.color.grey_dark)
                    .into(((DataObjectHolder) holder).cover);      */

    }


    public void addItem(BidHistory dataObj, int index) {
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
    public void addAll(ArrayList<BidHistory> list) {
        mDataset.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder  {
        RoundedImageView avatar;
        TextView title;
        TextView username;
        TextView nominalBid;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            this.title = (TextView) itemView.findViewById(R.id.auction_title);
            this.avatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            this.nominalBid = (TextView) itemView.findViewById(R.id.bid_nominal);
            this.username = (TextView) itemView.findViewById(R.id.username);

        }


    }



}




