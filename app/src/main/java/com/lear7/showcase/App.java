package com.lear7.showcase;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lear7.showcase.mvpdagger.base.component.ApplicationComponent;
import com.lear7.showcase.utils.CrashHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.concurrent.ThreadPoolExecutor;

import cn.leancloud.AVOSCloud;
import io.reactivex.Single;

//import com.lear7.showcase.mvpdagger.base.component.DaggerApplicationComponent;

public class App extends Application {

    public static final String TAG = "LEAR";
    private ApplicationComponent component;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initializeInjector() {
//        component = DaggerApplicationComponent.builder()
//                .applicationModule(new ApplicationModule(this))
//                .apiModule(new ApiModule(RxRetrofit.getInstance()))
//                .build();
//        component.inject(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Strict Mode
        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();

            StrictMode.setThreadPolicy(policy);
        }
        CrashHandler.getInstance(this);

        // disk log
        FormatStrategy diskFormatStrategy = CsvFormatStrategy.newBuilder()
                .tag("AndroidShowcase")
                .build();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("AndroidShowcase")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new DiskLogAdapter(diskFormatStrategy));
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        // Fresco
        Fresco.initialize(this);

        // ARouter
        // These two lines must be written before binding, otherwise these configurations will be invalid in the binding process
        if (BuildConfig.DEBUG) {
            // Print log
            ARouter.openLog();
            // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
            ARouter.openDebug();
        }
        ARouter.init(this);

        // dagger2
        initializeInjector();

        // init leancloud
        initLeanCloud();

        // callableTest();
        initVersion();
    }

    private void initLeanCloud() {
        Log.e(TAG, "LeanCloud initialling...");
        AVOSCloud.initialize(App.this,
                "E8V5scDOSn22Xfd1L9GFdTMv-9Nh9j0Va",
                "OJGURyEenUmSzSyciKGSWG89",
                "https://api.lear7.cc");
        Log.e(TAG, "LeanCloud initialized!");
    }

    private void initVersion() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            // read data from meta data
            String app_version = info.metaData.getString("app_version");
            String market = info.metaData.getString("market");

            // build.gradle文件里自动生成
            Logger.e("String from auto generated" + BuildConfig.API_URL);

            if (app_version.equals("test")) {
                //测试版
                Log.e(App.TAG, "test version");
            } else {
                //正式版
                Log.e(App.TAG, "release version");
            }
            Toast.makeText(this, app_version + " " + market, Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
