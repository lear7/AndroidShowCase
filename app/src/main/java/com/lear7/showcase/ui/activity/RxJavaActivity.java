package com.lear7.showcase.ui.activity;

import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.common.Routers;
import com.lear7.showcase.common.net.helper.DataManager;
import com.lear7.showcase.ui.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Route(path = Routers.Act_RxJava)
public class RxJavaActivity extends BaseActivity {

    @BindView(R.id.rxjava_text)
    TextView textView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void initView() {
        super.initView();
        testRxJava1();
        testRxJava2();
        testRxJava3();
        testRxJava4();
        testRxJava5();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void testRxJava1() {
        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                //调用观察者的回调
                emitter.onNext("From RxJava1");
                emitter.onError(new Throwable("出错了"));
                emitter.onComplete();
            }
        });

        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Timber.d("onError");
            }

            @Override
            public void onComplete() {
                Timber.d("onComplete");
            }
        };

        //注册，将观察者和被观察者关联，将会触发OnSubscribe.call方法
        observable.subscribe(observer);
    }


    private void testRxJava2() {
        Flowable.just("Test RxJava2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void testRxJava3() {
        Flowable.fromCallable(() -> {
            //  imitate expensive computation
            Thread.sleep(1000);
            return "Test RxJava3 and wait 1000 ms";
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    private void testRxJava4() {
        //创建被观察者，创建一个发射器，并发射一个事件
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(DataManager.getWeatherByOkHttp("From RxJava 4\n"));
            }
        });

        //创建观察者
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String mResponse) throws Exception {
                setText(mResponse);
            }
        };

        // 之后，订阅
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    private void testRxJava5() {
        // 简单写法
        Observable.create(emitter -> emitter.onNext(DataManager.getWeatherByOkHttp("From RxJava 5\n")))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> setText((String) response));

    }

    private void setText(String s) {
        textView.setText(s);
    }

}
