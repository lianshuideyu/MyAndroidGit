package com.atguigu.my_mobileplay.page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.my_mobileplay.R;
import com.atguigu.my_mobileplay.activity.SystemVideoPlayerActivity;
import com.atguigu.my_mobileplay.adapter.NetVideoAdapter;
import com.atguigu.my_mobileplay.domain.MediaItem;
import com.atguigu.my_mobileplay.domain.MoveInfo;
import com.atguigu.my_mobileplay.fragment.BaseFragment;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class NetVideoFragment extends BaseFragment {
    private TextView tv_nodata;

    private NetVideoAdapter adapter;
    private ListView lv;

    //与本地视频用同一个对象
    private ArrayList<MediaItem> mediaItems;
    @Override
    public View initView() {
        Log.e("TAG","NetVideoPager-initView");

        View view = View.inflate(context, R.layout.fragment_net_video_pager, null);
        tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
        lv = (ListView) view.findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                MoveInfo.TrailersBean item = adapter.getItem(position);

                MediaItem item = adapter.getItem(position);
                Intent intent = new Intent(context, SystemVideoPlayerActivity.class);

//                intent.setDataAndType(Uri.parse(item.getData()),"video/*");
//                startActivity(intent);
//                //-------------------------
//                intent.putExtra("position",position);

                //传递视频列表过去---------方法2
                Bundle bundle = new Bundle();
                bundle.putSerializable("videolist",mediaItems);
                intent.putExtra("position",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void initData() {
        Log.e("TAG","NetVideoPager-initData");
        super.initData();

        getDataFromNet();


    }

    private void getDataFromNet() {
        //配置联网请求地址
        //直接在主线程进行
        RequestParams request = new RequestParams("http://api.m.mtime.cn/PageSubArea/TrailerList.api");

        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //用gson解析网络数据返回字符串
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        
    }

    /**
     * 用json解析数据
     * @param json
     */
    /*private void processData(String json) {
        MoveInfo moveInfo = new Gson().fromJson(json, MoveInfo.class);

        List<MoveInfo.TrailersBean> datas = moveInfo.getTrailers();

        if (datas != null && datas.size() > 0) {
            tv_nodata.setVisibility(View.GONE);
            //有数据-适配器
            adapter = new NetVideoAdapter(context, datas);
            lv.setAdapter(adapter);

        }else {
            tv_nodata.setVisibility(View.VISIBLE);
        }
    }*/

    private void processData(String json) {
        MoveInfo moveInfo = new Gson().fromJson(json, MoveInfo.class);

        List<MoveInfo.TrailersBean> datas = moveInfo.getTrailers();

        Iterator<MoveInfo.TrailersBean> iterator = datas.iterator();
        mediaItems = new ArrayList<>();
        while (iterator.hasNext()){
            MoveInfo.TrailersBean bean = iterator.next();
            //String name, long title, long size, String data ,String icon
            mediaItems.add(new MediaItem(bean.getMovieName(),
                    bean.getVideoTitle(),bean.getVideoLength(),bean.getUrl(),bean.getCoverImg()));
        }

        if (mediaItems != null && mediaItems.size() > 0) {
            tv_nodata.setVisibility(View.GONE);
            //有数据-适配器
            adapter = new NetVideoAdapter(context, mediaItems);
            lv.setAdapter(adapter);

        }else {
            tv_nodata.setVisibility(View.VISIBLE);
        }
        Log.e("TAG","mediaItems---size" + mediaItems.size());
    }
}
