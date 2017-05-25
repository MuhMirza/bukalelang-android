package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.AddBidStatusData;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.data.net.RestService;
import id.clorus.bukalelang.presentation.utils.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 24/05/17.
 */

public class BidMenuBottomSheetFragment extends BottomSheetFragment {

    AppPreference appPreference;

    Auction auction;

    @BindView(R.id.btn_bid_1)
    Button btnBid1;

    @BindView(R.id.btn_bid_2)
    Button btnBid2;

    @BindView(R.id.btn_bid_3)
    Button btnBid3;

    @BindView(R.id.btn_bin)
    Button btnBin;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    int nominalBid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottomsheet_bid_menu, container, false);
        appPreference = Esperandro.getPreferences(AppPreference.class, getActivity());

        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

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
        }

        if (auction.getCurrentPrice()+auction.getKelipatanBid() < auction.getMaxPrice()){
            btnBid1.setText("Rp."+String.valueOf(auction.getCurrentPrice()+auction.getKelipatanBid()));
        } else btnBid1.setText("Rp."+String.valueOf(auction.getMaxPrice()));

        if ((auction.getCurrentPrice()+(2*auction.getKelipatanBid())) < auction.getMaxPrice()){
            btnBid2.setText("Rp."+String.valueOf(auction.getCurrentPrice()+(2*auction.getKelipatanBid())));
        } else btnBid2.setText("Rp."+String.valueOf(auction.getMaxPrice()));

        if ((auction.getCurrentPrice()+(3*auction.getKelipatanBid())) < auction.getMaxPrice()){
            btnBid3.setText("Rp."+String.valueOf(auction.getCurrentPrice()+(3*auction.getKelipatanBid())));
        } else btnBid3.setText("Rp."+String.valueOf(auction.getMaxPrice()));

        btnBin.setText("Rp."+String.valueOf(auction.getMaxPrice()));

        return view;
    }

    @OnClick(R.id.btn_bid_1)
    public void bid1(){
        int colorActive = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        int white = ContextCompat.getColor(getActivity(), R.color.white);
        btnBid1.setBackgroundColor(colorActive);
        btnBid1.setTextColor(white);
        nominalBid = auction.getCurrentPrice()+auction.getKelipatanBid();
        btnSubmit.setText("BID Rp."+String.valueOf(nominalBid));
        if (nominalBid > auction.getMaxPrice()) btnSubmit.setText("BIN Rp."+String.valueOf(auction.getMaxPrice()));
        btnBid2.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid2.setTextColor(colorActive);
        btnBid3.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid3.setTextColor(colorActive);
        btnBin.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBin.setTextColor(colorActive);
    }

    @OnClick(R.id.btn_bid_2)
    public void bid2(){
        int colorActive = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        int white = ContextCompat.getColor(getActivity(), R.color.white);
        btnBid2.setBackgroundColor(colorActive);
        btnBid2.setTextColor(white);
        nominalBid = auction.getCurrentPrice()+(2*auction.getKelipatanBid());
        btnSubmit.setText("BID Rp."+String.valueOf(nominalBid));
        if (nominalBid > auction.getMaxPrice()) btnSubmit.setText("BIN Rp."+String.valueOf(auction.getMaxPrice()));
        btnBid1.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid1.setTextColor(colorActive);
        btnBid3.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid3.setTextColor(colorActive);
        btnBin.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBin.setTextColor(colorActive);
    }


    @OnClick(R.id.btn_bid_3)
    public void bid3(){
        int colorActive = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        int white = ContextCompat.getColor(getActivity(), R.color.white);
        btnBid3.setBackgroundColor(colorActive);
        btnBid3.setTextColor(white);
        nominalBid = auction.getCurrentPrice()+(3*auction.getKelipatanBid());
        btnSubmit.setText("BID Rp."+String.valueOf(nominalBid));
        if (nominalBid > auction.getMaxPrice()) btnSubmit.setText("BIN Rp."+String.valueOf(auction.getMaxPrice()));
        btnBid1.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid1.setTextColor(colorActive);
        btnBid2.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid2.setTextColor(colorActive);
        btnBin.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBin.setTextColor(colorActive);
    }

    @OnClick(R.id.btn_bin)
    public void bin(){
        int colorActive = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        int white = ContextCompat.getColor(getActivity(), R.color.white);
        btnBin.setBackgroundColor(colorActive);
        btnBin.setTextColor(white);
        nominalBid = auction.getMaxPrice();
        btnSubmit.setText("BIN Rp."+String.valueOf(nominalBid));
        btnBid1.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid1.setTextColor(colorActive);
        btnBid3.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid3.setTextColor(colorActive);
        btnBid2.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_edittext_primary_color_border));
        btnBid2.setTextColor(colorActive);
    }

    @OnClick(R.id.btn_submit)
    public void bid(){

        Log.d("userId",appPreference.id()+"token :"+appPreference.accessToken());

        RestService.Factory.getInstance().addNewBid(auction.getId(),nominalBid,appPreference.id(),appPreference.accessToken()).enqueue(new Callback<AddBidStatusData>() {
            @Override
            public void onResponse(Call<AddBidStatusData> call, Response<AddBidStatusData> response) {
//                showToast(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<AddBidStatusData> call, Throwable t) {

            }
        });

    }

}
