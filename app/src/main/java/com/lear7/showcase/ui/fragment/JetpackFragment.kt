package com.lear7.showcase.ui.fragment

import com.lear7.showcase.R
import com.lear7.showcase.common.Routers
import com.lear7.showcase.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack.*

class JetpackFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_jetpack
    }

    override fun initView() {
        super.initView()
        btn_data_binding.setOnClickListener { goTo(Routers.Act_DataBinding) }
        btn_view_model.setOnClickListener { goTo(Routers.Act_ViewModel) }
        btn_lifecycle.setOnClickListener { goTo(Routers.Act_Lifecycle) }
        btn_movie_app.setOnClickListener { goTo(Routers.Act_MovieApp) }
        btn_nav_demo.setOnClickListener { goTo(Routers.Act_NAV) }
        btn_bottom_nav.setOnClickListener { goTo(Routers.Act_BottomNav) }
        btn_room_demo.setOnClickListener { goTo(Routers.Act_RoomDemo) }
    }
}