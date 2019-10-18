package com.lear7.showcase.mvp.base;


/**
 * Callback from Model to Presenter
 */
public interface BaseCallBack<T> {

    void onSuccess(T data);

    void onError(String error);

    void onComplete();
}
