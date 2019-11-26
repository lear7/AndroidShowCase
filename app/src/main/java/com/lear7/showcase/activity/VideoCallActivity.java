package com.lear7.showcase.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.demo.li.vc_room_out.ui.RoomOutVideoActivity;
import com.demo.li.vc_room_out.ui.RoomOutSettingActivity;
import com.demo.li.vc_room_out.net.HeartbeatThread;
import com.demo.li.vc_room_out.net.SocketHelper;
import com.demo.li.vc_room_out.net.VisitReceiver;
import com.demo.video_library.Global;
import com.demo.video_library.entity.CallOff;
import com.demo.video_library.entity.CallRequest;
import com.demo.video_library.entity.CallRequestResult;
import com.demo.video_library.entity.WarningInfo;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;

@Route(path = Routers.Act_VideoCall)
public class VideoCallActivity extends AppCompatActivity implements VisitReceiver.VisitListener {

    private final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        // Start 启动与室内机通讯线程
        Global.currentActivity = this;
        SocketHelper.getInstance().start();//连接室内端的socket并且开始监听回来的数据
        HeartbeatThread.getInstance().start();// 心跳线程
        VisitReceiver.setListener(this);
        // End 启动与室内机通讯线程

        TextView tvTitle = findViewById(R.id.main_time_tv);
        tvTitle.setTypeface(Typeface.createFromAsset(getAssets(), "font/kaiti.ttf"), Typeface.BOLD);

        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCall();
            }
        });

        findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoCallActivity.this, RoomOutSettingActivity.class);
                startActivity(intent);
            }
        });
    }


    private void startCall() {
        Intent intent = new Intent(VideoCallActivity.this, RoomOutVideoActivity.class);
        startActivity(intent);
//        if (isAttach) {
//            videoFragment = null;
//            removeFragment(videoFragment);
//        } else {
//            videoFragment = new RoomOutVideoFragment();
//            loadFragment(videoFragment);
//        }
    }

    private boolean isAttach = false;

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.video_parent, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        isAttach = true;
    }

    private void removeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commitAllowingStateLoss();
        isAttach = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 检查相关权限
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void receivedWarningInfo(WarningInfo warningInfo) {

    }

    @Override
    public void onCallRequestResult(CallRequestResult callRequestResult) {

    }

    @Override
    public void onCallRequest(CallRequest callRequest) {

    }

    @Override
    public void onCallOff(CallOff callOff) {
//        removeFragment(videoFragment);
    }

}
