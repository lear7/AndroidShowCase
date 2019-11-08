package com.lear7.showcase.mvpdagger.base.module;

import androidx.appcompat.app.AppCompatActivity;

import com.lear7.showcase.mvpdagger.base.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity() {
        return this.activity;
    }
}
