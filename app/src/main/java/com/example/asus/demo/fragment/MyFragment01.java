package com.example.asus.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.demo.adapter.MyDayAdapter;
import com.example.asus.demo.bean.DayBean;
import com.example.asus.demo.utils.Okhttp;
import com.example.asus.gaozhijie20170922.R;
import com.example.asus.demo.utils.NetDataCallBack;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyFragment01 extends Fragment implements NetDataCallBack<DayBean>, XRecyclerView.LoadingListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.day_recyclerview)
    XRecyclerView dayRecyclerview;
    Unbinder unbinder;
    private List<DayBean.StoriesBean> mlist = new ArrayList<>();
    private List<DayBean.TopStoriesBean> mlistimage = new ArrayList<>();
    private List<String> mimage = new ArrayList<>();

    private String path = "http://news-at.zhihu.com/api/4/news/latest";
    private MyDayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_day, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview(view);
        lodaData();
        return view;
    }

    private void initview(View view) {
        dayRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyDayAdapter(getActivity(), mlist);
        dayRecyclerview.setAdapter(adapter);

        dayRecyclerview.setLoadingListener(this);

        //设置图片加载器
        banner.setImageLoader(new BannerImageLoaders(getActivity()));
        //设置图片集合
        banner.setImages(mimage);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    private void lodaData() {
        Okhttp okhttp = new Okhttp();
        okhttp.getdata(path, this, DayBean.class);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void success(DayBean dayBean) {
        mlist.addAll(dayBean.getStories());
        mlistimage.addAll(dayBean.getTop_stories());
        for (DayBean.TopStoriesBean bean : mlistimage) {
            mimage.add(bean.getImage());
        }
        adapter.notifyDataSetChanged();
        stoprecyclerview();
    }

    private void stoprecyclerview() {
        dayRecyclerview.loadMoreComplete();
        dayRecyclerview.refreshComplete();
    }

    @Override
    public void faild(int positon, String str) {

    }

    @Override
    public void onRefresh() {
        mlist.clear();
        lodaData();
    }

    @Override
    public void onLoadMore() {
        lodaData();
    }

}
