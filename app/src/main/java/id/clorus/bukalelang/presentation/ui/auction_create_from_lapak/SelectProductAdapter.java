package id.clorus.bukalelang.presentation.ui.auction_create_from_lapak;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.lapak.Product;
import id.clorus.bukalelang.presentation.utils.AppPreference;


/**
 * Created by mirza on 07/04/16.
 */
public class SelectProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "ProductListAdapter";
    private List<Product> mDataset;
    AppPreference appPreference;
    //private static MyClickListener myClickListener;
    private Context mContext;
    static String imageUrls;
    public static String[] thumbnailUrl;
    private LayoutInflater inflater;
    private SelectProductView mView;

    public SelectProductAdapter(Context context, List<Product> mDataset, SelectProductView view) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.mView = view;
    }


    public SelectProductAdapter(ArrayList<Product> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lapak, parent, false);

        appPreference = Esperandro.getPreferences(AppPreference.class, mContext);


        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        try {

            ((DataObjectHolder) holder).productName.setText(mDataset.get(holder.getAdapterPosition()).getName());
            ((DataObjectHolder) holder).condition.setText(mDataset.get(holder.getAdapterPosition()).getCondition());
            ((DataObjectHolder) holder).weight.setText(String.valueOf(mDataset.get(holder.getAdapterPosition()).getWeight()));
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

            ((DataObjectHolder) holder).price.setText(formatRupiah.format(mDataset.get(holder.getAdapterPosition()).getPrice()));

            Picasso.with(mContext)
                    .load(mDataset.get(holder.getAdapterPosition()).getImages().get(0))
                    .error(R.color.grey_dark)
                    .placeholder(R.color.grey_dark)
                    .into(((DataObjectHolder) holder).cover);

        } catch (Exception e){
            e.printStackTrace();
        }


        mDataset.size();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.onLapakSelected(mDataset.get(holder.getAdapterPosition()));

            }
        });


    }


    public void addItem(Product dataObj, int index) {
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
    public void addAll(ArrayList<Product> list) {
        mDataset.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder  {
        ImageView cover;
        TextView productName;
        TextView condition;
        TextView price;
        TextView weight;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.cover = (ImageView) itemView.findViewById(R.id.product_image);
            this.productName = (TextView) itemView.findViewById(R.id.product_name);
            this.condition = (TextView) itemView.findViewById(R.id.condition);
            this.price = (TextView) itemView.findViewById(R.id.product_price);
            this.weight = (TextView) itemView.findViewById(R.id.weight);

        }


    }



}




