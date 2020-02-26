package com.lear7.showcase.component.mvvm

import android.os.SystemClock
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.routing.Routers
import com.lear7.showcase.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_model.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


@Route(path = Routers.Act_ViewModel)
class TimerActivity : BaseActivity() {

    private lateinit var timerViewModel: TimerViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_view_model
    }

    override fun initView() {
        super.initView()

        updateByTimer()
        updateByViewModel()
    }

    private fun updateByTimer() {
        var startTime = SystemClock.elapsedRealtime()

//        val timer = Timer()
//        timer.scheduleAtFixedRate(timerTask {
//            val newValue: Long = (SystemClock.elapsedRealtime() - startTime) / 1000
//            // setValue要在主线程中执行
//            // setValue() cannot be called from a background thread so post to main thread.
//            runOnUiThread {
//                view_model_textview.text = "Value from timer: ${newValue.toString()}"
//            }
//        }, 1000, 1000)

        val mScheduledExecutorService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        mScheduledExecutorService.scheduleAtFixedRate({
            val newValue: Long = (SystemClock.elapsedRealtime() - startTime) / 1000
            // setValue要在主线程中执行
            // setValue() cannot be called from a background thread so post to main thread.
            view_model_textview.text = "Value from timer: ${newValue.toString()}"
        }, 1, 1, TimeUnit.SECONDS)
    }

    private fun updateByViewModel() {
        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)
        val observer = Observer<Long> { newValue: Long ->
            view_model_textview_2.text = "Value from livedata: ${newValue.toString()}"
        }
        timerViewModel.elapsedTime.observe(this, observer)
    }

}
