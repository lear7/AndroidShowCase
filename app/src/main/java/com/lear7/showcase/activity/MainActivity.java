package com.lear7.showcase.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.App;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.lifecycle.observer.MyObserver;
import com.lear7.showcase.lifecycle.viewmodel.UserModel;
import com.lear7.showcase.service.WeatherService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = Routers.Act_Main)
public class MainActivity extends BaseActivity {


    @BindView(R.id.btn_go_to_b)
    Button btnGoToB;

    @BindView(R.id.btn_go_to_thread)
    Button btnGoToThread;

    @BindView(R.id.btn_start_service)
    Button startService;

    @BindView(R.id.btn_thread_test)
    Button startThreadTest;

    @BindView(R.id.btn_start_download)
    Button startDownloadTest;

    @BindView(R.id.btn_test_mvp)
    Button btnTestMvp;

    @BindView(R.id.btn_test_mvp_wrap)
    Button btnTestMvpWrap;

    @BindView(R.id.btn_test_mvp_dagger)
    Button btnTestMvpDagger;

    @BindView(R.id.btn_data_binding)
    Button btnDatabinding;

    @BindView(R.id.btn_view_model)
    Button btnViewModel;

    @BindView(R.id.btn_recycleview)
    Button btnMaterial;

    @BindView(R.id.btn_video_demo)
    Button btnVideoDemo;

    @BindView(R.id.btn_listview)
    Button btnListView;

    private int share = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static final String A = "abcdeabcdef";
    public static final String B = "aaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddabcdeabcdefaaddddaaddddaadddd";

    // 声明ThreadLocal
    private static ThreadLocal<Integer> result = new ThreadLocal<>();

    // 在子线程中赋予自己的值
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

    // 在子线程中赋予自己的值
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

    private int testString() {
        Log.e(App.TAG, "Begin");
        runOnUiThread(new Thread1());
        Log.e(App.TAG, "Result from thread is: " + result.get() + "");

        int lenA = A.length();
        int lenB = B.length();
        for (int i = 0; i < lenB; i++) {
            boolean isMatch = true;
            for (int j = 0; j < lenA; j++) {
                if (A.charAt(j) != B.charAt(i + j)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void initView() {
        super.initView();
        UserModel model = ViewModelProviders.of(this).get(UserModel.class);
        model.setAge("29");
        model.setName("Lihua");

        getLifecycle().addObserver(new MyObserver());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    private void bindWeatherService() {
        Intent serviceIntent = new Intent(this, WeatherService.class);
        startService(serviceIntent);
    }

    @OnClick({R.id.btn_go_to_b,
            R.id.btn_go_to_thread,
            R.id.btn_start_service,
            R.id.btn_thread_test,
            R.id.btn_start_download,
            R.id.btn_test_mvp,
            R.id.btn_test_mvp_wrap,
            R.id.btn_test_mvp_dagger,
            R.id.btn_data_binding,
            R.id.btn_view_model,
            R.id.btn_recycleview,
            R.id.btn_video_demo,
            R.id.btn_listview})
    public void onClick(View view) {
        if (view == btnGoToB) {
            startActivity(new Intent(this, ConstaintActivity.class));
        } else if (view == btnGoToThread) {
            Intent intent = new Intent(this, ThreadLearnActivity.class);
            startActivity(intent);
        } else if (view == startService) {
            bindWeatherService();
        } else if (view == startThreadTest) {
            Intent intent = new Intent(this, ThreadTestActivity.class);
            startActivity(intent);
        } else if (view == startDownloadTest) {
            Intent intent = new Intent(this, DownloadTestActivity.class);
            startActivity(intent);
        } else if (view == btnTestMvp) {
            goTo(Routers.Act_MvpDemo);
        } else if (view == btnTestMvpWrap) {
            goTo(Routers.Act_MvpWrap);
        } else if (view == btnTestMvpDagger) {
            goTo(Routers.Act_Dagger);
        } else if (view == btnDatabinding) {
            goTo(Routers.Act_DataBinding);
        } else if (view == btnViewModel) {
            goTo(Routers.Act_ViewModel);
        } else if (view == btnMaterial) {
            goTo(Routers.Act_RecyclerView);
        } else if (view == btnVideoDemo) {
            goTo(Routers.Act_Video);
        } else if (view == btnListView) {
            goTo(Routers.Act_ListView);
        }
    }

    private void testLizhiFM() {
        long time1 = System.currentTimeMillis();
        Log.e(App.TAG, "Result is: " + testString());
        Log.e(App.TAG, "End");
        Log.e(App.TAG, "Time elapse: " + (System.currentTimeMillis() - time1) + "ms");
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

    private void testPool() {
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
