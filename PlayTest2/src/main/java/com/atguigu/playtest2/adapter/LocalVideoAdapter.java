package com.atguigu.playtest2.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.playtest2.R;
import com.atguigu.playtest2.domain.LocalVideoBean;
import com.atguigu.playtest2.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/20.
 */

public class LocalVideoAdapter extends BaseAdapter {
    private final ArrayList<LocalVideoBean> videos;
    private final Context context;
    private Utils utils;

    public LocalVideoAdapter(Context context, ArrayList<LocalVideoBean> videos) {
        this.context = context;
        this.videos = videos;
        utils = new Utils();
    }

    @Override
    public int getCount() {
        return videos== null ? 0 : videos.size();
    }

    @Override
    public LocalVideoBean getItem(int i) {
        return videos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
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

        LocalVideoBean bean = videos.get(position);
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
