package com.lear7.showcase.component.mvpdagger.base.module;

import com.lear7.showcase.network.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    private final Retrofit retrofit;

    public ApiModule(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
