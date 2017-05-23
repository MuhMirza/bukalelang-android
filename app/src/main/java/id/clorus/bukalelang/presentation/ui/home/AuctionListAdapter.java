package id.clorus.bukalelang.presentation.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.presentation.config.AppConfig;
import id.clorus.bukalelang.presentation.ui.auction_detail.AuctionDetailActivity;


/**
 * Created by mirza on 07/04/16.
 */
public class AuctionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private ArrayList<Auction> mDataset;
    //private static MyClickListener myClickListener;
    private Context mContext;
    static String imageUrls;
    public static String[] thumbnailUrl;
    private LayoutInflater inflater;
    private HomeView mView;

    public AuctionListAdapter(Context context, ArrayList<Auction> mDataset, HomeView view) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.mView = view;
    }


    public AuctionListAdapter(ArrayList<Auction> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_auction, parent, false);


        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        mDataset.size();

        try {

            ((DataObjectHolder) holder).title.setText(mDataset.get(holder.getAdapterPosition()).getTitle());
            ((DataObjectHolder) holder).highestBid.setText("Rp. "+String.valueOf(mDataset.get(holder.getAdapterPosition()).getCurrentPrice()));

        } catch (Exception e){
            e.printStackTrace();
        }
            Picasso.with(mContext)
                    .load(mDataset.get(position).getImages())
                    .error(R.color.grey_dark)
                    .placeholder(R.color.grey_dark)
                    .into(((DataObjectHolder) holder).cover);


        CountDownTimer countDownTimer = new CountDownTimer(9800,100) {
            @Override
            public void onTick(long millisUntilFinished) {

                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                ((DataObjectHolder) holder).countdownTimerText.setText(hms);
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer.start();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mView.onItemSelected(mDataset.get(holder.getAdapterPosition()));

            }
        });

        ((DataObjectHolder) holder).btnBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.onItemSelected(mDataset.get(holder.getAdapterPosition()));
            }
        });


    }


    public void addItem(Auction dataObj, int index) {
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
    public void addAll(ArrayList<Auction> list) {
        mDataset.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder  {
        ImageView cover;
        TextView title;
        TextView highestBid;
        Button btnBid;
        TextView countdownTimerText;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.cover = (ImageView) itemView.findViewById(R.id.auction_cover);
            this.title = (TextView) itemView.findViewById(R.id.auction_title);
            this.highestBid = (TextView) itemView.findViewById(R.id.highest_bid);
            this.btnBid = (Button) itemView.findViewById(R.id.btn_bid_now);
            this.countdownTimerText = (TextView) itemView.findViewById(R.id.countdownText);

            Log.i(LOG_TAG, "Adding Listener");
        }


    }



}




