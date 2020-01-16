package com.lear7.showcase.net.api;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final ApiService apiService;

    @Inject
    public DataManager(ApiService apiService) {
        this.apiService = apiService;
    }

}
