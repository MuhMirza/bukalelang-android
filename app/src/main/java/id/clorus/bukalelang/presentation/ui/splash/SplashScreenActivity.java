package id.clorus.bukalelang.presentation.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.HomeActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 15/04/17.
 */

public class SplashScreenActivity extends DefaultActivity {

    AppPreference appPreference;

    Bundle bundle;
    String slug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreference = Esperandro.getPreferences(AppPreference.class, this);

        /*
        bundle = getIntent().getExtras();

        if (bundle != null){
            slug = bundle.getString("slug");

            Bundle b = new Bundle();
            b.putString("slug",slug);

            Intent intent = new Intent();
            intent.putExtras(b);
            intent.setClass(SplashScreenActivity.this, AuctionDetailFromNotifActivity.class);
            finish();
            startActivity(intent);
        } else {
            SplashThread splashThread = new SplashThread();
            splashThread.start();
        }*/

        SplashThread splashThread = new SplashThread();
        splashThread.start();






    }


    class SplashThread extends Thread {
        @Override
        public void run() {
            try {
                synchronized (this) {
                    wait(800);
                }
            } catch (InterruptedException e) {
//                Log.d(TAG, e.getMessage());
            }

                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);

        }
    }
}
