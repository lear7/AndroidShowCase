package com.lear7.showcase.component.lifecycle.observer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ActivityObserver implements androidx.lifecycle.LifecycleObserver {

    private Disposable disposable;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void runTask() {
        // 每隔1s执行一次事件
        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Timber.i(String.valueOf(aLong));
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopTask() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
