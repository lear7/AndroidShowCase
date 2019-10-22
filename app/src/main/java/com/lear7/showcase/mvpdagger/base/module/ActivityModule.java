package com.lear7.showcase.mvpdagger.base.module;

import android.app.Activity;

import com.lear7.showcase.mvpdagger.base.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
