package com.atguigu.myandroidgit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.atguigu.myandroidgit.base.BaseFragment;
import com.atguigu.myandroidgit.fragment.LocalAudioFragment;
import com.atguigu.myandroidgit.fragment.LocalVideoFragment;
import com.atguigu.myandroidgit.fragment.NetAudioFragment;
import com.atguigu.myandroidgit.fragment.NetVideoFragment;

import java.util.ArrayList;

public class MobilePlayMainActivity extends AppCompatActivity  {
    private RadioGroup rg_bottom_button;
    private int position;
    private ArrayList<BaseFragment> fragments;
    private BaseFragment temFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_play_main);

        rg_bottom_button = (RadioGroup)findViewById(R.id.rg_bottom_button);

        initData();

        CheckedChangeListener();

    }

    private void initData() {
        fragments = new ArrayList<>();

        fragments.add(new LocalVideoFragment());
        fragments.add(new LocalAudioFragment());
        fragments.add(new NetAudioFragment());
        fragments.add(new NetVideoFragment());
    }

    private void CheckedChangeListener() {
        rg_bottom_button.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_video :
                        position = 0;
                        break;
                    case R.id.rb_audio :
                        position = 1;
                        break;
                    case R.id.rb_netaudio :
                        position = 2;
                        break;
                    case R.id.rb_netvideo :
                        position = 3;
                        break;
                }

                BaseFragment currentFragment = fragments.get(position);
                addFragment(currentFragment);
            }
        });
        //设置初进页面默认的勾选
        rg_bottom_button.check(R.id.rb_video);
    }

    private void addFragment(BaseFragment currentFragment) {
        if(temFragment != currentFragment) {
            //开启事务
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(!currentFragment.isAdded()) {
                //没有添加的时候
                if(temFragment != null) {
                    ft.hide(temFragment);
                }
                //隐藏之前的
                //添加当前的
                ft.add(R.id.fl_main_content,currentFragment);
            }else {
                //添加过的时候
                if(temFragment != null) {
                    ft.hide(temFragment);
                }
                //隐藏之前的
                //显示当前的
                ft.show(currentFragment);
            }

            ft.commit();

            temFragment = currentFragment;
        }

    }


}
