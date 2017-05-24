package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import id.clorus.bukalelang.data.entity.response.TimeLeftData;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.net.RestService;
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

    AppPreference appPreference;

    Auction auction;

    @BindView(R.id.timeleft)
    TextView countdownTimerText;

    AuctionDetailPresenter presenter;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auction_detail);
        ButterKnife.bind(this);
        appPreference = Esperandro.getPreferences(AppPreference.class,this);

        presenter = new AuctionDetailPresenter(this);

        Bundle bundle = getIntent().getExtras();
        auction = new Auction();

        auction.setId(bundle.getInt("id"));
        auction.setUserId(bundle.getInt("userId"));
        auction.setMaxPrice(bundle.getInt("bin"));
        auction.setMinPrice(bundle.getInt("startPrice"));
        auction.setCurrentPrice(bundle.getInt("currentBid"));
        auction.setKelipatanBid(bundle.getInt("kelipatanBid"));
        auction.setWeight(bundle.getInt("weight"));
        auction.setTimeLeft(bundle.getInt("timeleft"));
        auction.setName(bundle.getString("name"));
        auction.setTitle(bundle.getString("title"));
        auction.setDescription(bundle.getString("description"));
        auction.setCategoryName(bundle.getString("categoryName"));
        auction.setLocation(bundle.getString("location"));
        auction.setProductId(bundle.getString("productId"));
        auction.setStartDate(bundle.getString("startDate"));
        auction.setEndDate(bundle.getString("endDate"));
        auction.setSlug(bundle.getString("slug"));
//        auction.setImages(bundle.getString("images"));

        timer(auction.getTimeLeft());

        setupViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#cb0051"));
        tabLayout.setSelectedTabIndicatorHeight(2);
        tabLayout.setupWithViewPager(viewPager);
        setupTab();

    }

    @Override
    protected void onResume() {
        super.onResume();


        presenter.getTimeLeft(auction.getId());
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
                String time = days + " Hari, " + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;


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

        adapter.addFrag(new ProductDetailFragment(), "ONE");
        adapter.addFrag(new BidHistoryFragment(), "ONE");

//        FragmentVenueInfo fragmentVenueInfo = new FragmentVenueInfo();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", venue.getId());
//        fragmentVenueInfo.setArguments(bundle);
//        adapter.addFrag(fragmentVenueInfo, "TWO");

        viewPager.setAdapter(adapter);

//        viewPager.setCurrentItem(bundle.getInt("page"),true);
    }

    private void setupTab() {

        tabLayout.getTabAt(0).setText("Detail");
        tabLayout.getTabAt(1).setText("Bid History");
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
        timer(data.getTimeLeft());
        countDownTimer.start();
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
        menuBottomSheetFragment.setArguments(bundle);
        menuBottomSheetFragment.show(getSupportFragmentManager(),R.id.bottomsheet);

    }
}
