package com.lear7.showcase.fragment

import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers
import kotlinx.android.synthetic.main.fragment_java.*

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
    }
}