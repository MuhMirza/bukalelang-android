package id.clorus.bukalelang.presentation.ui.won_auction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.win_auctions.AuctionsWon;
import id.clorus.bukalelang.presentation.ui.auction_detail.AuctionDetailActivity;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 27/05/17.
 */

public class WonAuctionActivity extends DefaultActivity implements WonView {


    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<AuctionsWon> auctions;
    private WonAuctionListAdapter adapter;
    private LinearLayoutManager layoutManager;

    WonAuctionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_auction);

        presenter = new WonAuctionPresenter(this,this);

        appPreference = Esperandro.getPreferences(AppPreference.class, this);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");

        auctions = new ArrayList<>();
        initRecyclerView();

        presenter.searchAuctions(userId);


    }


    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new WonAuctionListAdapter(this, auctions,this);

        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onAllAuctionLoaded(List<AuctionsWon> data) {
        if (data.size() > 0){
            adapter.clear();

            for (int i = 0;i<data.size();i++){
                adapter.addItem(data.get(i),auctions.size());
            }

        }
    }

    @Override
    public void onItemSelected(AuctionsWon data) {

        Bundle bundle = new Bundle();
        bundle.putInt("id", data.getAuctionId());
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
        bundle.putInt("bidderCount",data.getBidderCount());
        bundle.putString("avatarUrl",data.getAvatarUrl());

        String images = data.getImages().toString();
        images = images.substring(1,images.length()-1);

        bundle.putString("images",images);
        Log.d("images",images);

        Intent intent = new Intent(WonAuctionActivity.this, AuctionDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
