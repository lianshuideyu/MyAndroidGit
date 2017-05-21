package com.atguigu.myandroidgit.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.myandroidgit.R;
import com.atguigu.myandroidgit.domain.MediaItem;
import com.atguigu.myandroidgit.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19.
 */

public class LocalVideoAdapter extends BaseAdapter{
    private final Context context;
    private final ArrayList<MediaItem> videos;
    private Utils utils;

    public LocalVideoAdapter(Context context, ArrayList<MediaItem> videos) {
        this.context = context;
        this.videos = videos;
        this.utils = new Utils();
    }

    @Override
    public int getCount() {
        return videos==null ? 0 : videos.size();
    }

    @Override
    public MediaItem getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_local_video, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MediaItem bean = videos.get(position);

        viewHolder.tv_name.setText(bean.getName());
        viewHolder.tv_duration.setText(utils.stringForTime((int) bean.getDuration()));
        viewHolder.tv_size.setText(Formatter.formatFileSize(context,bean.getSize()));

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_duration;
        TextView tv_size;
    }
}
