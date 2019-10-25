package viewmodel;

import android.os.SystemClock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class TimeViewModel extends ViewModel {

    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    private long mInitialTime;

    public TimeViewModel() {
        mInitialTime = SystemClock.elapsedRealtime();

        ScheduledExecutorService mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(() -> {
            final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
            // setValue要在主线程中执行
            // setValue() cannot be called from a background thread so post to main thread.
            mElapsedTime.postValue(newValue);
        }, 1, 1, TimeUnit.SECONDS);
    }

    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }
}