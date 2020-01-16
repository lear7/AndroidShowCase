package com.lear7.showcase.mvpdagger.demo;

import javax.inject.Inject;

import timber.log.Timber;

public class Presenter {

    @Inject
    public Presenter() {
        // Presenter use @Inject to finish inject in constructor
    }

    public void log() {
        Timber.d("Injected");
    }
}
