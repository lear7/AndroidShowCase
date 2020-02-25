package com.lear7.showcase.common.net.helper

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lear7.showcase.App
import com.lear7.showcase.common.Urls
import com.lear7.showcase.common.net.api.KtApiService
import com.lear7.showcase.common.utils.NetWorkUtils
import com.skydoves.whatif.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

class CoroutineRetrofit private constructor() {

    companion object {
        val instance: CoroutineRetrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CoroutineRetrofit()
        }
    }

    private var retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(s: String) {
                Timber.d(s)
            }
        })
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
        }

        val builder = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)

        val httpCacheDirectory = File(App.getInstance().cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache).addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                if (!NetWorkUtils.isNetworkAvailable(App.getInstance())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build()
                }
                val response = chain.proceed(request)
                if (!NetWorkUtils.isNetworkAvailable(App.getInstance())) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=$maxAge")
                            .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                            .build()
                }
                return response
            }
        })

        val client = builder.build()

        val gson = GsonBuilder()
                .setLenient() // 宽松模式
                .disableHtmlEscaping() // 防止对“=”转换出现乱码
                .create()

        retrofit = Retrofit.Builder()
                .baseUrl(Urls.HOST)
                .client(client)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    val getApiService by lazy {
        retrofit.create(KtApiService::class.java)
    }
}