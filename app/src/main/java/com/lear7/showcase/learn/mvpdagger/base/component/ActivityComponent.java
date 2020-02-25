package com.lear7.showcase.learn.mvpdagger.base.component;

import com.lear7.showcase.ui.activity.MvpDaggerActivity;
import com.lear7.showcase.learn.mvpdagger.base.module.ActivityModule;
import com.lear7.showcase.learn.mvpdagger.base.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MvpDaggerActivity mvpDaggerActivity);

}
