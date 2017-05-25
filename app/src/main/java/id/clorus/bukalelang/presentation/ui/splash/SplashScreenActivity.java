package id.clorus.bukalelang.presentation.ui.splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.categories.Category;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.HomeActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 15/04/17.
 */

public class SplashScreenActivity extends DefaultActivity {

    AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreference = Esperandro.getPreferences(AppPreference.class, this);

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
