package com.atguigu.myandroidgit.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.atguigu.myandroidgit.R;
import com.atguigu.myandroidgit.base.BaseFragment;

/**
 * Created by Administrator on 2017/5/18.
 */

public class LocalVideoFragment extends BaseFragment {
    private ListView lv;

    @Override
    public View initView() {
        Log.e("TAG","本地视频Ui初始化...");
       View.inflate(mContext, R.layout.activity_main,null);

        return null;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","本地视频数据初始化了...");

    }
}
