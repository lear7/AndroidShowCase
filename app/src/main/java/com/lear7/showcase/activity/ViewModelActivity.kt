package com.lear7.showcase.activity

import android.os.SystemClock
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers
import kotlinx.android.synthetic.main.activity_view_model.*
import viewmodel.TimeViewModel
import java.util.*
import kotlin.concurrent.timerTask


@Route(path = Routers.Act_ViewModel)
class ViewModelActivity : BaseActivity() {

    private lateinit var timeViewModel: TimeViewModel

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
        val timer = Timer()
        timer.scheduleAtFixedRate(timerTask {
            val newValue: Long = (SystemClock.elapsedRealtime() - startTime) / 1000
            // setValue要在主线程中执行
            // setValue() cannot be called from a background thread so post to main thread.
            runOnUiThread {
                view_model_textview.text = "Value from timer: ${newValue.toString()}"
            }
        }, 1000, 1000)
    }

    private fun updateByViewModel() {
        timeViewModel = ViewModelProviders.of(this).get(TimeViewModel::class.java)
        val observer = Observer<Long> { newValue: Long ->
            view_model_textview_2.text = "Value from livedata: ${newValue.toString()}"
        }
        timeViewModel.elapsedTime.observe(this, observer)
    }

}
