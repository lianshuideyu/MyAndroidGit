package com.atguigu.myandroidgit.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.myandroidgit.R;
import com.atguigu.myandroidgit.activity.SystemVideoPlayerActivity;
import com.atguigu.myandroidgit.adapter.LocalVideoAdapter;
import com.atguigu.myandroidgit.base.BaseFragment;
import com.atguigu.myandroidgit.domain.MediaItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/18.
 */

public class LocalVideoFragment extends BaseFragment {
    private ListView lv;
    private TextView tv_nocontent;
    private ArrayList<MediaItem> mediaItems;
    private LocalVideoAdapter adapter;
    private Object data;

    /**
     * 加载布局是调用
     * @return
     */
    @Override
    public View initView() {
        Log.e("TAG","本地视频Ui初始化...");
        View view = View.inflate(mContext, R.layout.loacl_video, null);
        lv = (ListView)view.findViewById(R.id.lv_localvideo);
        tv_nocontent = (TextView)view.findViewById(R.id.tv_nocontent);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem item = adapter.getItem(position);
//                Intent intent = new Intent(mContext, SystemVideoPlayerActivity.class);
//                intent.setDataAndType(Uri.parse(item.getData()),"video/*");
//                startActivity(intent);

                //传递视频列表过去

                Intent intent = new Intent(mContext, SystemVideoPlayerActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("videolist",mediaItems);
                intent.putExtra("position",position);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        return view;
    }

    /**
     * 添加数据
     */
    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "本地视频数据初始化了...");

        getData();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mediaItems != null && mediaItems.size() > 0) {
                //有数据
                tv_nocontent.setVisibility(View.GONE);
                adapter = new LocalVideoAdapter(mContext,mediaItems);

                lv.setAdapter(adapter);
            }else {
                tv_nocontent.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, "没有视频数据", Toast.LENGTH_SHORT).show();
            }

        }
    };

    public void getData() {
        //添加数据为耗时的操作
        new Thread(){
            public void run(){
                mediaItems = new ArrayList<MediaItem>();
                //得到解析者
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                ContentResolver resolver = mContext.getContentResolver();

                String[] objs ={
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));

                    mediaItems.add(new MediaItem(name,duration,size,data));

                }

                    handler.sendEmptyMessage(0);

            }
        }.start();

    }
}
