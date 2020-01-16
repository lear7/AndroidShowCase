package com.lear7.showcase.activity

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers
import kotlinx.android.synthetic.main.activity_bottom_nav.*


@Route(path = Routers.Act_BottomNav)
class BottomNavActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_bottom_nav
    }

    override fun initView() {
        super.initView()
        setUpNavigation();
    }

    private fun setUpNavigation() {
        // 获取一个Fragment作为NavHostFragment
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        // 生成Nav
        NavigationUI.setupWithNavController(nav_bottom_view,
                navHostFragment!!.navController);

        // 屏蔽返回事件
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }


}
