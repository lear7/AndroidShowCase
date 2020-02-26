package com.lear7.showcase.network.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.lear7.showcase.component.mvpdagger.base.scope.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserPrefHelper {

    private final SharedPreferences mPref;
    private static final String PRE_FILE_NAME = "userConfig";

    @Inject
    public UserPrefHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().commit();
    }

}
