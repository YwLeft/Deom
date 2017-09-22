package com.example.asus.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.gaozhijie20170922.R;
import com.example.asus.demo.bean.DayBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyDayAdapter extends RecyclerView.Adapter<MyDayAdapter.ViewHolder> {
    Context context;
    List<DayBean.StoriesBean> mlist;
    ImageLoader imageloader;
    DisplayImageOptions  options;


    public MyDayAdapter(Context context, List<DayBean.StoriesBean> mlist) {
        this.mlist = mlist;
        this.context = context;
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);

        //将configuration配置到imageloader中
        imageloader= ImageLoader.getInstance();
        imageloader.init(configuration);

        //自定义ImageLoader缓冲目录
        File flie= new File(Environment.getExternalStorageDirectory(),"bawei");
        if(!flie.exists()){
            flie.mkdirs();
        }
        imageloader.init(configuration);

        options=new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DayBean.StoriesBean bean = mlist.get(position);
        holder.textTitle.setText(bean.getTitle());
        holder.textDate.setText(bean.getGa_prefix());
        imageloader.displayImage(bean.getImages().get(0), holder.dayImage, options);

    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.day_image)
        ImageView dayImage;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_date)
        TextView textDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
