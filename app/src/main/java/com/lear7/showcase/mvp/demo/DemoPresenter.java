package com.lear7.showcase.mvp.demo;

public class DemoPresenter {

    protected DemoView mView;

    public DemoPresenter() {

    }

    public void setView(DemoView mvpView) {
        this.mView = mvpView;
    }

    public DemoView getView() {
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


    public void getData(String params) {
        DemoModel.getNetDada(params, new DemoCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (isViewAttached()) {
                    mView.showData((String) data);
                }
            }

            @Override
            public void onError(String error) {
                if (isViewAttached()) {
                    mView.showErrorMessage(error);
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    mView.showComplete();
                }
            }
        });

    }
}
