package id.clorus.bukalelang.presentation.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.data.entity.response.joined_auction.JoinedAuctionData;
import id.clorus.bukalelang.presentation.ui.auction_detail.AuctionDetailActivity;
import id.clorus.bukalelang.presentation.ui.auction_detail.BidHistoryPresenter;
import id.clorus.bukalelang.presentation.ui.base.DefaultFragment;;
import id.clorus.bukalelang.presentation.ui.home.HomeView;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 28/05/17.
 */

public class JoinedAuctionFragment extends DefaultFragment implements JoinedAuctionView {

    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<Auction> auctions;
    private AuctionListAdapter adapter;
    private LinearLayoutManager layoutManager;

    JoinedAuctionPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joined_auction, container, false);
        ButterKnife.bind(this,view);
        appPreference = Esperandro.getPreferences(AppPreference.class, getActivity());
        presenter = new JoinedAuctionPresenter(this,getActivity());

        Bundle bundle = getActivity().getIntent().getExtras();
        String userId = bundle.getString("userId");

        auctions = new ArrayList<>();
        initRecyclerView();
        presenter.getJoinedAuction(userId);
        return view;
    }

    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new AuctionListAdapter(getActivity(), auctions,this);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onAllAuctionLoaded(JoinedAuctionData data) {
        Log.d("message",data.getMessage());
        adapter.clear();

        for (int i = 0;i<data.getAuctionsJoined().size();i++){
            adapter.addItem(data.getAuctionsJoined().get(i),auctions.size());
        }

        ((ProfileActivity) getActivity()).initToolbar(data.getUserDetail().getAvatarUrl(),data.getUserDetail().getName());


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
        bundle.putInt("bidderCount",data.getBidderCount());
        bundle.putString("avatarUrl",data.getAvatarUrl());

        String images = data.getImages().toString();
        images = images.substring(1,images.length()-1);

        bundle.putString("images",images);
        Log.d("images",images);

        Intent intent = new Intent(getActivity(), AuctionDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
