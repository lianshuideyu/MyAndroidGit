package com.atguigu.playtest.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.playtest.R;
import com.atguigu.playtest.domain.LocalVideo;
import com.atguigu.playtest.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19.
 */

public class LocalVideoAdapter extends BaseAdapter{

    private final ArrayList<LocalVideo> list;
    private final Context context;
    private Utils utils;

    public LocalVideoAdapter(Context context, ArrayList<LocalVideo> list) {
        this.context = context;
        this.list = list;
        utils = new Utils();
    }

    @Override
    public int getCount() {
        return list== null ? 0 : list.size();
    }

    @Override
    public LocalVideo getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
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


        //根据位置得到对应的数据
        LocalVideo mediaItem = list.get(position);
        viewHolder.tv_name.setText(mediaItem.getName());
        viewHolder.tv_size.setText(Formatter.formatFileSize(context,mediaItem.getSize()));
        viewHolder.tv_duration.setText(utils.stringForTime((int) mediaItem.getDuration()));
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_duration;
        TextView tv_size;
    }
}
