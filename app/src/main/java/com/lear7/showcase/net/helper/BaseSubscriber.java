package com.lear7.showcase.net.helper;

import android.util.Log;

import com.lear7.showcase.App;

import io.reactivex.observers.DisposableObserver;

public class BaseSubscriber<T> extends DisposableObserver<T> {


    public BaseSubscriber() {

    }

    @Override
    public void onNext(T o) {
        Log.e(App.TAG, o.toString());
    }


    @Override
    public void onError(Throwable t) {
        Log.e(App.TAG, "error");
    }

    @Override
    public void onComplete() {

    }
}
