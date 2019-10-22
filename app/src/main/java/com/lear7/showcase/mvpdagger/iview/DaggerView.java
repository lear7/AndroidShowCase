package com.lear7.showcase.mvpdagger.iview;

import com.lear7.showcase.mvp.demo2.base.BaseView;

/**
 * Callback from Presenter to Activity
 */
public interface DaggerView extends BaseView {

    void showMessage(String msg);
}
