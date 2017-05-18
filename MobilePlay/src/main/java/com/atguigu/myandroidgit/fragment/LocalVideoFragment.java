package com.atguigu.myandroidgit.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.myandroidgit.base.BaseFragment;

/**
 * Created by Administrator on 2017/5/18.
 */

public class LocalVideoFragment extends BaseFragment {
    //临时测试用
    private TextView textView;

    @Override
    public View initView() {
        Log.e("TAG","本地视频Ui初始化...");
        textView = new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","本地视频数据初始化了...");
        textView.setText("本地视频");
    }
}
