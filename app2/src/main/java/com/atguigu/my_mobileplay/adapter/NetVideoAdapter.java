package com.atguigu.my_mobileplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.my_mobileplay.R;
import com.atguigu.my_mobileplay.domain.MediaItem;
import com.atguigu.my_mobileplay.utils.Utils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/22.
 */

public class NetVideoAdapter extends BaseAdapter {

    private final Context context;
//    private ArrayList<MoveInfo.TrailersBean> datas;
    private ArrayList<MediaItem> datas;

    private Utils utils;
    private ImageOptions imageOptions;


    public NetVideoAdapter(Context context, ArrayList<MediaItem> datas) {
        this.context = context;
        this.datas = datas;

        utils = new Utils();

        //为了给网络视频设置默认图片才加的这个参数
        imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.drawable.video_default)
                .setLoadingDrawableId(R.drawable.video_default)
                .build();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public MediaItem getItem(int position) {
         return datas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_net_video, null);
            viewHolder = new ViewHolder();

            viewHolder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        MediaItem trailersBean = datas.get(position);
//        viewHolder.tv_name.setText(trailersBean.getMovieName());
//        viewHolder.tv_size.setText(trailersBean.getVideoLength() + "秒");
//        viewHolder.tv_duration.setText(trailersBean.getVideoTitle());
//
//        //图片的加载
//        x.image().bind(viewHolder.iv_icon,trailersBean.getCoverImg(),imageOptions);

        viewHolder.tv_name.setText(trailersBean.getName());
        viewHolder.tv_size.setText(trailersBean.getSize() + "秒");
        viewHolder.tv_duration.setText(trailersBean.getTitle());

        //图片的加载
        //imageOptions为了给网络视频设置默认图片才加的这个参数；一般不加这个参数也可以
        x.image().bind(viewHolder.iv_icon,trailersBean.getIcon(),imageOptions);

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_duration;
        TextView tv_size;
    }
}
