package com.lear7.showcase.component.mvpdagger.base.component;

import com.lear7.showcase.ui.activity.MvpDaggerActivity;
import com.lear7.showcase.component.mvpdagger.base.module.ActivityModule;
import com.lear7.showcase.component.mvpdagger.base.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MvpDaggerActivity mvpDaggerActivity);

}
