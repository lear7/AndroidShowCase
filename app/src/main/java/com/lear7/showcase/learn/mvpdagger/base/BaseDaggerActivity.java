package com.lear7.showcase.learn.mvpdagger.base;

import com.lear7.showcase.App;
import com.lear7.showcase.ui.base.BaseActivity;
import com.lear7.showcase.learn.mvp.demo2.base.BasePresenter;
import com.lear7.showcase.learn.mvp.demo2.base.BaseView;
import com.lear7.showcase.learn.mvpdagger.base.component.ActivityComponent;
import com.lear7.showcase.learn.mvpdagger.base.component.ApplicationComponent;
import com.lear7.showcase.learn.mvpdagger.base.component.DaggerActivityComponent;
import com.lear7.showcase.learn.mvpdagger.base.module.ActivityModule;

//import com.lear7.showcase.injection.base.getComponent.DaggerActivityComponent;


/**
 * @author lear7
 */
public abstract class BaseDaggerActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity implements BaseView {
    protected P mPresenter;
    private ActivityComponent mComponent;


    @Override
    protected void initView() {
        super.initView();

        injectDependencies();

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.setView((V) this);
        }
    }

    /**
     * @return Presenter
     */
    protected abstract P createPresenter();

    /**
     * Dagger2 not allow Parent class as target
     */
    protected abstract void injectDependencies();

    public ActivityComponent getActivityComponent() {
        if (null == mComponent) {
            mComponent = DaggerActivityComponent.builder()
                    .activityModule(getActivityModule())
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        return mComponent;
    }

    public ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
