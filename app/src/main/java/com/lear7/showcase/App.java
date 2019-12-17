package com.lear7.showcase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.parser.ParserConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.lear7.showcase.mvpdagger.base.component.ApplicationComponent;
import com.lear7.showcase.utils.ActivityStackManager;
import com.lear7.showcase.utils.AppLifeCallback;
import com.lear7.showcase.utils.CrashHandler;
import com.lear7.showcase.utils.shell.CommandResult;
import com.lear7.showcase.utils.shell.Shell;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;

//import com.lear7.showcase.mvpdagger.base.component.DaggerApplicationComponent;

public class App extends Application {

    //参数设置
    public class AppContext extends BlockCanaryContext {
        private static final String TAG = "AppContext";

        @Override
        public String provideQualifier() {
            String qualifier = "";
            try {
                PackageInfo info = getApplicationContext().getPackageManager()
                        .getPackageInfo(getApplicationContext().getPackageName(), 0);
                qualifier += info.versionCode + "_" + info.versionName + "_YYB";
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "provideQualifier exception", e);
            }
            return qualifier;
        }

        @Override
        public int provideBlockThreshold() {
            return 500;
        }

        @Override
        public boolean displayNotification() {
            return BuildConfig.DEBUG;
        }

        @Override
        public boolean stopWhenDebugging() {
            return false;
        }
    }

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
//        BlockCanary.install(this, new AppContext()).start();

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

        initLifecycleCallbacks();
    }

    private void initLifecycleCallbacks() {
        // build
        this.registerActivityLifecycleCallbacks(new AppLifeCallback());
    }

    private void initLeanCloud() {
        try {
            Log.e(TAG, "LeanCloud initialling...");
            AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
            AVOSCloud.initialize(this, "E8V5scDOSn22Xfd1L9GFdTMv-9Nh9j0Va", "OJGURyEenUmSzSyciKGSWG89", "https://api.lear7.cc");
            Log.e(TAG, "LeanCloud initialized!");
        } catch (Exception e) {
            Log.e(TAG, "LeanCloud init error!");
        }
    }

    private static final String BOARD1_CMD = "cat /proc/mcu | grep board_id";
    private static final String BOARD1_TOKEN = "board_id = 4";

    private static final String BOARD2_CMD = "getprop ro.hardinfo";
    private static final String BOARD2_TOKEN = "QJ-iCON39-180528-WHNE73-031609";

    private boolean isAuthorizedDevice() {
        Log.e(TAG, "Checking authorization.");
        // test command line
        boolean isAuthorized = false;
        CommandResult cmd1 = Shell.SH.run(BOARD1_CMD);
        if (cmd1.isSuccessful()) {
            String res = cmd1.getStdout();
            if (TextUtils.equals(res, BOARD1_TOKEN)) {
                return true;
            }
        }
        CommandResult cmd2 = Shell.SH.run(BOARD2_CMD);
        if (cmd2.isSuccessful()) {
            String res = cmd1.getStdout();
            if (TextUtils.equals(res, BOARD2_TOKEN)) {
                return true;
            }
        }
        Log.e(TAG, "Checking authorization finished!");
        return false;
    }

    private void initVersion() {
        if (isAuthorizedDevice()) {
            Toast.makeText(this, "授权设备", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "非授权设备", Toast.LENGTH_SHORT).show();
        }

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
            // Toast.makeText(this, app_version + " " + market, Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
