package com.lear7.showcase.mvp.wrap;

import com.lear7.showcase.mvp.base.BasePresenter;

public class WrapPresenter extends BasePresenter<WrapView> {

    public void getData(String params) {
        WrapModel.getNetDada(params, new WrapCallBack() {
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
