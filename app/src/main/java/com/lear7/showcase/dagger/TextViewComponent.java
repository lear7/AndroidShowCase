package com.lear7.showcase.dagger;


import com.lear7.showcase.activity.DaggerDemoActivity;

import dagger.Component;

/**
 * @author lear7
 */
@Component(modules = TextViewModule.class)
public interface TextViewComponent {

    /**
     * Component for DaggerActivity
     *
     * @param activity 表示可以被DaggerActivity注入
     */
    void inject(DaggerDemoActivity activity);
}
