package com.atguigu.myandroidgit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //测试同步问题
        //测试同步2
    }

    public static void main(String[] args){
        System.out.println("Android studio更改测试");
        System.out.println("Android studio更改测试222");
    }
}
