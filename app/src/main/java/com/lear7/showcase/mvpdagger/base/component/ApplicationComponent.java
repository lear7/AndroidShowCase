package com.lear7.showcase.mvpdagger.base.component;

import android.content.Context;

import com.lear7.showcase.App;
import com.lear7.showcase.mvpdagger.base.module.ApiModule;
import com.lear7.showcase.mvpdagger.base.module.ApplicationModule;
import com.lear7.showcase.mvpdagger.base.scope.ApplicationContext;
import com.lear7.showcase.net.helper.DataManager;
import com.lear7.showcase.net.helper.UserPreHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(App application);

//    @ApplicationContext
//    Context context();
//
//    ApplicationContext application();
//
//    DataManager dataManager();
//
//    UserPreHelper userHelper();
}
