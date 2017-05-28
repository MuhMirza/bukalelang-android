package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.AddBidStatusData;
import id.clorus.bukalelang.data.entity.response.ItemAuctionData;
import id.clorus.bukalelang.data.entity.response.TimeLeftData;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.ui.auth.AuthActivity;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.AuctionListAdapter;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class AuctionDetailActivity extends DefaultActivity implements AuctionDetailView {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.btn_bid)
    Button btnOpenBidMenu;

    AppPreference appPreference;

    Auction auction;

    @BindView(R.id.timeleft)
    TextView countdownTimerText;

    AuctionDetailPresenter presenter;

    CountDownTimer countDownTimer;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auction_detail);
        ButterKnife.bind(this);
        appPreference = Esperandro.getPreferences(AppPreference.class,this);

        presenter = new AuctionDetailPresenter(this);

        auction = new Auction();

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        if (data != null){
            initDeepLink(data);
        } else {
            bundle = getIntent().getExtras();
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
            auction.setTimeLeft(bundle.getInt("timeleft"));
            auction.setAvatarUrl(bundle.getString("avatarUrl"));
            auction.setBidderCount(bundle.getInt("bidderCount"));
            
            timer(auction.getTimeLeft());
            countDownTimer.start();
            if ((auction.getTimeLeft() <= 0) || (auction.getCurrentPrice() >= auction.getMaxPrice()) ){
                countdownTimerText.setText("LELANG TELAH BERAKHIR");
                countDownTimer.cancel();
                btnOpenBidMenu.setText("LELANG INI TELAH BERAKHIR");
                btnOpenBidMenu.setClickable(false);
            }


            setupViewPager(viewPager);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#cb0051"));
            tabLayout.setSelectedTabIndicatorHeight(2);
            tabLayout.setupWithViewPager(viewPager);
            setupTab();

            if (auction.getUserId() == appPreference.id()){
                btnOpenBidMenu.setText("Anda tidak bisa bid di lelang sendiri");
                btnOpenBidMenu.setClickable(false);
            }

        }


    }

    public void initDeepLink(Uri data){

            Log.d("deeplink",data.getLastPathSegment());

            RestService.Factory.getInstance().getItemAuctionBySlug("auctions/slug/"+data.getLastPathSegment()).enqueue(new Callback<Auction>() {
                @Override
                public void onResponse(Call<Auction> call, Response<Auction> response) {

                    Log.d("request","processed");

                    Auction data = response.body();
                    auction = data;
                    Log.d("title",data.getTitle());
                    Log.d("timeleft", String.valueOf(data.getTimeLeft()));
                    Log.d("images", String.valueOf(data.getImages()));
                    setBundle(data);

                    setupViewPager(viewPager);
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#cb0051"));
                    tabLayout.setSelectedTabIndicatorHeight(2);
                    tabLayout.setupWithViewPager(viewPager);
                    setupTab();

                    if (auction.getUserId() == appPreference.id()){
                        btnOpenBidMenu.setText("Anda tidak bisa bid di lelang sendiri");
                        btnOpenBidMenu.setClickable(false);
                    }

                    timer(data.getTimeLeft());
                    countDownTimer.start();
                    if ((auction.getTimeLeft() <= 0) || (auction.getCurrentPrice() >= auction.getMaxPrice()) ){
                        countdownTimerText.setText("LELANG TELAH BERAKHIR");
                        countDownTimer.cancel();
                        btnOpenBidMenu.setText("LELANG INI TELAH BERAKHIR");
                        btnOpenBidMenu.setClickable(false);
                    }


//                    presenter.getTimeLeft(auction.getId());



                }

                @Override
                public void onFailure(Call<Auction> call, Throwable t) {
                    Log.d("request","failed");

                }
            });



    }

    public void setBundle(Auction data){

        bundle = new Bundle();

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

    }
    public void setCurrentBid(int nominal){
        auction.setCurrentPrice(nominal);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void timer(int timeleft){

        countDownTimer = new CountDownTimer(timeleft,10) {
            @Override
            public void onTick(long millisUntilFinished) {

                long millis = millisUntilFinished;

                long seconds = millis / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                String time;
                if (days > 0){
                    time = days + " Hari, " + hours % 24 + " Jam," + minutes % 60 + " Menit lagi";
                } else {
                    time = hours % 24 + " Jam," + minutes % 60 + " Menit lagi";
                }
//                time = days + " Hari, " + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;


                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(time);
            }

            @Override
            public void onFinish() {

            }
        };

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        productDetailFragment.setArguments(bundle);
        BidHistoryFragment bidHistoryFragment = new BidHistoryFragment();
        bidHistoryFragment.setArguments(bundle);

        adapter.addFrag(productDetailFragment, "ONE");
        adapter.addFrag(bidHistoryFragment, "TWO");

        viewPager.setAdapter(adapter);

//        viewPager.setCurrentItem(bundle.getInt("page"),true);
    }

    private void setupTab() {

        tabLayout.getTabAt(0).setText("Detail");
        tabLayout.getTabAt(1).setText("Daftar Bid");
        tabLayout.setTabTextColors(ContextCompat.getColor(getBaseContext(), R.color.grey_dark), ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onTimeLeftLoaded(TimeLeftData data) {
        countDownTimer.cancel();

        if ((data.getTimeLeft() <= 0) || (auction.getCurrentPrice() >= auction.getMaxPrice()) ){
            countdownTimerText.setText("LELANG TELAH BERAKHIR");
            countDownTimer.cancel();
            btnOpenBidMenu.setText("LELANG INI TELAH BERAKHIR");
//            btnOpenBidMenu.setVisibility(View.GONE);
            btnOpenBidMenu.setClickable(false);

        } else {
            timer(data.getTimeLeft());
            auction.setTimeLeft(data.getTimeLeft());
            countDownTimer.start();
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return null to display only the icon
            return null;
        }
    }

    public String tagFragment(int position){
        return "android:switcher:" + R.id.viewPager + ":" + position;
    }

    @OnClick(R.id.btn_bid)
    public void bid(){

        if (!appPreference.loggedIn()){
            Intent intent = new Intent(AuctionDetailActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        }

        BidMenuBottomSheetFragment menuBottomSheetFragment = new BidMenuBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", auction.getId());
        bundle.putInt("userId",auction.getUserId());
        bundle.putInt("bin",auction.getMaxPrice());
        bundle.putInt("startPrice",auction.getMinPrice());
        bundle.putInt("currentBid",auction.getCurrentPrice());
        bundle.putInt("kelipatanBid",auction.getKelipatanBid());
        bundle.putInt("weight",auction.getWeight());
        bundle.putString("name", auction.getName());
        bundle.putString("title",auction.getTitle());
        bundle.putString("description",auction.getDescription());
        bundle.putString("categoryName",auction.getCategoryName());
        bundle.putString("location",auction.getLocation());
        bundle.putString("productId",auction.getProductId());
        bundle.putString("startDate",auction.getStartDate());
        bundle.putString("endDate",auction.getEndDate());
        bundle.putString("slug",auction.getSlug());
        bundle.putInt("timeleft",auction.getTimeLeft());
        bundle.putInt("bidderCount",auction.getBidderCount());
        bundle.putString("avatarUrl",auction.getAvatarUrl());

        menuBottomSheetFragment.setArguments(bundle);
        menuBottomSheetFragment.show(getSupportFragmentManager(),R.id.bottomsheet);

    }

    @OnClick(R.id.btn_share)
    public void share(){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Yuk ikut bid di lelang "+auction.getTitle()+". Bid terakhir baru Rp."+String.valueOf(auction.getCurrentPrice())+" loh!. cekidot http://web.bukalelang.id/lelang/"+auction.getSlug());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

    }

    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
