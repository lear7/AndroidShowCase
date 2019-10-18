package com.lear7.showcase.injection.iview;

import com.lear7.showcase.mvp.base.BaseView;

/**
 * Callback from Presenter to Activity
 */
public interface DaggerView extends BaseView {

    void showMessage(String msg);
}
