package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.bids.BidHistory;
import id.clorus.bukalelang.data.entity.response.bids.BidHistoryData;
import id.clorus.bukalelang.presentation.ui.Bukalelang;
import id.clorus.bukalelang.presentation.ui.base.DefaultFragment;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;

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

    @BindView(R.id.no_bid_status)
    ImageView noBidStatus;

    private BidHistoryAdapter adapter;
    private LinearLayoutManager layoutManager;
    ArrayList<BidHistory> bidHistories;

    BidHistoryPresenter presenter;

    Auction auction;

    Socket socket;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bid_history, container, false);
        unbinder = ButterKnife.bind(this,view);
        appPreference = Esperandro.getPreferences(AppPreference.class, getActivity());
        presenter = new BidHistoryPresenter(this,getActivity());

        Bundle bundle = this.getArguments();
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
        auction.setAvatarUrl(bundle.getString("avatarUrl"));

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
        initWs();

        if ((auction.getTimeLeft() <= 0) || (auction.getCurrentPrice() >= auction.getMaxPrice()) ){
            container.setPadding(0,0,0,0);
        }

        return view;
    }

    public void initWs(){

        Bukalelang bukalelang = (Bukalelang) getActivity().getApplication();
        socket = bukalelang.getSocket();

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                JSONObject obj = (JSONObject)args[0];
//                showToast(obj.toString());

                try {
                    Log.d("message",args[0].toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).on("auction-"+auction.getId(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
//                    output("Ada Bid Baru!!");
                    JSONObject obj = (JSONObject)args[0];
                    Log.d("message",obj.toString());

                    newBidAdded(obj);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).on("chat message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
                    Log.d("message",args[0].toString());
                    output(args[0].toString());
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
            }
        }).on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Transport transport = (Transport) args[0];
                transport.on(Transport.EVENT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Exception e = (Exception) args[0];
                        Log.e("TEST", "transport error " + e);
                        e.printStackTrace();
                        e.getCause().printStackTrace();
                    }
                });
            }
        });
        socket.connect();
    }


    private void output(final String txt) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(txt);
            }
        });
    }

    private void newBidAdded(final JSONObject obj) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BidHistory newBid = new BidHistory();
                try {
                    newBid.setNameOfBidder(obj.getString("name"));
                    newBid.setBidNominal(obj.getInt("current_price"));
                    newBid.setBiddingTime(obj.getString("bidding_time"));
                    newBid.setAvatarUrl(obj.getString("avatarUrl"));

                    showToast("Ada bid baru!");

                    try {
                        ((AuctionDetailActivity) getActivity()).setCurrentBid(newBid.getBidNominal());
                        ((AuctionDetailActivity) getActivity()).cekStatusAuction();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try {
                        ((AuctionDetailFromNotifActivity) getActivity()).setCurrentBid(newBid.getBidNominal());
                        ((AuctionDetailFromNotifActivity) getActivity()).cekStatusAuction();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    adapter.addItem(newBid,0);

                    try {
                        if (bidHistories.size() > 6){
                            layoutManager.scrollToPosition(0);
                            layoutManager.smoothScrollToPosition(recyclerView, null, 0);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (data.getBidHistory().size() > 0) {
            for (int i = 0;i<data.getBidHistory().size();i++){
                adapter.addItem(data.getBidHistory().get(i),bidHistories.size());
            }
            noBidStatus.setVisibility(View.GONE);
        }
        swipeRefresh.setRefreshing(false);

    }
}
