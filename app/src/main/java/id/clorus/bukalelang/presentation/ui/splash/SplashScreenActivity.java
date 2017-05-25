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

    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreference = Esperandro.getPreferences(AppPreference.class, this);

        getCategories();

        if (appPreference.firstTime()){

        } else {
            SplashThread splashThread = new SplashThread();
            splashThread.start();
        }



    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("categories.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void getCategories(){

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Type listType = new TypeToken<ArrayList<Category>>(){}.getType();
                categories = new GsonBuilder().create().fromJson(loadJSONFromAsset(), listType);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                for (int a= 0; a< categories.size();a++){
                    Category category1 = categories.get(a);
                    Log.d("category name level 1",category1.getName());

                    if (categories.get(a).getChildren().size() >0){
                        for (int b= 0; b< categories.get(a).getChildren().size();b++){
                            Category category2 = categories.get(a).getChildren().get(b);
                            Log.d("category name level 2",category2.getName());

                            if (categories.get(a).getChildren().get(b).getChildren().size() >0){
                                for (int c= 0; c< categories.get(a).getChildren().get(b).getChildren().size();c++){
                                    Category category3 = categories.get(a).getChildren().get(b).getChildren().get(c);
                                    Log.d("category name level 3",category3.getName());

                                }
                            }


                        }
                    }


                }

//                appPreference.firstTime(false);
//                SplashThread splashThread = new SplashThread();
//                splashThread.start();

            }
        }.execute();
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
