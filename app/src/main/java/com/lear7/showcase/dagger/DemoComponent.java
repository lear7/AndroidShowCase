package com.lear7.showcase.dagger;


import com.lear7.showcase.activity.DemoDaggerActivity;

import dagger.Component;

/**
 * @author lear7
 */
@Component
public interface DemoComponent {

    /**
     * Component for DaggerActivity
     *
     * @param activity 表示可以被DaggerActivity注入
     */
    void inject(DemoDaggerActivity activity);
}
