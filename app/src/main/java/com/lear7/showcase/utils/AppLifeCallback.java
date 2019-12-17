package com.lear7.showcase.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Lear
 * @description
 * @date 2019/12/11 17:21
 */
public class AppLifeCallback implements Application.ActivityLifecycleCallbacks {
    private static final int APP_STATUS_UNKNOWN = -1;
    private static final int APP_STATUS_LIVE = 0;
    private int appStatus = APP_STATUS_UNKNOWN;

    /**
     * 统计在前台的Activity的个数，来判断当前APP是否在前台还是后台运行
     */
    private int appCount = 0;
    private boolean isForground = true;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.i("Lifecycle", activity.getLocalClassName() + "onActivityCreated");
        if (appStatus == APP_STATUS_UNKNOWN) {
            // 设置状态标志 APP_STATUS_LIVE
            // 判断是否跳转闪屏页
            appStatus = APP_STATUS_LIVE;
            // 监听Activity创建过程，如果是低内存情况重启，那么需要跳转到初始的启动页面，比如SplashActivity， 来执行正常的启动流程
            // TODO startLauncherActivity(activity);
        }

        // 添加到Activity栈
        ActivityStackManager.getInstance().addActivity(activity);

        if (savedInstanceState != null && savedInstanceState.getBoolean("saveStateKey", false)) {
            // 在这里可以读取保存的数据;
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.i("Lifecycle", activity.getLocalClassName() + "onActivityStarted");
        // 添加到Activity栈
        ActivityStackManager.getInstance().addActivity(activity);
        appCount++;
        // 统计前台运行Activity个数
        if (!isForground) {
            isForground = true;
        }
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.i("Lifecycle", "onActivityDestroyed");

        appCount--;
        // 如果前台Activity数量不大于零，说明程序在后台运行
        if (!isForgroundAppValue()) {
            isForground = false;
        }

        // 从Activity栈中移除该Activity
        ActivityStackManager.getInstance().removeActivity(activity);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.i("Lifecycle", activity.getLocalClassName() + "onActivityResumed");

        // 弱引用持有当前 Activity 实例
        ActivityStackManager.getInstance().setCurrentActivity(activity);
        // Activity 页面栈方式
        ActivityStackManager.getInstance().setTopActivity(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.i("Lifecycle", activity.getLocalClassName() + "onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.i("Lifecycle", activity.getLocalClassName() + "onActivityStopped");
        ActivityStackManager.getInstance().removeActivity(activity);

        appCount--;
        if (!isForgroundAppValue()) {
            isForground = false;
            Log.e("AppLifecycle", "app into background ");
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Log.i("Lifecycle", activity.getLocalClassName() + "onActivitySaveInstanceState");
        // 在这里可以保存一些Activity数据
        // outState.putBoolean("saveStateKey", true);
        // outState.putLong("localTime", System.currentTimeMillis());
    }

    private boolean isForgroundAppValue() {
        return appCount > 0;
    }

}
