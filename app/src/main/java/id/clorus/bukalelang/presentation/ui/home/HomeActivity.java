package id.clorus.bukalelang.presentation.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.presentation.ui.Bukalelang;
import id.clorus.bukalelang.presentation.ui.auction_create.CreateAuctionActivity;
import id.clorus.bukalelang.presentation.ui.auction_create_from_lapak.SelectProductActivity;
import id.clorus.bukalelang.presentation.ui.auction_detail.AuctionDetailActivity;
import id.clorus.bukalelang.presentation.ui.auth.AuthActivity;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.my_auctions.MyAuctionActivity;
import id.clorus.bukalelang.presentation.ui.won_auction.WonAuctionActivity;
import id.clorus.bukalelang.presentation.ui.profile.JoinedAuctionActivity;
import id.clorus.bukalelang.presentation.ui.search_auction.SearchAuctionActivity;
import id.clorus.bukalelang.presentation.ui.splash.SplashScreenActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;

public class HomeActivity extends DefaultActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeView {

    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    RoundedImageView avatar;
    TextView username;
    TextView email;
    Button btnAuth;

    @BindView(R.id.nav_footer_auth)
    TextView authBtn;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.fab_new_auction)
    FloatingActionButton fab;

    @BindView(R.id.maintenance_logo)
    ImageView maintenanceLogo;

    private AuctionListAdapter adapter;
    private LinearLayoutManager layoutManager;
    ArrayList<Auction> auctions;
    HomePresenter presenter;

    static int page;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    NavigationView navigationView;

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPreference = Esperandro.getPreferences(AppPreference.class, this);
        ButterKnife.bind(this);

        presenter = new HomePresenter(this,this);
        auctions = new ArrayList<>();
        initRecyclerView();
        page = 1;

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                presenter.getAllAuctions(1,5);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        avatar = (RoundedImageView) navigationView.getHeaderView(0).findViewById(R.id.user_photo);
        email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        btnAuth = (Button) navigationView.getHeaderView(0).findViewById(R.id.btn_auth);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId",String.valueOf(appPreference.id()));
                intent.putExtras(bundle);
                startActivity(intent);*/

            }
        });

        if (appPreference.loggedIn()){

            authBtn.setText("LogOut");
//            email.setText(appPreference.email());
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            email.setText("saldo : "+formatRupiah.format(appPreference.saldo()));
            username.setText(appPreference.username());

            navigationView.getMenu().findItem(R.id.nav_bikin_lelang).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_joined_auction).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_won_auction).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_lelang_ku).setVisible(true);

            btnAuth.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            avatar.setVisibility(View.VISIBLE);
            btnAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this,AuthActivity.class);
                    startActivity(intent);
                }
            });
            fab.setVisibility(View.VISIBLE);
        } else {
            authBtn.setText("Login");

            navigationView.getMenu().findItem(R.id.nav_bikin_lelang).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_joined_auction).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_won_auction).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_lelang_ku).setVisible(false);

            btnAuth.setVisibility(View.VISIBLE);
            username.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            avatar.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);

        }

        initWs();

        presenter.getAllAuctions(1,10);
        Log.d("userId", String.valueOf(appPreference.id()));
    }

    @OnClick(R.id.nav_footer_auth)
    public void logout(){

        if (appPreference.loggedIn()){

        appPreference.loggedIn(false);
            appPreference.accessToken("");
            appPreference.bukalapakId(0);
            appPreference.id(0);
            appPreference.saldo(0);
            appPreference.email("");
            appPreference.username("");
            appPreference.basicToken("");
            appPreference.photoUrl("");
            appPreference.isHaveAddress(false);

        Intent intent = new Intent(HomeActivity.this, SplashScreenActivity.class);
        startActivity(intent);
        finish();

        } else {
            Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @OnClick(R.id.btn_search)
    public void search(){
        Intent intent = new Intent(HomeActivity.this, SearchAuctionActivity.class);
        startActivity(intent);
    }
    public void initWs(){

        Bukalelang bukalelang = (Bukalelang) getApplication();
        socket = bukalelang.getSocket();

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
//                    Log.d("message",args[0].toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).on("new-auction", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
                    JSONObject obj = (JSONObject)args[0];
                    Log.d("auction",obj.toString());

                    newAuction(obj);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).on("chat message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
//                    Log.d("message",args[0].toString());
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

    private void newAuction(final JSONObject obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Auction auction = new Auction();
                try {
                    auction.setTitle(obj.getString("title"));
                    auction.setDescription(obj.getString("description"));
//                  auction.setName(obj.getString("name"));
                    auction.setId(obj.getInt("id"));
                    auction.setTimeLeft(obj.getInt("time_left"));
                    auction.setCategoryName(obj.getString("categoryName"));
                    auction.setEndDate(obj.getString("end_date"));
                    auction.setCurrentPrice(obj.getInt("current_price"));
                    auction.setMaxPrice(obj.getInt("max_price"));
                    auction.setKelipatanBid(obj.getInt("kelipatan_bid"));
                    auction.setWeight(obj.getInt("weight"));
                    auction.setUserId(obj.getInt("userId"));
                    auction.setIsRunning(obj.getInt("isRunning"));
                    auction.setSlug(obj.getString("slug"));
                    auction.setLocation(obj.getString("location"));
                    auction.setProductId(obj.getString("productId"));
                    auction.setBidderCount(obj.getInt("bidderCount"));
                    auction.setAvatarUrl(obj.getString("avatarUrl"));

                    List<String> images = new ArrayList<String>();
                    JSONArray arrayImages =  obj.getJSONArray("images");
                    for (int i = 0;i < arrayImages.length();i++){
                        images.add(arrayImages.get(i).toString());
                    }
                    auction.setImages(images);

                    showToast("Ada Lelang baru!");

                    adapter.addItem(auction,0);
                    layoutManager.smoothScrollToPosition(recyclerView,null,0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void output(final String txt) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(txt);
            }
        });
    }

    @OnClick(R.id.fab_new_auction)
    public void createAuction(){
        createDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (appPreference.loggedIn()){
            authBtn.setText("LogOut");
            navigationView.getMenu().findItem(R.id.nav_bikin_lelang).setVisible(true);

            btnAuth.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            avatar.setVisibility(View.VISIBLE);
            btnAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this,AuthActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            authBtn.setText("Login");
            navigationView.getMenu().findItem(R.id.nav_bikin_lelang).setVisible(true);

            btnAuth.setVisibility(View.VISIBLE);
            username.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            avatar.setVisibility(View.GONE);

        }

        try {
            Picasso.with(this)
                    .load(appPreference.photoUrl())
                    .error(R.drawable.avatar_default)
                    .placeholder(R.drawable.avatar_default)
                    .into(avatar);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bikin_lelang){

            createDialog();

        } else if (id == R.id.nav_won_auction){
            Intent intent = new Intent(HomeActivity.this, WonAuctionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId",String.valueOf(appPreference.id()));
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_lelang_ku){
            Intent intent = new Intent(HomeActivity.this, MyAuctionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId",String.valueOf(appPreference.id()));
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id == R.id.nav_joined_auction){
            Bundle bundle = new Bundle();
            bundle.putString("userId",String.valueOf(appPreference.id()));

//            changeView(R.id.container,new JoinedAuctionFragment(),bundle);

            Intent intent = new Intent(HomeActivity.this, JoinedAuctionActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initRecyclerView(){

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new AuctionListAdapter(this, auctions,this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                     if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount){
                            Log.v("...", "Last Item Wow !");
                            page++;
                            presenter.getAllAuctions(page,5);

                        }

                }


            }
        });

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onAllAuctionLoaded(AuctionData data) {
        Log.d("message",data.getMessage());
//        auctions.addAll(data.getAuctions());
        if (data.getAuctions().size() > 0){
            for (int i = 0;i<data.getAuctions().size();i++){
                adapter.addItem(data.getAuctions().get(i),auctions.size());
                maintenanceLogo.setVisibility(View.GONE);
            }
        }
//        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

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
        bundle.putInt("bidderCount",data.getBidderCount());
        bundle.putString("avatarUrl",data.getAvatarUrl());

        String images = data.getImages().toString();
        images = images.substring(1,images.length()-1);

        bundle.putString("images",images);
        Log.d("images",images);

        Intent intent = new Intent(HomeActivity.this, AuctionDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void createDialog(){

        if (!appPreference.isHaveAddress()){
            showToast("Kamu harus melengkapi alamat terlebih dulu via aplikasi bukalapak");
        } else {

            final String[] items = getResources().getStringArray(R.array.auction_create);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection
                    switch (item) {
                        case 0:
                            Intent intent = new Intent(HomeActivity.this,CreateAuctionActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent i = new Intent(HomeActivity.this,SelectProductActivity.class);
                            startActivity(i);
                            break;
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();


        }



    }

}
