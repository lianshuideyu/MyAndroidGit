package com.atguigu.playtest.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.playtest.R;
import com.atguigu.playtest.baseFragment.BaseFragment;
import com.atguigu.playtest.fragment.LocalAudioFragment;
import com.atguigu.playtest.fragment.LocalVideoFragment;
import com.atguigu.playtest.fragment.NetAudioFragment;
import com.atguigu.playtest.fragment.NetVideoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rg_bottom;

    private ArrayList<BaseFragment> fragments;

    private int position;
    private BaseFragment temFragment;

    private FrameLayout fl_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl_content = (FrameLayout)findViewById(R.id.fl_content);

        rg_bottom = (RadioGroup)findViewById(R.id.rg_bottom);
        initData();

        //设置勾选状态的监听
        checkedChangeListener();
    }

    private void initData() {
        fragments = new ArrayList<>();

        fragments.add(new LocalVideoFragment());
        fragments.add(new LocalAudioFragment());
        fragments.add(new NetAudioFragment());
        fragments.add(new NetVideoFragment());
    }


    private void checkedChangeListener() {
        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {

                switch (checkId) {
                    case R.id.rb_local_video :
                        position = 0;
                        break;
                    case R.id.rb_local_audio :
                        position = 1;
                        break;
                    case R.id.rb_net_audio :
                        position = 2;
                        break;
                    case R.id.rb_net_video :
                        position = 3;
                        break;
                }
                //得到当前的Fragment
                BaseFragment currentFragment = fragments.get(position);

                addFragment(currentFragment);
            }
        });

        //设置在监听事件的后面,默认选中本地视频
        rg_bottom.check(R.id.rb_local_video);
    }

    private void addFragment(BaseFragment currentFragment) {
        if(temFragment != currentFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(!currentFragment.isAdded()) {
               //当没有添加的时候
                //隐藏之前的
                if(temFragment != null) {
                    ft.hide(temFragment);
                }
                //添加当前
                ft.add(R.id.fl_content,currentFragment);
            }else {
                //当已经添加过了
                //隐藏之前的
                if(temFragment != null) {
                    ft.hide(temFragment);
                }
                //显示当前的
                ft.show(currentFragment);
            }
            ft.commit();//提交事务，千万不要忘记

            temFragment = currentFragment;//千万不要忘记将缓存提换为当前的
        }

    }
}
