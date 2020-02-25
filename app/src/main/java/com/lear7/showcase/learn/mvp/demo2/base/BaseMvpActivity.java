package com.lear7.showcase.learn.mvp.demo2.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lear7.showcase.ui.base.BaseActivity;

public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity implements BaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initial presenter
        mPresenter = createPresenter();

        // binding view
        if (mPresenter != null) {
            mPresenter.setView((V) this);
        }

    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
