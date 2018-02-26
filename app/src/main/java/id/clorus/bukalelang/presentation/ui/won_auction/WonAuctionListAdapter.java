package id.clorus.bukalelang.presentation.ui.won_auction;

import android.content.Context;
import android.graphics.Typeface;
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
import id.clorus.bukalelang.data.entity.response.win_auctions.AuctionsWon;
import id.clorus.bukalelang.presentation.utils.AppPreference;


/**
 * Created by mirza on 07/04/16.
 */
public class WonAuctionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private ArrayList<AuctionsWon> mDataset;
    AppPreference appPreference;
    //private static MyClickListener myClickListener;
    private Context mContext;
    static String imageUrls;
    public static String[] thumbnailUrl;
    private LayoutInflater inflater;
    private WonView mView;

    public WonAuctionListAdapter(Context context, ArrayList<AuctionsWon> mDataset, WonView view) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.mView = view;
    }


    public WonAuctionListAdapter(ArrayList<AuctionsWon> myDataset) {
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

        ((DataObjectHolder) holder).countdownTimerText.setText("SELESAI");
        ((DataObjectHolder) holder).btnBid.setText("LIHAT DETAIL");

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


    public void addItem(AuctionsWon dataObj, int index) {
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
    public void addAll(ArrayList<AuctionsWon> list) {
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




