package id.clorus.bukalelang.presentation.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.presentation.utils.AppPreference;


/**
 * Created by mirza on 07/04/16.
 */
public class AuctionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private ArrayList<Auction> mDataset;
    AppPreference appPreference;
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

        appPreference = Esperandro.getPreferences(AppPreference.class, mContext);


        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        mDataset.size();

        try {

            ((DataObjectHolder) holder).title.setText(mDataset.get(holder.getAdapterPosition()).getTitle());
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            ((DataObjectHolder) holder).highestBid.setText(formatRupiah.format(mDataset.get(holder.getAdapterPosition()).getCurrentPrice()));

        } catch (Exception e){
            e.printStackTrace();
        }
            Picasso.with(mContext)
                    .load(mDataset.get(position).getImages().get(0))
                    .error(R.color.grey_dark)
                    .placeholder(R.color.grey_dark)
                    .into(((DataObjectHolder) holder).cover);

        ((DataObjectHolder) holder).countdownTimerText.setTypeface(null, Typeface.BOLD);

        if ((mDataset.get(holder.getAdapterPosition()).getTimeLeft() > 0) && (mDataset.get(holder.getAdapterPosition()).getCurrentPrice() < mDataset.get(holder.getAdapterPosition()).getMaxPrice()) ){

            try {
                CountDownTimer countDownTimer = new CountDownTimer(mDataset.get(holder.getAdapterPosition()).getTimeLeft(),10) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        long millis = millisUntilFinished;

                        long seconds = millis / 1000;
                        long minutes = seconds / 60;
                        long hours = minutes / 60;
                        long days = hours / 24;

                        String time;
                        if (days > 0){
                            time = days + "Hari, " + String.format("%02d:%02d",hours % 24,minutes % 60);
                        } else {
                            time = String.format("%02d:%02d",hours % 24,minutes % 60);
                        }

                        ((DataObjectHolder) holder).countdownTimerText.setText(time);

                    }

                    @Override
                    public void onFinish() {

                    }
                };

                countDownTimer.start();
            } catch (Exception e){
                e.printStackTrace();
            }

        } else {
            ((DataObjectHolder) holder).countdownTimerText.setText("SELESAI");
        }

        if (mDataset.get(holder.getAdapterPosition()).getUserId() == appPreference.id() ){
            ((DataObjectHolder) holder).btnBid.setText("LIHAT LELANG");
//            ((DataObjectHolder) holder).btnBid.setClickable(false);
        } else ((DataObjectHolder) holder).btnBid.setText("BID SEKARANG");



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




