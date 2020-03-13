package com.lear7.showcase.ui.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lear7.showcase.R
import com.lear7.showcase.routing.Routers
import com.lear7.showcase.ui.base.BaseActivity
import com.lear7.showcase.ui.fragment.FullSheetDialogFragment
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import timber.log.Timber


@Route(path = Routers.Act_BottomSheet)
class BottomSheetActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_bottom_sheet
    }

    override fun initView() {
        super.initView()

        var bottomSheetBehavior = BottomSheetBehavior.from(layout_bottom_sheet)
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
        btn_bottom_sheet1.setOnClickListener {
            bottomSheetBehavior.apply {
                if (this.state != BottomSheetBehavior.STATE_EXPANDED) {
                    this.setState(BottomSheetBehavior.STATE_EXPANDED)
                } else {
                    this.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }
        }

        var dialog = BottomSheetDialog(this)
        val view: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
        dialog.setContentView(view)
        btn_bottom_sheet2.setOnClickListener {
            dialog.show()
        }

        btn_bottom_sheet3.setOnClickListener {
            FullSheetDialogFragment().show(supportFragmentManager, "dialog")
        }

    }
}