package com.lear7.showcase.activity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.App;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.constants.Urls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Route(path = Routers.Act_ThreadTest)
public class ThreadTestActivity extends BaseActivity {

    @BindView(R.id.btn_test_runonui)
    Button btnRunOnUi;

    @BindView(R.id.btn_test_handlerthread)
    Button btnHandlerThread;

    @BindView(R.id.btn_test_pood)
    Button btnTestPool;

    private HandlerThread mHandlerThread;

    @OnClick({R.id.btn_test_runonui, R.id.btn_test_handlerthread, R.id.btn_test_pood})
    public void onClick(View view) {
        if (view == btnRunOnUi) {
            testRunOnUI();
        } else if (view == btnHandlerThread) {
            testThreadHandler();
        } else if (view == btnTestPool) {
            testThreadPool();
        }
    }

    private void testRunOnUI() {

        // API 24(7.0)及以上不会执行，因为没有AttachToWindow
        View view = new View(this);
        View rootView = getWindow().getDecorView().getRootView();
        view.post(() -> Log.e(App.TAG, "1. view.post"));
        // View.post() 只有在 View attachedToWindow 的时候才会立即执行

        UI.handler.post(new Runnable() {
            @Override
            public void run() {
                String data = getWeatherData();
                Log.e(App.TAG, "2. handler.post");
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 这样会导致卡顿，因为是在UI线程执行的
//                try {
//                    Thread.sleep(8000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Log.e(App.TAG, "3. main thread run on ui");
            }
        });

        new Thread(() -> runOnUiThread(() -> Log.e(App.TAG, "4. sub thread run on ui"))).start();

        // 正确的顺序是3->2->4，1不会执行，3是主线程先执行，2和4看入展顺序，使用的都是主线程的Looper
        // runOnUiThread - Handler.post - new Thread() - [runOnUiThread] - View.post
    }

    private void testThreadHandler() {
        // 打印主线程的Looper
        Log.e(App.TAG, "1 Looper is:" + getMainLooper());

        // 第一种，子线程中用主线程的Looper
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                // 在子线程中创建handler
                Handler handler = new Handler();
                Log.e(App.TAG, "3 Looper is:" + handler.getLooper());
                Looper.loop();
                // 这里是不会执行的，因为上面的loop()已经阻塞了
                Handler handler2 = new Handler();
                Log.e(App.TAG, "3.1 Looper is:" + handler2.getLooper());
            }

        });
        newThread.start();

        // 第二种，用主线程Looper
        Handler handler1 = new Handler();
        Log.e(App.TAG, "2 Looper is:" + handler1.getLooper());

        // 第三种，使用HandlerThread
        mHandlerThread = new HandlerThread("mHandlerThread");
        mHandlerThread.start();

        Handler handler2 = new Handler(mHandlerThread.getLooper());
        Log.e(App.TAG, "4 Looper is:" + mHandlerThread.getLooper());
        handler2.post(() -> {
            Toast.makeText(this, "Message from HandlerThread", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mHandlerThread.quitSafely();
            } else {
                mHandlerThread.quit();
            }
        });

        // 也是第三种，使用HandlerThread
        HandlerThread thread = new HandlerThread("A Handler Thread");
        thread.start();

        // 不要在这里更新UI，这里是子线程
        Handler handler3 = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.e(App.TAG, "5 Looper is:" + getLooper());
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_thread_test;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    private String getWeatherData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Urls.Weather_URL)
                .build();
        Response response;

        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "error";
        }
    }

    private static class UI {
        public static final Handler handler = new Handler(Looper.getMainLooper());

        private UI() {

        }
    }

    public class MyAsyncTask extends AsyncTask {

        private String taskName;

        public MyAsyncTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        protected String doInBackground(Object[] objects) {
            String time = new Date().toString();
            System.out.println(taskName + " Start");
            try {
                share++;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return time;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            System.out.println(taskName + " End");
        }
    }

    private int share = 0;

    // 声明ThreadLocal
    private static ThreadLocal<Integer> result = new ThreadLocal<>();

    public static final String A = "abcdeabcdef";
    public static final String B = "aaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddabcdeabcdefaaddddaaddddaadddd";

    static class Thread1 implements Runnable {

        @Override
        public void run() {
            int lenA = A.length();
            int lenB = B.length();
            int i = 0;

            while (result.get() == null && i < lenB) {
                boolean isMatch = true;
                for (int j = 0; j < lenA; j++) {
                    if (A.charAt(j) != B.charAt(i + j)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    result.set(i);
                    break;
                }
                i++;
            }
        }
    }

    static class Thread2 implements Runnable {

        @Override
        public void run() {
            int lenA = A.length();
            int lenB = B.length();
            int i = lenB - lenA;

            while (result.get() == null && i >= lenA) {
                boolean isMatch = true;
                for (int j = lenA - 1; j >= 0; j--) {
                    if (A.charAt(j) != B.charAt(i - j)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    result.set(i);
                    break;
                }
                i--;
            }
        }
    }

    private void testThreadPool() {
        // 这种方式不建议
        // ExecutorService pool = Executors.newFixedThreadPool(5);
        ExecutorService pool = new ThreadPoolExecutor(4, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        List<MyAsyncTask> tasks = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            MyAsyncTask task = new MyAsyncTask("task" + i);
            tasks.add(task);
        }

        for (MyAsyncTask task : tasks) {
            task.executeOnExecutor(pool);
        }

        pool.shutdown();
    }
}
