package com.lear7.showcase.activity;

import android.util.Log;

import com.lear7.showcase.App;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RxJavaActivityTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onResume() {
        Log.e(App.TAG,"Testing!");
    }
}