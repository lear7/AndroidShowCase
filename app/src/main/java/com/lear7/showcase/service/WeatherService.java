package com.lear7.showcase.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lear7.showcase.App;
import com.lear7.showcase.net.helper.DataHelper;
import com.lear7.showcase.events.BaseEvent;

import org.greenrobot.eventbus.EventBus;

public class WeatherService extends Service {

    private final ServiceBinder binder = new ServiceBinder();
    private Thread thread;
    private String tempData;


    public class ServiceBinder extends Binder {
        public WeatherService getService() {
            return WeatherService.this;
        }
    }

    // 只有第一个绑定的时候会调用

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(App.TAG, "onBind");
        getWeatherInThread();
        return binder;
    }

    // start service 时候调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(App.TAG, "onStartCommand");
        getWeatherInThread();
        return START_STICKY;
    }


    private void getWeatherInThread() {
        thread = new Thread(() -> {
            tempData = DataHelper.getWeatherByOkHttp("From Service\n");
            EventBus.getDefault().post(new BaseEvent(tempData));
        });
        thread.start();
    }


    public String getTempData() {
        return tempData;
    }

    public void setTempData(String tempData) {
        this.tempData = tempData;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tempData = "";
    }


}
