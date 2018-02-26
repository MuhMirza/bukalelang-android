package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.bids.BidHistory;


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

    PrettyTime prettyTime = new PrettyTime();

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
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            ((DataObjectHolder) holder).nominalBid.setText(formatRupiah.format(mDataset.get(holder.getAdapterPosition()).getBidNominal()));
/*
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String dateString = mDataset.get(holder.getAdapterPosition()).getBiddingTime();
            Date result1 = df1.parse(dateString);
            ((DataObjectHolder) holder).time.setText(prettyTime.format(result1));*/

        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            Picasso.with(mContext)
                    .load(mDataset.get(holder.getAdapterPosition()).getAvatarUrl())
                    .error(R.drawable.avatar_default)
                    .placeholder(R.drawable.avatar_default)
                    .into(((DataObjectHolder) holder).avatar);
        } catch (Exception e){
            e.printStackTrace();
        }



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
        TextView time;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.time = (TextView) itemView.findViewById(R.id.bid_time);
            this.avatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            this.nominalBid = (TextView) itemView.findViewById(R.id.bid_nominal);
            this.username = (TextView) itemView.findViewById(R.id.username);

        }


    }



}




