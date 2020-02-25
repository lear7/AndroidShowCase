package com.lear7.showcase.learn.mvp.demo2.base;


/**
 * Callback from Model to Presenter
 */
public interface BaseCallBack<T> {

    void onSuccess(T data);

    void onError(String error);

    void onComplete();
}
