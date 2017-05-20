package com.atguigu.playtest2.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.atguigu.playtest2.baseFragment.BaseFragment;

/**
 * Created by Administrator on 2017/5/20.
 */

public class LocalAudio extends BaseFragment{
    private TextView tv;

    @Override
    public View initView() {
        tv = new TextView(context);
        tv.setTextSize(30);
        tv.setTextColor(Color.RED);

        return tv;
    }

    @Override
    public void initData() {
        super.initData();

        tv.setText("本地音乐");
    }
}
