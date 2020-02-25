package com.lear7.showcase.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.model.WeatherBean
import com.lear7.showcase.common.Routers
import com.lear7.showcase.common.net.helper.CoroutineRetrofit
import com.lear7.showcase.ui.base.BaseActivity
import com.skydoves.whatif.whatIfNotNull
import kotlinx.android.synthetic.main.activity_coroutine_demo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

@Route(path = Routers.Act_Coroutine)
class CoroutineDemoActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_coroutine_demo
    }

    override fun initView() {
        super.initView()
        btn_run.setOnClickListener {
            tv_routine.setText("")
            getWeather()
        }
    }

    fun getWeather() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                showWeather(CoroutineRetrofit.instance.getApiService
                        .getWeatherInfoCoroutine("101280101", "19251592", "5cUief7V")
                        .await())
            } catch (e: Exception) {
                showError(e.toString())
                Timber.e(e)
            }
        }
    }

    fun showWeather(w: WeatherBean) {
        w.whatIfNotNull {
            val showStr = "Get Succeed:\n" + w.city.toString()
            tv_routine.setText(showStr)
        }
    }

    fun showError(s: String) {
        val showStr = "Get Failed!\n"
        tv_routine.setText(showStr + s)
    }

}
