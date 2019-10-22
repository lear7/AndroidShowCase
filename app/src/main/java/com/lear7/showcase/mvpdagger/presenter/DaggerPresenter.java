package com.lear7.showcase.mvpdagger.presenter;

import com.lear7.showcase.mvpdagger.iview.DaggerView;
import com.lear7.showcase.mvp.demo2.base.BasePresenter;

import javax.inject.Inject;

public class DaggerPresenter extends BasePresenter<DaggerView> {

    @Inject
    public DaggerPresenter() {

    }

    public void showToast() {
        getView().showMessage("Message from DaggerPresenter!");
    }

}
