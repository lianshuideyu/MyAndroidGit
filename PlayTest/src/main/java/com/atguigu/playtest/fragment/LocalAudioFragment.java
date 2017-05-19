package com.atguigu.playtest.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.playtest.baseFragment.BaseFragment;

/**
 * Created by Administrator on 2017/5/19.
 */

public class LocalAudioFragment extends BaseFragment {
    private TextView tv;

    @Override
    public View initView() {
        tv = new TextView(context);
        tv.setTextSize(30);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    /**
     * 添加数据
     */
    @Override
    public void initData() {
        super.initData();

        tv.setText("本地音频");
    }
}
