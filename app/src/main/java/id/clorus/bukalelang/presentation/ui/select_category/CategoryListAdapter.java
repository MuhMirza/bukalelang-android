package id.clorus.bukalelang.presentation.ui.select_category;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.categories.Category;
import id.clorus.bukalelang.presentation.ui.home.HomeView;


/**
 * Created by mirza on 07/04/16.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private List<Category> mDataset;
    //private static MyClickListener myClickListener;
    private Context mContext;
    static String imageUrls;
    public static String[] thumbnailUrl;
    private LayoutInflater inflater;
    CategorySelectedView view;

    public CategoryListAdapter(Context context, List<Category> mDataset, CategorySelectedView view) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.view = view;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);


        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        mDataset.size();


        try {
            ((DataObjectHolder) holder).categoryName.setText(mDataset.get(holder.getAdapterPosition()).getName());
//            if (mDataset.get(holder.getAdapterPosition()).getChildren().size() > 0){
//                ((DataObjectHolder) holder).detailImg.setVisibility(View.VISIBLE);
//            } ((DataObjectHolder) holder).detailImg.setVisibility(View.GONE);

        } catch (Exception e){
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.onCategorySelected(mDataset.get(holder.getAdapterPosition()),holder.getAdapterPosition());
            }
        });


    }


    public void addItem(Category dataObj, int index) {
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
    public void addAll(List<Category> list) {
        mDataset.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder  {

        TextView categoryName;
        ImageView detailImg;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.categoryName = (TextView) itemView.findViewById(R.id.category_name);
            this.detailImg = (ImageView) itemView.findViewById(R.id.detail_img);

            Log.i(LOG_TAG, "Adding Listener");
        }


    }



}




