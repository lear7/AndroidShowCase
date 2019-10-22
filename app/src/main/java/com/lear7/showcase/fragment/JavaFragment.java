package com.lear7.showcase.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProviders;

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

public class JavaFragment extends BaseFragment {


    @BindView(R.id.btn_go_to_thread)
    Button btnGoToThread;

    @BindView(R.id.btn_start_service)
    Button startService;

    @BindView(R.id.btn_thread_test)
    Button startThreadTest;

    @BindView(R.id.btn_start_download)
    Button startDownloadTest;


    @BindView(R.id.btn_video_demo)
    Button btnVideoDemo;


    @OnClick({
            R.id.btn_go_to_thread,
            R.id.btn_start_service,
            R.id.btn_thread_test,
            R.id.btn_start_download,
            R.id.btn_video_demo,
    })
    public void onClick(View view) {
        if (view == btnGoToThread) {
            goTo(Routers.Act_ThreadLearning);
        } else if (view == startService) {
            bindWeatherService();
        } else if (view == startThreadTest) {
            goTo(Routers.Act_ThreadTest);
        } else if (view == startDownloadTest) {
            goTo(Routers.Act_DownloadTest);
        } else if (view == btnVideoDemo) {
            goTo(Routers.Act_Video);
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

    private void setViewModel() {
        UserModel model = ViewModelProviders.of(this).get(UserModel.class);
        model.setAge("29");
        model.setName("Lihua");
        getLifecycle().addObserver(new MyObserver());
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

    private void bindWeatherService() {
        Intent serviceIntent = new Intent(getContext(), WeatherService.class);
        getContext().startService(serviceIntent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_java;
    }
}
