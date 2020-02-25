package com.lear7.showcase.ui.fragment;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.lear7.showcase.R;
import com.lear7.showcase.ui.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LifecycleFragment extends BaseFragment {

    @BindView(R.id.lifecycle_hint)
    TextView textHint;

    public class TaskObserver implements androidx.lifecycle.LifecycleObserver {

        private Disposable disposable;

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            Timber.d("onCreate");
            // 每隔1s执行一次事件
            disposable = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        textHint.setText("Data is: " + String.valueOf(aLong));
                    });
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            Timber.d("onPause");
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    public LifecycleFragment() {
        // 要在构造函数中注册
        getLifecycle().addObserver(new TaskObserver());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lifecycle;
    }

}
