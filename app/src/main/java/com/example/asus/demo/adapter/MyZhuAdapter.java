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

import com.example.asus.demo.bean.ZhuBean;
import com.example.asus.gaozhijie20170922.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyZhuAdapter extends RecyclerView.Adapter<MyZhuAdapter.ViewHolder> {
    Context context;
    List<ZhuBean.OthersBean> mlist;
    ImageLoader imageloader;
    DisplayImageOptions options;
    public MyZhuAdapter(Context context, List<ZhuBean.OthersBean> mlist) {
        this.context = context;
        this.mlist = mlist;
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
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new UnlimitedDiskCache(flie))
                .build();
        imageloader.init(config);

        options=new DisplayImageOptions.Builder()
                //设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                //设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                //设置图片的解码类型
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                //设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                //设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                //设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                //图像将被二次采样的整数倍
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                //设置图片加入缓存前，对bitmap进行设置
                //.preProcessor(BitmapProcessor preProcessor)
                // 设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                //是否设置为圆角，弧度为多少
                .displayer(new RoundedBitmapDisplayer(20))
                //是否图片加载好后渐入的动画时间
                .displayer(new FadeInBitmapDisplayer(100))
                //构建完成
                .build();

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
        imageloader.displayImage(bean.getThumbnail(), holder.zhuImage, options);

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
