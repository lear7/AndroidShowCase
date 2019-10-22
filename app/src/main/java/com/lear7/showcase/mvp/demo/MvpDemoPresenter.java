package com.lear7.showcase.mvp.demo;

public class MvpDemoPresenter {

    protected MvpDemoView mView;

    public MvpDemoPresenter() {

    }

    public void setView(MvpDemoView mvpView) {
        this.mView = mvpView;
    }

    public MvpDemoView getView() {
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
        MvpDemoModel.getNetDada(params, new MvpDemoCallBack() {
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
