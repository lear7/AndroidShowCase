package com.lear7.showcase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers

@Route(path = Routers.Act_KOIN_STOP_WATCH)
class KoinStopwatchActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_koin_stopwatch
    }


}
