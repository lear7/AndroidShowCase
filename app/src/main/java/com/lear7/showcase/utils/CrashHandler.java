package com.lear7.showcase.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.lear7.showcase.utils.shell.CommandResult;
import com.lear7.showcase.utils.shell.Shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = CrashHandler.class.getName();

    private static CrashHandler instance;

    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
    private Map<String, String> infos = new HashMap<String, String>();
    private static DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static final String LOG_PATH = "qj_crash";

    public class CustomException extends Exception {
        public CustomException(String message) {
            super(message);
        }
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public synchronized static CrashHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                instance = new CrashHandler(context);
            }
        }
        return instance;
    }

    private CrashHandler(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 清理30天前的旧log，且每隔10天执行一次，以防APP永远不关闭
        ScheduledExecutorService mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(() -> cleanHistoryLog(30), 10, 10 * 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
            ex.printStackTrace();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Timber.d("error : ", e);
            }
            //退出程序
            System.exit(1);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Timber.d("an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Timber.d(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Timber.e("an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 手动保存一条Log到文件
     *
     * @param content
     */
    public static void logToFile(String content) {
        saveFileOld(content + "\n");
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        Timber.d(result);
        // 保存文件
        return saveFileOld(result);
    }

    private static String saveFile(Context context, String content) {
        long timestamp = System.currentTimeMillis();
        String time = FORMATTER.format(new Date());
        String fileName = "crash-" + time + "-" + timestamp + ".log";

        File file = new File(context.getExternalFilesDir(DIRECTORY_DOCUMENTS), fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] data = content.getBytes();
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e("error occured while writing file...", e);
        }
        return fileName;
    }

    private static String saveFileOld(String content) {
        long timeStamp = System.currentTimeMillis();
        String time = FORMATTER.format(new Date());
        String fileName = "crash-" + time + "-" + timeStamp + ".log";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                // 保存到SD卡根目录或内部存储的qj_crash目录， /storage/emulated/0/qj_crash
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOG_PATH);
                Timber.d(dir.toString());
                if (!dir.exists()) {
                    dir.mkdir();
                }
                FileOutputStream fos = new FileOutputStream(new File(dir,
                        fileName));
                fos.write(content.getBytes());
                fos.close();
                return fileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    // 删除一个月前的log

    private void cleanHistoryLog(int day) {
        new Thread(() -> {
            String cmd =
                    "find " + Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOG_PATH + " -mtime +" + day + " -type f -name \"*.log\" | xargs rm -rf";
            Timber.d(cmd);

            CommandResult result2 = Shell.SH.run(cmd);
            if (result2.isSuccessful()) {
                Timber.d(day + "天前的旧日志清理成功");
            }
        }).start();
    }

}
