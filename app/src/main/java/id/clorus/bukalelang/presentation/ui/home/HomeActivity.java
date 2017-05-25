package id.clorus.bukalelang.presentation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.entity.response.auctions.AuctionData;
import id.clorus.bukalelang.presentation.ui.auction_create.CreateAuctionActivity;
import id.clorus.bukalelang.presentation.ui.auction_detail.AuctionDetailActivity;
import id.clorus.bukalelang.presentation.ui.auth.AuthActivity;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

public class HomeActivity extends DefaultActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeView {

    AppPreference appPreference;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private AuctionListAdapter adapter;
    private LinearLayoutManager layoutManager;
    ArrayList<Auction> auctions;
    HomePresenter presenter;

    static int page;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

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
//                auctions.clear();
                adapter.clear();
                presenter.getAllAuctions(1,5);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        presenter.getAllAuctions(1,10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_auth){
            Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bikin_lelang){
            Intent intent = new Intent(HomeActivity.this, CreateAuctionActivity.class);
            startActivity(intent);
        }

        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

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
//        auctions.addAll(data.getAuctions());

        for (int i = 0;i<data.getAuctions().size();i++){
            adapter.addItem(data.getAuctions().get(i),auctions.size());
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


        String images = data.getImages().toString();
        images = images.substring(1,images.length()-1);

        bundle.putString("images",images);
        Log.d("images",images);

        Intent intent = new Intent(HomeActivity.this, AuctionDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}
