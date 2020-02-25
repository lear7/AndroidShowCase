package com.lear7.showcase.ui.fragment

import com.lear7.showcase.R
import com.lear7.showcase.common.Routers
import com.lear7.showcase.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mvp.*

class MvpFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mvp
    }

    override fun initView() {
        super.initView()
        btn_mvp_demo.setOnClickListener { goTo(Routers.Act_MvpDemo) }
        btn_mvp_demo_2.setOnClickListener { goTo(Routers.Act_MvpDemo2) }
        btn_dagger_demo.setOnClickListener { goTo(Routers.Act_DaggerDemo) }
        btn_mvp_dagger_demo.setOnClickListener { goTo(Routers.Act_MvpDagger) }
    }
}