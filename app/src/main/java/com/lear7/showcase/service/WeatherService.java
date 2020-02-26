package com.lear7.showcase.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.lear7.showcase.common.events.BaseEvent;
import com.lear7.showcase.network.helper.DataManager;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

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
        Timber.d("onBind");
        getWeatherInThread();
        return binder;
    }

    // start service 时候调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("onStartCommand");
        getWeatherInThread();
        return START_STICKY;
    }


    private void getWeatherInThread() {
        thread = new Thread(() -> {
            tempData = DataManager.getWeatherByOkHttp("From Service\n");
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
