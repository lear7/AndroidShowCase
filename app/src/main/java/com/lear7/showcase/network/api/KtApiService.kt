package com.lear7.showcase.network.api

import com.lear7.showcase.model.WeatherBean
import com.lear7.showcase.network.Urls
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface KtApiService {
    // https://www.tianqiapi.com/api/?version=v1&cityid=101110101&appid=19251592&appsecret=5cUief7V
// 给Retrofit用的
    @GET(Urls.HOST)
    fun getWeatherInfoCoroutine(@Query("cityid") cityid: String?, @Query("appid") appid: String?, @Query("appsecret") appsecret: String?): Deferred<WeatherBean>

}