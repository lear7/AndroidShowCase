package com.lear7.showcase.injection.demo;

import com.lear7.showcase.activity.DaggerActivity;

import dagger.Component;

/**
 * linking the target and the source
 */
@Component
// @Component(modules = PresenterModule.class) // if use third-party module
public interface PresenterComponent {
    // Firstly, component will link the target which is DaggerActivity, then find the
    // attribute with @Inject inject in the target, then find the @Inject constructor.
    void inject(DaggerActivity daggerActivity);
}
