package com.lear7.showcase.injection.demo;

import dagger.Module;
import dagger.Provides;

/**
 * Create third-parth module inject
 */
@Module
public class PresenterModule {
    @Provides
    Presenter providePresenter(){
        return new Presenter();
    }

}
