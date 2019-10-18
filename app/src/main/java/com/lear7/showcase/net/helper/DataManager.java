package com.lear7.showcase.net.helper;

import com.lear7.showcase.net.api.ApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final ApiService apiService;

    @Inject
    public DataManager(ApiService service) {
        this.apiService = service;
    }
}
