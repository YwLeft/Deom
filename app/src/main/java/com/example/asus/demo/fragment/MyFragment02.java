package com.example.asus.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.demo.activity.NextActivity;
import com.example.asus.demo.bean.ZhuBean;
import com.example.asus.demo.utils.Okhttp;
import com.example.asus.gaozhijie20170922.R;
import com.example.asus.demo.adapter.MyZhuAdapter;
import com.example.asus.demo.utils.NetDataCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyFragment02 extends Fragment implements NetDataCallBack<ZhuBean> {

    @BindView(R.id.zhu_recyclerview)
    RecyclerView zhuRecyclerview;
    Unbinder unbinder;
    private String path = "http://news-at.zhihu.com/api/4/themes";
    private List<ZhuBean.OthersBean> mlist = new ArrayList<>();
    private MyZhuAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_zhu, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview(view);
        LoadData();
        return view;
    }

    private void LoadData() {
        Okhttp okhttp = new Okhttp();
        okhttp.getdata(path,this,ZhuBean.class);
    }

    private void initview(View view) {
        zhuRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new MyZhuAdapter(getActivity(),mlist);
        zhuRecyclerview.setAdapter(adapter);

        adapter.setSetitemClick(new MyZhuAdapter.SetitemClick() {
            @Override
            public View setitemclick(View view, int position) {

                Intent intent = new Intent(getActivity(), NextActivity.class);
                intent.putExtra("id",mlist.get(position).getId());
                startActivity(intent);
                return null;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void success(ZhuBean zhuBean) {
        mlist.addAll(zhuBean.getOthers());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void faild(int positon, String str) {

    }
}
