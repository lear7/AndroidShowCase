package com.lear7.showcase.net.helper;

import android.util.Log;

import com.lear7.showcase.App;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {


    public BaseSubscriber() {

    }

    @Override
    public abstract void onNext(T o);


    @Override
    public void onError(Throwable t) {
        Log.e(App.TAG, "error");
    }

    @Override
    public void onComplete() {

    }
}
