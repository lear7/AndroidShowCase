package com.lear7.showcase.ui.fragment

import cn.leancloud.AVObject
import com.lear7.showcase.R
import com.lear7.showcase.routing.Routers
import com.lear7.showcase.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_java.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*


class JavaFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_java
    }

    override fun initView() {
        super.initView()
        btn_thread_learning.setOnClickListener { goTo(Routers.Act_ThreadLearning) }
        btn_thread_test.setOnClickListener { goTo(Routers.Act_ThreadTest) }
        btn_download_test.setOnClickListener { goTo(Routers.Act_DownloadTest) }
        btn_video_demo.setOnClickListener { goTo(Routers.Act_Video) }
        btn_video_demo2.setOnClickListener { goTo(Routers.Act_Video2) }
        btn_rx_java.setOnClickListener { goTo(Routers.Act_RxJava) }
        btn_printer.setOnClickListener { goTo(Routers.Act_Printer) }
        btn_video_call.setOnClickListener { goTo(Routers.Act_VideoCall) }
        btn_coroutine.setOnClickListener { goTo(Routers.Act_Coroutine) }
        btn_crash_handler.setOnClickListener { makeACrash() }

        doAsync {
            val testObject = AVObject("TestObject")
            testObject.put("First", "Message from Android device at: " + getCurrentTime())
            testObject.saveInBackground().blockingSubscribe()
        }

    }

    fun getCurrentTime(): String {
        var calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT+08")
        calendar.setTimeInMillis(System.currentTimeMillis())
        var template = SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS") // SimpleDateFormat
        var timeStr = template.format(calendar.time) // String
        return timeStr
    }

    fun makeACrash() {
        var name = "Edvard Li";
        toast("I am$name" + name[20])
    }


}