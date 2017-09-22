package com.example.asus.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.gaozhijie20170922.R;
import com.example.asus.demo.adapter.MyNextAdapter;
import com.example.asus.demo.bean.NextBean;
import com.example.asus.demo.utils.NetDataCallBack;
import com.example.asus.demo.utils.Okhttp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NextActivity extends AppCompatActivity implements NetDataCallBack<NextBean> {
    @BindView(R.id.next_recyclerview)
    RecyclerView nextRecyclerview;
    private String path = "http://news-at.zhihu.com/api/4/theme/";
    private List<NextBean.StoriesBean> mlist = new ArrayList<>();
    private MyNextAdapter adapter;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next);
        ButterKnife.bind(this);
        initview();
        LoadData();
    }

    private void initview() {
        nextRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyNextAdapter(this,mlist);
        nextRecyclerview.setAdapter(adapter);
    }

    private void LoadData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Okhttp okhttp = new Okhttp();
        okhttp.getdata(path+id,this,NextBean.class);
    }


    @Override
    public void success(NextBean nextBean) {
        mlist.addAll(nextBean.getStories());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void faild(int positon, String str) {

    }
}
