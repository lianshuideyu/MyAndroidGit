package com.atguigu.playtest2.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.playtest2.R;
import com.atguigu.playtest2.activity.SystemVideoPlayer;
import com.atguigu.playtest2.adapter.LocalVideoAdapter;
import com.atguigu.playtest2.baseFragment.BaseFragment;
import com.atguigu.playtest2.domain.LocalVideoBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/20.
 */

public class LocalVideo extends BaseFragment{
    private ListView lv_localvideo;
    private TextView tv_novideo;
    private ArrayList<LocalVideoBean> videos;
    private LocalVideoAdapter adapter;
    //private Uri uri;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.local_video_list, null);
        lv_localvideo = (ListView) view.findViewById(R.id.lv_localvideo);
        tv_novideo = (TextView) view.findViewById(R.id.tv_novideo);

        lv_localvideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LocalVideoBean item = adapter.getItem(i);

                Intent intent = new Intent(context, SystemVideoPlayer.class);
                intent.setDataAndType(Uri.parse(item.getData()),"video/*");

                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getData();
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(videos != null && videos.size() > 0) {
                Log.e("TAG","查询电影数据成功");
                adapter = new LocalVideoAdapter(context,videos);

                lv_localvideo.setAdapter(adapter);
                tv_novideo.setVisibility(View.GONE);
            }else {
                Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
                tv_novideo.setVisibility(View.VISIBLE);
            }
        }
    };

    public void getData() {
        new Thread(){
            public void run(){
                videos = new ArrayList<LocalVideoBean>();

                ContentResolver resolver = context.getContentResolver();

                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if(cursor != null) {
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));

                        //添加数据
                        videos.add(new LocalVideoBean(name,duration,size,data));
                    }

                    handler.sendEmptyMessage(0);
                    cursor.close();
                }

            }
        }.start();

    }
}
