package com.lear7.showcase.component.mvpdagger.iview;

import com.lear7.showcase.component.mvp.demo2.base.BaseView;

/**
 * Callback from Presenter to Activity
 */
public interface DaggerView extends BaseView {

    void showMessage(String msg);
}
