package com.lear7.showcase.injection.presenter;

import com.lear7.showcase.injection.iview.DaggerView;
import com.lear7.showcase.injection.model.DaggerModel;
import com.lear7.showcase.mvp.base.BasePresenter;

import javax.inject.Inject;

public class DaggerPresenter extends BasePresenter<DaggerView> {

    @Inject
    public DaggerPresenter() {

    }

    public void showToast() {
        getView().showMessage("Message from DaggerPresenter!");
    }

}
