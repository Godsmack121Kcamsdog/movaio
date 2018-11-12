package io.mova.kucherenko.gettyimages;

import android.app.Application;
import android.content.res.Resources;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private Resources res;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        res = getResources();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("getty_image.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    public static App getInstance() {
        return instance;
    }


    /**
     * Simplifying of getting application resources
     * @return resources
     */
    public Resources getMResources() {
        return res;
    }
}
