package com.atguigu.my_mobileplay.page;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.my_mobileplay.R;
import com.atguigu.my_mobileplay.activity.SystemVideoPlayerActivity;
import com.atguigu.my_mobileplay.adapter.NetVideoAdapter;
import com.atguigu.my_mobileplay.domain.MoveInfo;
import com.atguigu.my_mobileplay.fragment.BaseFragment;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class NetVideoFragment extends BaseFragment {
    private TextView tv_nodata;

    private NetVideoAdapter adapter;
    private ListView lv;

    @Override
    public View initView() {
        Log.e("TAG","NetVideoPager-initView");

        View view = View.inflate(context, R.layout.fragment_net_video_pager, null);
        tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
        lv = (ListView) view.findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MoveInfo.TrailersBean item = adapter.getItem(position);
                Intent intent = new Intent(context, SystemVideoPlayerActivity.class);
                intent.setDataAndType(Uri.parse(item.getUrl()),"video/*");
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
    private void processData(String json) {
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
    }
}
