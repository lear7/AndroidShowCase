package com.lear7.showcase.mvp.wrap;

import com.lear7.showcase.mvp.base.BaseView;

/**
 * Callback from Presenter to Activity
 */
public interface WrapView extends BaseView {

    void showData(String data);

    void showErrorMessage(String error);

    void showComplete();
}
