package id.clorus.bukalelang.presentation.ui.select_category;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.bids.BidHistory;
import id.clorus.bukalelang.data.entity.response.categories.Category;
import id.clorus.bukalelang.presentation.ui.auction_create.CreateAuctionActivity;
import id.clorus.bukalelang.presentation.ui.auction_detail.BidHistoryAdapter;
import id.clorus.bukalelang.presentation.ui.auction_detail.BidHistoryPresenter;
import id.clorus.bukalelang.presentation.ui.base.DefaultFragment;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 25/05/17.
 */

public class SelectCategoryFragment extends DefaultFragment implements CategorySelectedView{

    Unbinder unbinder;
    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.category_name)
    TextView categoryName;

    private CategoryListAdapter adapter;
    private LinearLayoutManager layoutManager;
    List<Category> categoriesJson;
    List<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_category, container, false);
        unbinder = ButterKnife.bind(this,view);
        appPreference = Esperandro.getPreferences(AppPreference.class, getActivity());

        initCategories();

        return view;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("categories.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void initCategories(){

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Type listType = new TypeToken<ArrayList<Category>>(){}.getType();
                categoriesJson = new GsonBuilder().create().fromJson(loadJSONFromAsset(), listType);

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                categories = categoriesJson;
                initRecyclerView();

/*                for (int a= 0; a< categoriesJson.size();a++){
                    Category category1 = categoriesJson.get(a);
                    Log.d("category name level 1",category1.getName());

                    if (categoriesJson.get(a).getChildren().size() >0){
                        for (int b= 0; b< categoriesJson.get(a).getChildren().size();b++){
                            Category category2 = categoriesJson.get(a).getChildren().get(b);
                            Log.d("category name level 2",category2.getName());

                            if (categoriesJson.get(a).getChildren().get(b).getChildren().size() >0){
                                for (int c= 0; c< categoriesJson.get(a).getChildren().get(b).getChildren().size();c++){
                                    Category category3 = categoriesJson.get(a).getChildren().get(b).getChildren().get(c);
                                    Log.d("category name level 3",category3.getName());

                                }
                            }
                        }
                    }

                }*/

            }
        }.execute();
    }

    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new CategoryListAdapter(getActivity(), categories,this);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onCategorySelected(Category category, int position) {

        // category = categori yg diklik pada level sebelumnya

        if (category.getChildren().size() >0){
            categoryName.setText(category.getName());
            //ketika masih ada child

            adapter.clear();
            categories = category.getChildren();
//            adapter.notifyDataSetChanged();
            adapter.addAll(categories);
        } else {
            //ketika child sudah habis

            ((SelectCategoryActivity) getActivity()).onCategorySelected(category);
//            dismiss();

//            showToast("selected = "+category.getName());

        }

    }
}
