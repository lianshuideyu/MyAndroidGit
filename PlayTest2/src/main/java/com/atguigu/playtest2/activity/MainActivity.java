package com.atguigu.playtest2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.atguigu.playtest2.R;
import com.atguigu.playtest2.baseFragment.BaseFragment;
import com.atguigu.playtest2.fragment.LocalAudio;
import com.atguigu.playtest2.fragment.LocalVideo;
import com.atguigu.playtest2.fragment.NetAudio;
import com.atguigu.playtest2.fragment.NetVideo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<BaseFragment> fragments;
    private RadioGroup rg_bottom;
    private int position;
    private BaseFragment tmpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg_bottom = (RadioGroup)findViewById(R.id.rg_bottom);

        initData();

        checkedChangeListener();
    }

    private void checkedChangeListener() {
        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case  R.id.rb_local_video:
                        position = 0;
                        break;
                    case  R.id.rb_local_audio:
                        position = 1;
                        break;
                    case  R.id.rb_net_audio:
                        position = 2;
                        break;
                    case  R.id.rb_net_video:
                        position = 3;
                        break;
                }

                BaseFragment currentFragment = fragments.get(position);
                addFragment(currentFragment);
            }
        });

        rg_bottom.check(R.id.rb_local_video);
    }

    private void addFragment(BaseFragment currentFragment) {
        if(currentFragment !=tmpFragment ) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if(!currentFragment.isAdded()) {
                if(tmpFragment!= null) {
                    ft.hide(tmpFragment);
                }
                ft.add(R.id.fl_content,currentFragment);

            }else {
                if(tmpFragment!= null) {
                    ft.hide(tmpFragment);
                }
                ft.show(currentFragment);
            }

            ft.commit();
            tmpFragment = currentFragment;
        }

    }

    private void initData() {
        fragments = new ArrayList<>();

        fragments.add(new LocalVideo());
        fragments.add(new LocalAudio());
        fragments.add(new NetAudio());
        fragments.add(new NetVideo());
    }
}
