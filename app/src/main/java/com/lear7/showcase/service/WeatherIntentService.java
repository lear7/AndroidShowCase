package com.lear7.showcase.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.lear7.showcase.events.BaseEvent;
import com.lear7.showcase.net.helper.DataUtils;

import org.greenrobot.eventbus.EventBus;

public class WeatherIntentService extends IntentService {


    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String data = DataUtils.getWeatherByOkHttp("From IntentService\nl");
        EventBus.getDefault().post(new BaseEvent(data));
    }

}
