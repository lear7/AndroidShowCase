package com.lear7.showcase.activity

import android.view.animation.AnimationUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers
import kotlinx.android.synthetic.main.activity_customer_view.*

@Route(path = Routers.Act_CustomerView)
class CustomerViewActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_customer_view
    }

    override fun initView() {
        super.initView()
        custom_run_anim.setOnClickListener {
            // 设置动画
            custom_view.animation = AnimationUtils.loadAnimation(this, R.anim.translate)
        }
    }
}
