package com.lear7.showcase.injection.base.component;

import com.lear7.showcase.App;
import com.lear7.showcase.injection.base.module.ApiModule;
import com.lear7.showcase.injection.base.module.ApplicationModule;
import com.lear7.showcase.injection.base.scope.ApplicationContext;
import com.lear7.showcase.net.helper.DataManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(App application);

    //
//    @ApplicationContext
//    Context context();
//
//    ApplicationContext application();
//
//    DataManager dataManager();
//
//    UserPreHelper userHelper();
}
