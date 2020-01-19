package com.lear7.showcase;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lear7.showcase.mvpdagger.base.component.ApplicationComponent;
import com.lear7.showcase.utils.AppLifeCallback;
import com.lear7.showcase.utils.CrashHandler;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import timber.log.Timber;

//import com.lear7.showcase.mvpdagger.base.component.DaggerApplicationComponent;

public class App extends Application {

    public static final String TAG = "LEAR";
    private ApplicationComponent component;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

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

        instance = this;
        // Strict Mode
        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();

            StrictMode.setThreadPolicy(policy);

            Timber.plant(new Timber.DebugTree());
        }
        CrashHandler.getInstance(this);
//        BlockCanary.install(this, new AppContext()).start();

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
        // initLeanCloud();

        // callableTest();
        initVersion();

        initLifecycleCallbacks();
    }

    private void initLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(new AppLifeCallback());
    }

    private void initLeanCloud() {
        try {
            Timber.d("LeanCloud initialling...");
            AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
            AVOSCloud.initialize(this, "E8V5scDOSn22Xfd1L9GFdTMv-9Nh9j0Va", "OJGURyEenUmSzSyciKGSWG89", "https://api.lear7.cc");
            Timber.d("LeanCloud initialized!");
        } catch (Exception e) {
            Timber.d("LeanCloud init error!");
        }
    }

    private void initVersion() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            // read data from meta data
            String app_version = info.metaData.getString("app_version");
            String market = info.metaData.getString("market");

            // build.gradle文件里自动生成
            Timber.i("String from auto generated " + BuildConfig.API_URL);

            if (app_version.equals("test")) {
                //测试版
                Timber.i("test version");
            } else {
                //正式版
                Timber.i("release version");
            }
            // Toast.makeText(this, app_version + " " + market, Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
