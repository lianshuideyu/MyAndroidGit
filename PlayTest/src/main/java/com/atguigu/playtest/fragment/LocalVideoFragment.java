package com.atguigu.playtest.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.playtest.R;
import com.atguigu.playtest.activity.SystemVideoPlayerActivity;
import com.atguigu.playtest.adapter.LocalVideoAdapter;
import com.atguigu.playtest.baseFragment.BaseFragment;
import com.atguigu.playtest.domain.MediaItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19.
 */

public class LocalVideoFragment extends BaseFragment {
    private TextView tv_noContent;
    private ListView listView;
    private LocalVideoAdapter adapter;
    private ArrayList<MediaItem> list;
    private Uri uri;

    /**
     * 加载布局的方法
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.localvideo_list, null);
        listView = (ListView) view.findViewById(R.id.lv_local_video);
        tv_noContent = (TextView) view.findViewById(R.id.tv_noContent);

        //设置点击事件，点击开启视频
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MediaItem item = adapter.getItem(i);

                Intent intent = new Intent(context, SystemVideoPlayerActivity.class);
                intent.setDataAndType(Uri.parse(item.getData()),"video/*");
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
        getData();

    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(list != null && list.size() > 0) {
                tv_noContent.setVisibility(View.GONE);
                //有数据的时候创建适配器
                adapter = new LocalVideoAdapter(context,list);

                listView.setAdapter(adapter);
                //Toast.makeText(context, "数据加载成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "没有视频数据", Toast.LENGTH_SHORT).show();
                tv_noContent.setVisibility(View.VISIBLE);
            }
        }
    };

    private void getData() {
        //添加数据为耗时的操作需要在子线程中进行
        new Thread(){
            public void run(){
                list = new ArrayList<MediaItem>();
                //得到解析者
                ContentResolver resolver = context.getContentResolver();

                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频名称
                        MediaStore.Video.Media.DURATION,//时长
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if(cursor != null) {
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));

                        list.add(new MediaItem(name,duration,size,data));

                        handler.sendEmptyMessage(0);
                    }
                }

            }
        }.start();

    }
}
