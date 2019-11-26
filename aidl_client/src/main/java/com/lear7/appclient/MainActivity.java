package com.lear7.appclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lear7.showcase.IRemoteService;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private class BindConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IRemoteService.Stub.asInterface(service);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    }

    private IRemoteService mService = null;
    private boolean bound = false;
    private BindConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 屏幕常亮
        setContentView(R.layout.activity_main);

        connection = new BindConnection();

        findViewById(R.id.btn_get_message).setOnClickListener((v) -> {
            if (!bound) {
                connectService();
            } else {
                try {
                    Toast.makeText(this, mService.getRemoteMessage(0), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        if (!bound) {
            connectService();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unbind.
        if (mService != null) {
            unbindService(connection);
        }
    }

    private synchronized void connectService() {
        Intent intent = new Intent("com.lear7.action.BindRemoteService");
        // 这里设置要绑定的APP的applicationId
        intent.setPackage("com.lear7.showcase_test");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

}
