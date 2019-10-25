package com.lear7.showcase.lifecycle.observer;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActivityObserver implements androidx.lifecycle.LifecycleObserver {

    private Disposable disposable;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void runTask() {
        // 每隔1s执行一次事件
        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Log.i("接收数据", String.valueOf(aLong));
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopTask() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
