package com.lear7.showcase.injection.base.component;

import com.lear7.showcase.activity.DaggerActivity;
import com.lear7.showcase.injection.base.scope.PerActivity;
import com.lear7.showcase.injection.base.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(DaggerActivity daggerActivity);

}
