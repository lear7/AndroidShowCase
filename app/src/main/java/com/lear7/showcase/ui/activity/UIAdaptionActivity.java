package com.lear7.showcase.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.common.Routers;
import com.lear7.showcase.ui.base.BaseActivity;

/**
 * @author Lear
 * @description
 * @date 2019/12/10 15:04
 */
@Route(path = Routers.Act_UIAdaption)
public class UIAdaptionActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_ui_adaption;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setCustomerDensity(this, getApplication());
    }

    private static void setCustomerDensity(@NonNull Activity activity, @NonNull Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        final float targetDensity = appDisplayMetrics.widthPixels / 720;
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = appDisplayMetrics.scaledDensity = targetDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = activityDisplayMetrics.scaledDensity = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
