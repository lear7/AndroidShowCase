package com.lear7.showcase.mvpdagger.base.module;

import android.app.Application;

import com.lear7.showcase.App;

import dagger.Module;
import dagger.Provides;

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
