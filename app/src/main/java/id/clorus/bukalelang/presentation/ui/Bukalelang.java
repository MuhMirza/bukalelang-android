package id.clorus.bukalelang.presentation.ui;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.net.URISyntaxException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by mirza on 25/05/17.
 */

public class Bukalelang extends MultiDexApplication {
    public static final String TAG = Bukalelang.class
            .getSimpleName();

    private static Bukalelang mInstance;

    private Socket socket;
    {
        try {
            socket = IO.socket("http://studio.tealinuxos.org:3000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        try {
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build();
            Realm.setDefaultConfiguration(config);

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized Bukalelang getInstance() {
        return mInstance;
    }





}