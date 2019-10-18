package com.lear7.showcase.injection.base.module;

import android.app.Application;

import com.lear7.showcase.App;
import com.lear7.showcase.net.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApplicationModule {
    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    Application provideApplication() {
        return app;
    }
}
