package com.lear7.showcase.component.mvp.demo2.base;

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
