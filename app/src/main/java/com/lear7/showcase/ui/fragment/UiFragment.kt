package com.lear7.showcase.ui.fragment

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lear7.showcase.R
import com.lear7.showcase.common.Routers
import com.lear7.showcase.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ui.*
import kotlinx.android.synthetic.main.layout_bottom.*
import timber.log.Timber

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

        var bottomSheetBehavior = BottomSheetBehavior.from(layout_bottom_sheet)
        btn_bottom_sheet1.setOnClickListener {
            bottomSheetBehavior.apply {
                if (this.state != BottomSheetBehavior.STATE_EXPANDED) {
                    this.setState(BottomSheetBehavior.STATE_EXPANDED)
                } else {
                    this.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Timber.d("BottomSheet Hidden")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Timber.d("BottomSheet Expanded")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })
    }
}