package com.lear7.showcase.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * @author Lear
 * @description
 * @date 2019/12/11 16:58
 */
public class ActivityStackManager {
    private static ActivityStackManager INSTANCE;
    private WeakReference<Activity> sCurrentActivityWeakRef;
    private Stack<Activity> activities;

    public static synchronized ActivityStackManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ActivityStackManager.class) {
                INSTANCE = new ActivityStackManager();
            }
        }
        return INSTANCE;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

    public Activity getTopActivity() {
        if (activities != null && activities.size() > 0) {
            return activities.peek();
        }
        return null;
    }

    public void setTopActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            if (activities.search(activity) == -1) {
                activities.push(activity);
                return;
            }

            int location = activities.search(activity);
            if (location != 1) {
                activities.remove(activity);
                activities.push(activity);
            }
        }
    }

    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new Stack<Activity>();
        }
        if (activities.search(activity) == -1) {
            activities.push(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            activities.remove(activity);
        }
    }

}
