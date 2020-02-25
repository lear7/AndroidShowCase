package com.lear7.showcase.learn.mvvm;

import android.os.SystemClock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class TimerViewModel extends ViewModel {

    // 走过的时间
    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();
    // 当前时间
    private MutableLiveData<String> mCurrentTime = new MutableLiveData<>();

    private long mInitialTime;

    public TimerViewModel() {
        mInitialTime = SystemClock.elapsedRealtime();

        ScheduledExecutorService mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(() -> {
            final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
            // setValue要在主线程中执行
            // setValue() cannot be called from a background thread so post to main thread.
            mElapsedTime.postValue(newValue);
        }, 1, 100, TimeUnit.SECONDS);

        mScheduledExecutorService.scheduleAtFixedRate(() -> {
            mCurrentTime.postValue(getCurrentTime());
        }, 1, 100, TimeUnit.SECONDS);
    }

    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat template = new SimpleDateFormat("mm:ss:SSS");
        String timeStr = template.format(calendar.getTime());
        return timeStr;
    }
}