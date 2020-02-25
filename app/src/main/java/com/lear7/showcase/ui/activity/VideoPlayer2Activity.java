package com.lear7.showcase.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.lear7.showcase.R;
import com.lear7.showcase.common.Routers;
import com.lear7.showcase.common.utils.FileUtilsJ;

import java.io.File;

@Route(path = Routers.Act_Video2)
public class VideoPlayer2Activity extends AppCompatActivity {

    private PlayerView playerView;
    private Button button9;

    static Handler mainHandler = new Handler();
    // step1. 创建一个默认的TrackSelector
    // 创建带宽
    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    // 创建轨道选择工厂
    TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

    // 创建轨道选择器实例
    TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

    //step2. 创建播放器
    SimpleExoPlayer player;
    private int resumeWindow;
    private long resumePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player2);
        initView();
        initExoplayer();
    }

    private void initExoplayer() {
        //step2. 创建播放器
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        // 创建加载数据的工厂
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "yourApplicationName"), (TransferListener) bandwidthMeter);

        // 创建解析数据的工厂
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // 传入Uri、加载数据的工厂、解析数据的工厂，就能创建出MediaSource
        File videoFile = FileUtilsJ.getFileFromAsset(this, "disco.mp4");
        Uri mp4VideoUri = Uri.parse(videoFile.getAbsolutePath());
        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri,
                dataSourceFactory, extractorsFactory, null, null);
        // Prepare
        player.prepare(videoSource);
    }


    public void initView() {
        playerView = findViewById(R.id.simpleExoPlayerView);
        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayer();
            }
        });
    }

    private void startPlayer() {
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null && player.getCurrentPosition() > 0) {
            player.setPlayWhenReady(true);
            player.seekTo(resumePosition);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.getPlayWhenReady()) {
            resumeWindow = player.getCurrentWindowIndex();
            resumePosition = Math.max(0, player.getContentPosition());
            player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放播放器
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
