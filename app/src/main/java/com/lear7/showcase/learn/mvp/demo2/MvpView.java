package com.lear7.showcase.learn.mvp.demo2;

import com.lear7.showcase.learn.mvp.demo2.base.BaseView;

/**
 * Callback from Presenter to Activity
 */
public interface MvpView extends BaseView {

    void showData(String data);

    void showErrorMessage(String error);

    void showComplete();
}
