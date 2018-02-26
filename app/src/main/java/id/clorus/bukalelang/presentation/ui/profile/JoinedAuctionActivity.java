package id.clorus.bukalelang.presentation.ui.profile;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 28/05/17.
 */

public class JoinedAuctionActivity extends DefaultActivity{


    AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_auction);

        appPreference = Esperandro.getPreferences(AppPreference.class, this);
        ButterKnife.bind(this);

        changeView(R.id.container,new JoinedAuctionFragment());

    }



    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
