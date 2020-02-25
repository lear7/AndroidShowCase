package com.lear7.showcase.common.net.helper;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {


    public BaseSubscriber() {

    }

    @Override
    public abstract void onNext(T o);


    @Override
    public void onError(Throwable t) {
        Timber.e("error");
    }

    @Override
    public void onComplete() {

    }
}
