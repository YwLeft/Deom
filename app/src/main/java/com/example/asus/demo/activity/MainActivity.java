package com.example.asus.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.demo.fragment.MyFragment02;
import com.example.asus.demo.fragment.MyFragment03;
import com.example.asus.demo.fragment.MyFragment04;
import com.example.asus.gaozhijie20170922.R;
import com.example.asus.demo.fragment.MyFragment01;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private String[] titles = {"最新日报","专栏","热门","主题日报"};

    private List<Fragment> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {

        list.add( new MyFragment01());
        list.add( new MyFragment03());
        list.add( new MyFragment04());
        list.add( new MyFragment02());


        adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

        //绑定
        tablayout.setupWithViewPager(viewpager);

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
