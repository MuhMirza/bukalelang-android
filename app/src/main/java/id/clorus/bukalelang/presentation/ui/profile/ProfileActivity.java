package id.clorus.bukalelang.presentation.ui.profile;

import android.os.Bundle;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 28/05/17.
 */

public class ProfileActivity extends DefaultActivity{

    @BindView(R.id.avatar)
    RoundedImageView avatar;

    @BindView(R.id.username)
    TextView usernameTv;


    AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        appPreference = Esperandro.getPreferences(AppPreference.class, this);
        ButterKnife.bind(this);

        changeView(R.id.container,new JoinedAuctionFragment());

    }

    public void initToolbar(String avatarUrl,String username){
        try {
            Picasso.with(this)
                    .load(avatarUrl)
                    .error(R.drawable.avatar_default)
                    .placeholder(R.drawable.avatar_default)
                    .into(avatar);
        } catch (Exception e){
            e.printStackTrace();
        }

        usernameTv.setText(username);
    }




    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
