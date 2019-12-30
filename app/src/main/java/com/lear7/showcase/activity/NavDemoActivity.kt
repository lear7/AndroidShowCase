package com.lear7.showcase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers

@Route(path = Routers.Act_NAV)
class NavDemoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_nav_demo
    }

//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        setContentView(R.layout.activity_nav_demo)
//    }

}
