package com.example.asus.demo.fragment;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class BannerImageLoaders extends com.youth.banner.loader.ImageLoader{
    public BannerImageLoaders(FragmentActivity fragment1) {
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
