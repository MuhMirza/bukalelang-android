package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.presentation.ui.base.DefaultFragment;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import id.clorus.bukalelang.presentation.utils.TextViewExpandableAnimation;

/**
 * Created by mirza on 23/05/17.
 */

public class ProductDetailFragment extends DefaultFragment implements ViewPager.OnPageChangeListener{

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    private GalleryPagerAdapter mAdapter;

    @BindView(R.id.product_description)
    TextViewExpandableAnimation description;

    @BindView(R.id.auction_title)
    TextView title;

    private int dotsCount;
    private ImageView[] dots;

    Unbinder unbinder;
    AppPreference appPreference;

    List<String> listPhotos;

    Auction auction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        unbinder = ButterKnife.bind(this,view);
        appPreference = Esperandro.getPreferences(AppPreference.class, getActivity());

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

        String images = bundle.getString("images");
        images = images.replaceAll("\\s","");
        String[] items = images.split(",");
        listPhotos = Arrays.asList(items);
        for (String photo : listPhotos){
            Log.d("photo",photo);
        }

        title.setText(auction.getTitle());
        description.setText(auction.getDescription());
        description.resetState(true);

        initGalleryViewPager(listPhotos);

        return view;
    }

    public void initGalleryViewPager(List<String> listPhotos) {
        //Init viewPager
        mAdapter = new GalleryPagerAdapter(getActivity(), listPhotos);
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setOnPageChangeListener(this);
        indicator.setViewPager(viewpager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private static class GalleryPagerAdapter extends PagerAdapter {

        @NonNull
        final List<String> mObjects;
        @NonNull
        final Context mContext;

        public GalleryPagerAdapter(@NonNull final Context context, @NonNull final List<String> objects) {
            mContext = context;
            mObjects = objects;
        }

        @Override
        public int getCount() {
            return mObjects.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }



        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);
            final ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            container.addView(itemView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("pager clicked","pos"+position);
                }
            });

            Picasso.with(mContext)
                    .load(mObjects.get(position))
                    .error(R.color.dark_red)
                    .placeholder(R.color.grey_dark)
                    .into(imageView);


            return itemView;
        }
    }
}
