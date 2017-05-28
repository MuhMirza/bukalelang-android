package id.clorus.bukalelang.presentation.ui.search_auction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.presentation.ui.auction_detail.AuctionDetailActivity;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.AuctionListAdapter;
import id.clorus.bukalelang.presentation.ui.home.HomeActivity;
import id.clorus.bukalelang.presentation.ui.home.HomePresenter;
import id.clorus.bukalelang.presentation.ui.home.HomeView;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 27/05/17.
 */

public class SearchAuctionActivity extends DefaultActivity implements HomeView {


    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<Auction> auctions;
    private AuctionListAdapter adapter;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.searchView)
    SearchView searchView;

    SearchAuctionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_auction);

        presenter = new SearchAuctionPresenter(this,this);

        appPreference = Esperandro.getPreferences(AppPreference.class, this);
        ButterKnife.bind(this);

        auctions = new ArrayList<>();
        initRecyclerView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                presenter.searchAuctions(query);
                return false;
            }
        });

        searchView.onActionViewExpanded();
    }


    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new AuctionListAdapter(this, auctions,this);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onAllAuctionLoaded(AuctionData data) {
        Log.d("message",data.getMessage());
        adapter.clear();

        for (int i = 0;i<data.getAuctions().size();i++){
            adapter.addItem(data.getAuctions().get(i),auctions.size());
        }


    }
    @Override
    public void onItemSelected(Auction data) {

        Bundle bundle = new Bundle();
        bundle.putInt("id", data.getId());
        bundle.putInt("userId",data.getUserId());
        bundle.putInt("bin",data.getMaxPrice());
        bundle.putInt("startPrice",data.getMinPrice());
        bundle.putInt("currentBid",data.getCurrentPrice());
        bundle.putInt("kelipatanBid",data.getKelipatanBid());
        bundle.putInt("weight",data.getWeight());
        bundle.putString("name", data.getName());
        bundle.putString("title",data.getTitle());
        bundle.putString("description",data.getDescription());
        bundle.putString("categoryName",data.getCategoryName());
        bundle.putString("location",data.getLocation());
        bundle.putString("productId",data.getProductId());
        bundle.putString("startDate",data.getStartDate());
        bundle.putString("endDate",data.getEndDate());
        bundle.putString("slug",data.getSlug());
        bundle.putInt("timeleft",data.getTimeLeft());

        String images = data.getImages().toString();
        images = images.substring(1,images.length()-1);

        bundle.putString("images",images);
        Log.d("images",images);

        Intent intent = new Intent(SearchAuctionActivity.this, AuctionDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
