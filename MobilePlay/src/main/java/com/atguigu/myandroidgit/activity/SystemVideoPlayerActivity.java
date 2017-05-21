package com.atguigu.myandroidgit.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.atguigu.myandroidgit.R;
import com.atguigu.myandroidgit.utils.Utils;

public class SystemVideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PROGRESS = 0;
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
    private Utils utils;

    private void findViews() {

        setContentView(R.layout.activity_system_video_player);

        vv = (VideoView) findViewById(R.id.vv);
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

        btnVoice.setOnClickListener(this);
        btnSwitchPlayer.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnStartPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSwitchScreen.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utils = new Utils();

        findViews();

        uri = getIntent().getData();

        //设置三个监听
        setListener();

        vv.setVideoURI(uri);
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case  PROGRESS:
                    //得到当前进度
                    int currentPosition = vv.getCurrentPosition();
                    //使seekBar进度更新
                    seekbarVideo.setProgress(currentPosition);
                    //设置文本当前播放进度
                    tvCurrentTime.setText(utils.stringForTime(currentPosition));

                    //循环发消息
                    sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
            }

        }
    };

    private void setListener() {
        //设置播放视频的三个监听
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //得到视频总时长
                int duration = vv.getDuration();
                seekbarVideo.setMax(duration);
                //设置文本总时间
                tvDuration.setText(utils.stringForTime(duration));

                vv.start();

                //发消息开始更新播放进度
                handler.sendEmptyMessage(PROGRESS);
            }
        });

        vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(SystemVideoPlayerActivity.this, "视频播放错误", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(SystemVideoPlayerActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //设置下部工具栏，播放条的监听
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

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v == btnVoice) {

        } else if (v == btnSwitchPlayer) {

        } else if (v == btnExit) {

        } else if (v == btnPre) {

        } else if (v == btnStartPause) {
            if(vv.isPlaying()) {
                //暂停
                vv.pause();
                //按钮状态--播放
                btnStartPause.setBackgroundResource(R.drawable.btn_start_selector);

            }else {
                //播放
                vv.start();
                btnStartPause.setBackgroundResource(R.drawable.btn_pause_selector);
            }

        } else if (v == btnNext) {

        } else if (v == btnSwitchScreen) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
