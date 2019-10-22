package com.lear7.showcase.fragment

import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers
import kotlinx.android.synthetic.main.fragment_jetpack.*

class JetpackFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_jetpack
    }

    override fun initView() {
        super.initView()
        btn_data_binding.setOnClickListener { goTo(Routers.Act_DataBinding) }
        btn_view_model.setOnClickListener { goTo(Routers.Act_ViewModel) }
    }
}