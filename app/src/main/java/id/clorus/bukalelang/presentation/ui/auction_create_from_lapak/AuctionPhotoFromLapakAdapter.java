package id.clorus.bukalelang.presentation.ui.auction_create_from_lapak;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.clorus.bukalelang.R;


/**
 * Created by mirza on 07/04/16.
 */
public class AuctionPhotoFromLapakAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private List<String> mDataset;
    //private static MyClickListener myClickListener;
    private Context mContext;
    static String imageUrls;
    public static String[] thumbnailUrl;
    private LayoutInflater inflater;
    private CreateAuctionView mView;

    public AuctionPhotoFromLapakAdapter(Context context, List<String> mDataset, CreateAuctionView view) {
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



            Picasso.with(mContext)
                    .load(mDataset.get(holder.getAdapterPosition()))
                    .error(R.color.grey_dark)
                    .placeholder(R.color.grey_dark)
                    .into(((DataObjectHolder) holder).imageView);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void addItem(String dataObj, int index) {
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
    public void addAll(ArrayList<String> list) {
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




