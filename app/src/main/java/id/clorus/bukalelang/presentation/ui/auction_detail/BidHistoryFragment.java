package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.bids.BidHistory;
import id.clorus.bukalelang.data.entity.response.bids.BidHistoryData;
import id.clorus.bukalelang.presentation.ui.base.DefaultFragment;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 23/05/17.
 */

public class BidHistoryFragment extends DefaultFragment implements BidHistoryView {

    Unbinder unbinder;
    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private BidHistoryAdapter adapter;
    private LinearLayoutManager layoutManager;
    ArrayList<BidHistory> bidHistories;

    BidHistoryPresenter presenter;

    Auction auction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bid_history, container, false);
        unbinder = ButterKnife.bind(this,view);
        appPreference = Esperandro.getPreferences(AppPreference.class, getActivity());
        presenter = new BidHistoryPresenter(this,getActivity());

        Bundle bundle = getActivity().getIntent().getExtras();
        auction = new Auction();

        auction.setId(bundle.getInt("id"));
        auction.setUserId(bundle.getInt("userId"));
        auction.setMaxPrice(bundle.getInt("bin"));
        auction.setMinPrice(bundle.getInt("startPrice"));
        auction.setCurrentPrice(bundle.getInt("currentBid"));
        auction.setKelipatanBid(bundle.getInt("kelipatanBid"));
        auction.setWeight(bundle.getInt("weight"));
        auction.setName(bundle.getString("name"));
        auction.setTitle(bundle.getString("title"));
        auction.setDescription(bundle.getString("description"));
        auction.setCategoryName(bundle.getString("categoryName"));
        auction.setLocation(bundle.getString("location"));
        auction.setProductId(bundle.getString("productId"));
        auction.setStartDate(bundle.getString("startDate"));
        auction.setEndDate(bundle.getString("endDate"));
        auction.setSlug(bundle.getString("slug"));
//        auction.setImages(bundle.getString("image"));

        bidHistories = new ArrayList<>();
        initRecyclerView();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                adapter.clear();
                presenter.bidHistoryRequest(String.valueOf(auction.getId()));
            }
        });

        presenter.bidHistoryRequest(String.valueOf(auction.getId()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        swipeRefresh.setRefreshing(true);
//        adapter.clear();
//        presenter.bidHistoryRequest(String.valueOf(auction.getId()));

    }

    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new BidHistoryAdapter(getActivity(), bidHistories,this);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onBidHistoryLoaded(BidHistoryData data) {
        for (int i = 0;i<data.getBidHistory().size();i++){
            adapter.addItem(data.getBidHistory().get(i),bidHistories.size());
        }
        swipeRefresh.setRefreshing(false);
    }
}
