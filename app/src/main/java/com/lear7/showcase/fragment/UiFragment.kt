package com.lear7.showcase.fragment

import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers
import kotlinx.android.synthetic.main.fragment_ui.*

class UiFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_ui
    }

    override fun initView() {
        super.initView()
        btn_constraint.setOnClickListener { goTo(Routers.Act_Constaint) }
        btn_material.setOnClickListener { goTo(Routers.Act_Material) }
        btn_material_alipay.setOnClickListener { goTo(Routers.Act_MaterialAlipay) }
        btn_listview.setOnClickListener { goTo(Routers.Act_ListView) }
        btn_customview.setOnClickListener{goTo(Routers.Act_Printer)}
    }
}