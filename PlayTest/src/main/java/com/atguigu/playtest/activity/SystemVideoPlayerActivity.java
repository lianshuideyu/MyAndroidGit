package com.atguigu.playtest.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.atguigu.playtest.R;
import com.atguigu.playtest.domain.MediaItem;
import com.atguigu.playtest.utils.Utils;

import java.util.ArrayList;

public class SystemVideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView vv;
    private Uri uri;

    private LinearLayout llTop;
    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemTime;
    private Button btnVoice;
    private SeekBar seekbarVoice;
    private Button btnSwitchPlayer;
    private LinearLayout llBottom;
    private TextView tvCurrentTime;
    private SeekBar seekbarVideo;
    private TextView tvDuration;
    private Button btnExit;
    private Button btnPre;
    private Button btnStartPause;
    private Button btnNext;
    private Button btnSwitchScreen;

    private static final int PROGRESS = 0;
    private Utils utils;
    //广播
    private MyBroadCastReceiver receiver;
    private int batteryView;

    private ArrayList<MediaItem> mediaItems;
    private int position;//视频列表的位置
    //隐藏控制面板的码
    private static final int HIDE_MEDIACONTROLLER = 1;
    //手势识别器
    private GestureDetector detector;

    //设置视频的默认尺寸
    private static final int DEFUALT_SCREEN = 0;
    //全屏视频尺寸
    private static final int FULL_SCREEN = 1;
    //是否全屏
    private boolean isFullScreen = false;
    //屏幕的高
    private int screenHeight;
    private int screenWidth;
    //视频原生的宽和高
    private int videoWidth;
    private int videoHeight;

    //当前的音量：0~15之间
    private int currentVoice;
    //音频管理
    private AudioManager am;
    private int maxVoice;
    //是否静音
    private boolean isMute = false;

    private void findViews() {

        setContentView(R.layout.activity_system_video_player);
        llTop = (LinearLayout) findViewById(R.id.ll_top);
        tvName = (TextView) findViewById(R.id.tv_name);
        ivBattery = (ImageView) findViewById(R.id.iv_battery);
        tvSystemTime = (TextView) findViewById(R.id.tv_system_time);
        btnVoice = (Button) findViewById(R.id.btn_voice);
        seekbarVoice = (SeekBar) findViewById(R.id.seekbar_voice);
        btnSwitchPlayer = (Button) findViewById(R.id.btn_switch_player);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        seekbarVideo = (SeekBar) findViewById(R.id.seekbar_video);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        btnExit = (Button) findViewById(R.id.btn_exit);
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnStartPause = (Button) findViewById(R.id.btn_start_pause);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSwitchScreen = (Button) findViewById(R.id.btn_switch_screen);
        vv = (VideoView) findViewById(R.id.vv);

        //点击事件
        btnVoice.setOnClickListener(this);
        btnSwitchPlayer.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnStartPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSwitchScreen.setOnClickListener(this);

    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnVoice) {


        } else if (v == btnSwitchPlayer) {

        } else if (v == btnExit) {
            finish();
        } else if (v == btnPre) {

        } else if (v == btnStartPause) {
            setStartOrPause();

        } else if (v == btnNext) {

        } else if (v == btnSwitchScreen) {


        }

    }

    private void setStartOrPause() {
        if(vv.isPlaying()) {
            vv.pause();//如果是播放中那就暂停
            btnStartPause.setBackgroundResource(R.drawable.btn_start_selector);
        }else {
            vv.start();
            btnStartPause.setBackgroundResource(R.drawable.btn_pause_selector);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViews();

        uri = getIntent().getData();

        //设置三个监听
        setListener();

        vv.setVideoURI(uri);

    }

    private void setListener() {
        //准备播放的时候调用
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                int duration = vv.getDuration();//得到视频总时长
                seekbarVideo.setMax(duration);

                vv.start();//开启视频
            }
        });

        vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

                Toast.makeText(SystemVideoPlayerActivity.this, "播放出错", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        //播放完成时候调用
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
                Toast.makeText(SystemVideoPlayerActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 播放条的监听，并可拖动改变进度
         */
        seekbarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {


                    vv.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }




    private class MyBroadCastReceiver {

    }
}
