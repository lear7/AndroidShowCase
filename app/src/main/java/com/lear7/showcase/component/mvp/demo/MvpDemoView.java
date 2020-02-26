package com.lear7.showcase.component.mvp.demo;

/**
 * Callback from Presenter to Activity
 */
public interface MvpDemoView {

    void showData(String data);

    void showErrorMessage(String error);

    void showComplete();
}
