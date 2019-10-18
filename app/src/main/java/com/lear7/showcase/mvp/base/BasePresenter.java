package com.lear7.showcase.mvp.base;

import com.lear7.showcase.beans.WeatherBean;
import com.lear7.showcase.events.BaseEvent;
import com.lear7.showcase.net.api.ApiService;
import com.lear7.showcase.net.helper.BaseSubscriber;
import com.lear7.showcase.net.helper.RxRetrofit;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<V extends BaseView> {

    protected V mView;

    public void setView(V mvpView) {
        this.mView = mvpView;
    }

    public V getView() {
        return mView;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public void detachView() {
        if (null != mView) {
            this.mView = null;
        }
    }

}
