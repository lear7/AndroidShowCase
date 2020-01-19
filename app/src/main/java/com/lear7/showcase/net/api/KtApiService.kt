package com.lear7.showcase.net.api

import com.lear7.showcase.beans.WeatherBean
import com.lear7.showcase.constants.Urls
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface KtApiService {
    // https://www.tianqiapi.com/api/?version=v1&cityid=101110101&appid=19251592&appsecret=5cUief7V
// 给Retrofit用的
    @GET(Urls.HOST)
    fun getWeatherInfoCoroutine(@Query("cityid") cityid: String?, @Query("appid") appid: String?, @Query("appsecret") appsecret: String?): Deferred<WeatherBean>

}