package com.atguigu.myandroidgit.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.myandroidgit.R;

/**
 * Created by Administrator on 2017/5/18.
 */

public class TitleBarView extends LinearLayout implements View.OnClickListener {
    private final Context context;

    private TextView tv_title_search;
    private RelativeLayout rl_game;
    private ImageView iv_record;

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 当布局加载完成时回调此方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tv_title_search = (TextView) getChildAt(1);
        rl_game = (RelativeLayout) getChildAt(2);
        iv_record = (ImageView) getChildAt(3);
        //设置点击事件

        tv_title_search.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        iv_record.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.tv_title_search:
                Toast.makeText(context , "搜索栏", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.rl_game:
                Toast.makeText(context , "游戏", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.iv_record:
                Toast.makeText(context , "历史记录", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
