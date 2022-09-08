package com.lear7.showcase.ui.fragment

import com.lear7.showcase.R
import com.lear7.showcase.routing.Routers
import com.lear7.showcase.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ui.*

class UiFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_ui
    }

    override fun initView() {
        super.initView()
        btn_fragment.setOnClickListener { goTo(Routers.Act_Fragment) }
        btn_constraint.setOnClickListener { goTo(Routers.Act_Constaint) }
        btn_material.setOnClickListener { goTo(Routers.Act_Material) }
        btn_material_alipay.setOnClickListener { goTo(Routers.Act_MaterialAlipay) }
        btn_listview.setOnClickListener { goTo(Routers.Act_ListView) }
        btn_ui_adaption.setOnClickListener { goTo(Routers.Act_UIAdaption) }
        btn_auto_load_listview.setOnClickListener { goTo(Routers.Act_AutoLoadListView) }
        btn_bottom_sheet.setOnClickListener { goTo(Routers.Act_BottomSheet) }
        btn_transition.setOnClickListener { goTo(Routers.Act_Transition) }
    }
}