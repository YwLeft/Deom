package com.example.asus.demo.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(5)
                .threadPriority(1000)
                .build();
        ImageLoader.getInstance().init(configuration);
        x.Ext.init(this);

    }


}
