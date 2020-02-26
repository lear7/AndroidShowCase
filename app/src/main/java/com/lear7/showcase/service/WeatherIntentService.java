package com.lear7.showcase.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.lear7.showcase.common.events.BaseEvent;
import com.lear7.showcase.network.helper.DataManager;

import org.greenrobot.eventbus.EventBus;

public class WeatherIntentService extends IntentService {


    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String data = DataManager.getWeatherByOkHttp("From IntentService\nl");
        EventBus.getDefault().post(new BaseEvent(data));
    }

}
