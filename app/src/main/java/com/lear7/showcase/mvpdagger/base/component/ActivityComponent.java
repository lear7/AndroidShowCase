package com.lear7.showcase.mvpdagger.base.component;

import com.lear7.showcase.activity.MvpDaggerActivity;
import com.lear7.showcase.mvpdagger.base.module.ActivityModule;
import com.lear7.showcase.mvpdagger.base.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MvpDaggerActivity mvpDaggerActivity);

}
