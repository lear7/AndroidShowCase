package com.lear7.showcase.injection.demo;

import android.util.Log;

import javax.inject.Inject;

public class Presenter {

    @Inject
    public Presenter() {
        // Presenter use @Inject to finish inject in constructor
    }

    public void log(){
        Log.e("Dagger","Injected");
    }
}
