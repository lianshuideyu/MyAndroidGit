package com.atguigu.myandroidgit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.atguigu.myandroidgit.base.BaseFragment;
import com.atguigu.myandroidgit.fragment.LocalAudioFragment;
import com.atguigu.myandroidgit.fragment.LocalVideoFragment;
import com.atguigu.myandroidgit.fragment.NetAudioFragment;
import com.atguigu.myandroidgit.fragment.NetVideoFragment;

import java.util.ArrayList;

import static com.atguigu.myandroidgit.R.id.fl_main_content;

public class MobilePlayMainActivity extends AppCompatActivity  {
    private RadioGroup rg_bottom_button;
    private ArrayList<BaseFragment> fragments;

    private int position;//记录Fragment页码

    private Fragment currentFragment;
    private Fragment temFragment;//缓冲的Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_play_main);

        rg_bottom_button = (RadioGroup)findViewById(R.id.rg_bottom_button);


        //添加集合数据
        initFragment();
        //RadioGroup的点击事件监听
        initListener();


    }

    //RadioGroup的点击事件监听
    private void initListener() {
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
                currentFragment = fragments.get(position);//得到当前的Fragment页面
                
                switchFragment();
            }
        });

        //默认勾选第本地视频个位置，这个要放在RadioGroup的监听事件之后，保证达到页面也随之改变的效果
        rg_bottom_button.check(R.id.rb_video);
    }

    private void switchFragment() {
        
        if(temFragment != currentFragment) {
            //开启事务
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //切换
            if(currentFragment != null) {

                if(!currentFragment.isAdded()) {//如果没有添加
                    //将之前的隐藏
                    if(temFragment != null) {
                        ft.hide(temFragment);
                    }
                    //如果没有添加就添加
                    ft.add(fl_main_content,currentFragment);
                }else {//添加过了
                    //先把之前的隐藏
                    if(temFragment != null) {
                        ft.hide(temFragment);
                    }

                    //如果添加就显示
                    ft.show(currentFragment);

                }
                ft.commit();//不要忘了提交
            }
            temFragment = currentFragment;//重新赋值
        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideoFragment());
        fragments.add(new LocalAudioFragment());
        fragments.add(new NetAudioFragment());
        fragments.add(new NetVideoFragment());
    }

}
