package com.lear7.showcase.mvp.demo2;

import com.lear7.showcase.mvp.demo2.base.BasePresenter;

public class MvpPresenter extends BasePresenter<MvpView> {

    public void getData(String params) {
        MvpModel.getNetDada(params, new MvpCallBack() {
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
