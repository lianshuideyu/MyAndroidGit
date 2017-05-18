package com.atguigu.myandroidgit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class MobilePlayMainActivity extends AppCompatActivity {
    private RadioGroup rg_bottom_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_play_main);

        rg_bottom_button = (RadioGroup)findViewById(R.id.rg_bottom_button);

        //默认勾选第0个位置
        rg_bottom_button.check(0);
    }
}
