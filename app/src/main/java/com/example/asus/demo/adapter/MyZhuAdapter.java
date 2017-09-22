package com.example.asus.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.demo.bean.ZhuBean;
import com.example.asus.gaozhijie20170922.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyZhuAdapter extends RecyclerView.Adapter<MyZhuAdapter.ViewHolder> {
    Context context;
    List<ZhuBean.OthersBean> mlist;
    ImageLoader imageloader;
    DisplayImageOptions  options;


    public MyZhuAdapter(Context context, List<ZhuBean.OthersBean> mlist) {
        this.context = context;
        this.mlist = mlist;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zhu_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ZhuBean.OthersBean bean = mlist.get(position);
        holder.zhuTitle.setText(bean.getName());
//        imageloader.displayImage(bean.getThumbnail(), holder.zhuImage, options);
        DisplayImageOptions displayImageOptions = new DisplayImageOptions
                .Builder()
                .displayer(new CircleBitmapDisplayer())
                .build();
        ImageLoader.getInstance().displayImage(bean.getThumbnail(),holder.zhuImage,displayImageOptions);

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setitemClick !=null){
                    setitemClick.setitemclick(v, (Integer) v.getTag());
                }
            }
        });

    }
    public interface SetitemClick{
        View setitemclick(View view,int position);
    }

    SetitemClick setitemClick;

    public void setSetitemClick(SetitemClick setitemClick) {
        this.setitemClick = setitemClick;
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.zhu_image)
        ImageView zhuImage;
        @BindView(R.id.zhu_title)
        TextView zhuTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
