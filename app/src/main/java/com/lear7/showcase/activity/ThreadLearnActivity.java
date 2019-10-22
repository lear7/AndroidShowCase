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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.App;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.net.helper.DataHelper;
import com.lear7.showcase.events.BaseEvent;
import com.lear7.showcase.service.WeatherIntentService;
import com.lear7.showcase.service.WeatherService;
import com.lear7.showcase.lifecycle.viewmodel.UserModel;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    @BindView(R.id.btn_get_weather_rx_okhttp)
    Button btnGetWeatherRxOkhttp;

    @BindView(R.id.btn_get_weather_retrofit)
    Button btnGetWeatherRetrofit;

    @BindView(R.id.btn_get_weather_rxretrofit)
    Button btnGetWeatherRxRetrofit;

    @BindView(R.id.btn_get_viewmodel_data)
    Button btnGetViewModelData;

    private WeatherService.ServiceBinder aBinder;

    @OnClick({R.id.btn_get_weather_loader,
            R.id.btn_get_weather_async,
            R.id.btn_get_weather_handlethread,
            R.id.btn_get_weather_intentservice,
            R.id.btn_get_weather_service,
            R.id.btn_get_weather_rx_okhttp,
            R.id.btn_get_weather_retrofit,
            R.id.btn_get_weather_rxretrofit, R.id.btn_get_viewmodel_data})
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
        } else if (view == btnGetWeatherRxOkhttp) {
            textView.setText("");
            testRxJava3();
        } else if (view == btnGetWeatherRetrofit) {
            textView.setText("");
            getWeatherByRetrofit();
        } else if (view == btnGetWeatherRxRetrofit) {
            textView.setText("");
            getWeatherByRxRetrofit();
        } else if (view == btnGetViewModelData) {
            UserModel model = ViewModelProviders.of(this).get(UserModel.class);
            if (model != null) {
                textView.setText("Data from UserModel: " + model.getName() + " " + model.getAge());
            }
        }
    }

    private void getWeatherByRetrofit() {
        DataHelper.getWeatherByRetrofit("From Retrofit\n");
    }

    private void getWeatherByRxRetrofit() {
        DataHelper.getWeatherByRxRetrofit("From RxRetrofit\n");
    }

    private void getWeatherByHandlerThread() {
        HandlerThread thread = new HandlerThread("mHandler");
        thread.start();

        Handler handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                showWeatherInfo((String) msg.obj);
            }
        };

        handler.post(() -> {
            String weather = DataHelper.getWeatherByOkHttp("From HandlerThread\n");
            // 这里是另一个线程，不能直接与UI线程进行交互
            // * showWeatherInfo(weather);
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
                Log.e(App.TAG, "Service connected");
                aBinder = (WeatherService.ServiceBinder) iBinder;
                if (aBinder != null) {
                    showWeatherInfo(aBinder.getService().getTempData());
                }
                // 这里才是绑定成功的操作
                // textView.setText(aBinder.getService().getWeatherInfo());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e(App.TAG, "Service disconnected");
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
            return DataHelper.getWeatherByOkHttp("From AsyncTask\n");
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
        showWeatherInfo(event.getData());
    }

    private void showWeatherInfo(String s) {
        textView.setText(s);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(App.TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(App.TAG, "oneResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(App.TAG, "onStop");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(App.TAG, "onCreate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(App.TAG, "onConfigurationChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(App.TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(App.TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(App.TAG, "onRestart");
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
            return DataHelper.getWeatherByOkHttp("From AsyncTaskLoader\n");
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
                    showWeatherInfo((String) data);
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

    private void testRxJava1() {
        Flowable.just("Hello World").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Toast.makeText(ThreadLearnActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println, Throwable::printStackTrace);

    }

    private void testRxJava2() {
        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                //调用观察者的回调
                emitter.onNext("我是");
                emitter.onNext("RxJava");
                emitter.onNext("简单示例");
                emitter.onError(new Throwable("出错了"));
                emitter.onComplete();
            }
        });

        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(App.TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(ThreadLearnActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(App.TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(App.TAG, "onComplete");
            }
        };

        //注册，将观察者和被观察者关联，将会触发OnSubscribe.call方法
        observable.subscribe(observer);
    }

    private void testRxJava3() {
        //创建被观察者
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(Apis.getWeatherByOkHttp());
//            }
//        });
        Observable<String> observable = Observable.create(emitter -> emitter.onNext(DataHelper.getWeatherByOkHttp("From RxJava\n")));

        //创建观察者
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String mResponse) throws Exception {
                showWeatherInfo(mResponse);
            }
        };
//        Consumer<String> consumer = response -> showWeatherInfo(response);

        //subscribeOn() 指定的是发送事件的线程, observeOn() 指定的是接收事件的线程.
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(consumer);

//        // 简单写法
        Observable.create(emitter -> emitter.onNext(DataHelper.getWeatherByOkHttp("From RxJava\n")))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> showWeatherInfo((String) response));
    }

}
