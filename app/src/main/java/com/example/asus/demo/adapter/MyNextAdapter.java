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

import com.example.asus.demo.bean.NextBean;
import com.example.asus.gaozhijie20170922.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyNextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<NextBean.StoriesBean> mlist;
    ImageLoader imageloader;
    DisplayImageOptions options;


    public MyNextAdapter(Context context, List<NextBean.StoriesBean> mlist) {
        this.context = context;
        this.mlist = mlist;
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);

        //将configuration配置到imageloader中
        imageloader = ImageLoader.getInstance();
        imageloader.init(configuration);

        //自定义ImageLoader缓冲目录
        File flie = new File(Environment.getExternalStorageDirectory(), "bawei");
        if (!flie.exists()) {
            flie.mkdirs();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new UnlimitedDiskCache(flie))
                .build();
        imageloader.init(configuration);

        options = new DisplayImageOptions.Builder()

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_next_one, parent, false);
                holder = new ViewHolderone(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_next_two, parent, false);
                holder = new ViewHoldertwo(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NextBean.StoriesBean bean = mlist.get(position);
        if (holder instanceof ViewHolderone) {
            ViewHolderone viewHolder = (ViewHolderone) holder;
            viewHolder.nextTitle.setText(bean.getTitle());
            imageloader.displayImage(bean.getImages().get(0), viewHolder.nextImage, options);
        } else if (holder instanceof ViewHoldertwo) {
            ViewHoldertwo viewHolders = (ViewHoldertwo) holder;
            viewHolders.nextTitles.setText(bean.getTitle());
        }


    }


    @Override
    public int getItemViewType(int position) {
        List<String> images = mlist.get(position).getImages();
        if (images != null) {
            return 0;
        } else {
            return 1;
        }


    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class ViewHolderone extends RecyclerView.ViewHolder {
        @BindView(R.id.next_image)
        ImageView nextImage;
        @BindView(R.id.next_title)
        TextView nextTitle;

        public ViewHolderone(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHoldertwo extends RecyclerView.ViewHolder {
        @BindView(R.id.next_title)
        TextView nextTitles;

        public ViewHoldertwo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
