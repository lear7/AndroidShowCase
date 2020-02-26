package com.lear7.showcase.component.mvpdagger.demo;

import com.lear7.showcase.ui.activity.MvpDaggerActivity;

import dagger.Component;

/**
 * linking the target and the source
 */
@Component
// @Component(modules = PresenterModule.class) // if use third-party module
public interface PresenterComponent {
    // Firstly, getComponent will link the target which is DaggerActivity, then find the
    // attribute with @Inject inject in the target, then find the @Inject constructor.
    void inject(MvpDaggerActivity mvpDaggerActivity);
}
