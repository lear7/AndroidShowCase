package com.lear7.showcase.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.beans.WeatherBean;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.events.BaseEvent;
import com.lear7.showcase.net.api.ApiService;
import com.lear7.showcase.net.helper.BaseSubscriber;
import com.lear7.showcase.net.helper.DataManager;
import com.lear7.showcase.net.helper.RxRetrofit;
import com.lear7.showcase.service.WeatherIntentService;
import com.lear7.showcase.service.WeatherService;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Route(path = Routers.Act_ThreadLearning)
public class ThreadLearnActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.btn_get_weather_loader)
    Button btnGetWeatherLoader;

    @BindView(R.id.btn_get_weather_async)
    Button btnGetWeatherAsync;

    @BindView(R.id.btn_get_weather_handlethread)
    Button btnGetWeatherHandlerThread;

    @BindView(R.id.btn_get_weather_intentservice)
    Button btnGetWeatherIntentService;

    @BindView(R.id.btn_get_weather_service)
    Button btnGetWeatherByService;

    @BindView(R.id.btn_get_weather_okhttp)
    Button btnGetWeatherOkhttp;

    @BindView(R.id.btn_get_weather_retrofit)
    Button btnGetWeatherRetrofit;

    @BindView(R.id.btn_get_weather_rxretrofit)
    Button btnGetWeatherRxRetrofit;

    @BindView(R.id.btn_get_by_callable)
    Button btnGetByCallable;

    private WeatherService.ServiceBinder aBinder;

    @OnClick({R.id.btn_get_weather_loader,
            R.id.btn_get_weather_async,
            R.id.btn_get_weather_handlethread,
            R.id.btn_get_weather_intentservice,
            R.id.btn_get_weather_service,
            R.id.btn_get_weather_okhttp,
            R.id.btn_get_weather_retrofit,
            R.id.btn_get_weather_rxretrofit,
            R.id.btn_get_by_callable})
    public void onClick(View view) {
        if (view == btnGetWeatherLoader) {
            textView.setText("");
            getWeatherByLoader();
            // 触发内容变化，以引发forceLoad();
//            onContentChanged();
        } else if (view == btnGetWeatherAsync) {
            textView.setText("");
            getWeatherByAsync();
        } else if (view == btnGetWeatherHandlerThread) {
            textView.setText("");
            getWeatherByHandlerThread();
        } else if (view == btnGetWeatherIntentService) {
            textView.setText("");
            getWeatherByIntentService();
        } else if (view == btnGetWeatherByService) {
            textView.setText("");
            getWeatherByService();
        } else if (view == btnGetWeatherRetrofit) {
            textView.setText("");
            getWeatherByRetrofit();
        } else if (view == btnGetWeatherRxRetrofit) {
            textView.setText("");
            getWeatherByRxRetrofit();
        } else if (view == btnGetWeatherOkhttp) {
            textView.setText("");
            getWeatherByOkhttp();
        } else if (view == btnGetByCallable) {
            textView.setText("");
            getByCallable();
        } else if (view == btnGetByCallable) {
            textView.setText("");
            getByCallable();
        }
    }

    private void getWeatherByOkhttp() {
        //创建被观察者，创建一个发射器，并发射一个事件
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(DataManager.getWeatherByOkHttp("From RxJava\n"));
            }
        });

        //创建观察者
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String mResponse) throws Exception {
                setWeatherInfo(mResponse);
            }
        };

        // 之后，订阅
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

        // 简单写法
        Observable.create(emitter -> emitter.onNext(DataManager.getWeatherByOkHttp("From RxJava\n")))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> setWeatherInfo((String) response));
    }

    private void getWeatherByRetrofit() {
        DataManager.getWeatherByRetrofit("From Retrofit\n");
    }

    private void getWeatherByRxRetrofit() {
        // DataManager.getWeatherByRxRetrofit("From RxRetrofit\n");

        RxRetrofit.getInstance()
                .create(ApiService.class)
                .getWeatherInfoRx("101280101", "19251592", "5cUief7V")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WeatherBean>() {

                    @Override
                    public void onNext(WeatherBean o) {
                        setWeatherInfo("From RxRetrofit\n" + o.getCity());
                    }
                });
    }

    private void getWeatherByHandlerThread() {
        HandlerThread thread = new HandlerThread("mHandler");
        thread.start();

        Handler handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                setWeatherInfo((String) msg.obj);
            }
        };

        handler.post(() -> {
            String weather = DataManager.getWeatherByOkHttp("From HandlerThread\n");
            // 这里是另一个线程，不能直接与UI线程进行交互
            // * setWeatherInfo(weather);
            EventBus.getDefault().post(new BaseEvent(weather));
        });

    }

    private void getWeatherByIntentService() {
        Intent intent = new Intent(this, WeatherIntentService.class);
        startService(intent);
    }

    private void getWeatherByService() {
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Timber.d("Service connected");
                aBinder = (WeatherService.ServiceBinder) iBinder;
                if (aBinder != null) {
                    setWeatherInfo(aBinder.getService().getTempData());
                }
                // 这里才是绑定成功的操作
                // textHint.setText(aBinder.getService().getWeatherInfo());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Timber.d("Service disconnected");
                aBinder = null;
            }
        };

        Intent serviceIntent = new Intent(this, WeatherService.class);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_thread_learn;
    }

    private void testRxJava4() {
        Observable.just("Hello World").subscribe(s -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());

        Observable.just("A")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                    }
                });
    }

    private static class WeatherTask extends AsyncTask<Void, Integer, String> {

        private WeakReference<TextView> textView;

        public WeatherTask(TextView textView) {
            this.textView = new WeakReference<TextView>(textView);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return DataManager.getWeatherByOkHttp("From AsyncTask\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (textView.get() != null) {
                textView.get().setText(s);
            }
        }
    }


    @Override
    public void handleEvent(BaseEvent event) {
        super.handleEvent(event);
        setWeatherInfo(event.getData());
    }

    private void setWeatherInfo(String s) {
        textView.setText(s);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("oneResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.d("onStop");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Timber.d("onConfigurationChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.d("onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Timber.d("onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("weatherinfo", textView.getText().toString());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("newdata", "this is a data before reboot");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getString("weatherinfo") != null) {
            textView.setText(savedInstanceState.getString("weatherinfo"));
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if (savedInstanceState != null && savedInstanceState.getString("newdata") != null) {
            textView.setText(savedInstanceState.getString("newdata"));
        }
    }

    private void getWeatherByAsync() {
        new WeatherTask(textView).execute();
    }

    public static class WeatherLoader extends AsyncTaskLoader {

        public WeatherLoader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public String loadInBackground() {
            return DataManager.getWeatherByOkHttp("From AsyncTaskLoader\n");
        }

        // 这两个方法一定要加，否则不启动
        @Override
        protected void onStartLoading() {
            if (takeContentChanged()) {
                forceLoad();
            }
        }

        // 这两个方法一定要加，否则不启动
        @Override
        protected void onStopLoading() {
            cancelLoad();
        }

    }

    private void getWeatherByLoader() {
        // 创建Loader
        WeatherLoader loader = new WeatherLoader(this);

        // 创建回调
        LoaderManager.LoaderCallbacks callbacks = new LoaderManager.LoaderCallbacks() {
            @NonNull
            @Override
            public Loader onCreateLoader(int id, @Nullable Bundle args) {
                // 创建Loader
                return loader;
            }

            @Override
            public void onLoadFinished(@NonNull Loader loader, Object data) {
                // Loader结束
                if (data instanceof String) {
                    setWeatherInfo((String) data);
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader loader) {

            }

        };

        // 启动Loader
//        LoaderManager.getInstance(this).restartLoader(1, null, callbacks);
        // 强制启动
        LoaderManager.getInstance(this).initLoader(1, null, callbacks).forceLoad();
    }

    public class GetWeatherCallable implements Callable<String> {
        @Override
        public String call() {
            return DataManager.getWeatherByOkHttp("From Callable:\n");
        }
    }

    private void getByCallable() {
        Callable<String> task = () -> DataManager.getWeatherByOkHttp("From Callable:\n");

        //  1. 通过线程池+Callable+Future接口
        ExecutorService executor1 = Executors.newCachedThreadPool();
        Future<String> result = executor1.submit(task);
        executor1.shutdown();

//        // 2. 通过线程池+Callable+FutureTask
//        ExecutorService executor2 = Executors.newCachedThreadPool();
//        FutureTask<String> result = new FutureTask<>(task);
//        executor2.submit(result);
//        executor2.shutdown();
//
//        // 3.通过新线程+FutureTask
//        FutureTask<String> result = new FutureTask<>(task);
//        Thread thread = new Thread(result);
//        thread.start();
//
//        // 展示结果
        new Thread(() -> {
            while (true) {
                if (result.isDone()) {
                    String resultStr = "not ready";
                    try {
                        resultStr = result.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String finalResultStr = resultStr;
                    runOnUiThread(() -> {
                        setWeatherInfo(finalResultStr);
                    });
                    break;
                }
            }
        }).start();
    }


}
