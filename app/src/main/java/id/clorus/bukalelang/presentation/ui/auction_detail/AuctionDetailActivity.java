package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.AddBidStatusData;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 23/05/17.
 */

public class AuctionDetailActivity extends DefaultActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    AppPreference appPreference;

    Auction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auction_detail);
        ButterKnife.bind(this);
        appPreference = Esperandro.getPreferences(AppPreference.class,this);

        Bundle bundle = getIntent().getExtras();
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
        auction.setImages(bundle.getString("image"));

        setupViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setupWithViewPager(viewPager);
        setupTab();

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

        Log.d("userId",appPreference.id()+"token :"+appPreference.accessToken());

        RestService.Factory.getInstance().addNewBid(auction.getId(),100000,appPreference.id(),appPreference.accessToken()).enqueue(new Callback<AddBidStatusData>() {
            @Override
            public void onResponse(Call<AddBidStatusData> call, Response<AddBidStatusData> response) {
                showToast(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<AddBidStatusData> call, Throwable t) {

            }
        });

    }
}
