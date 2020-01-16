package com.lear7.showcase.net.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lear7.showcase.constants.Urls;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Singleton
public class RxRetrofit {

    // 使用双重检查
    private volatile static Retrofit instance;

    /**
     * 不需要在请求头添加东西,可以做成单例模式
     *
     * @return
     */
    public static Retrofit getInstance() {
        if (instance == null) {
            // 加锁
            synchronized (RxRetrofit.class) {
                // 双重检查
                if (instance == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor((string) ->
                            Timber.d(string));
                    interceptor.level(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(600, TimeUnit.SECONDS)
                            .writeTimeout(600, TimeUnit.SECONDS)
                            .build();


                    Gson gson = new GsonBuilder()
                            .setLenient() // 宽松模式
                            .disableHtmlEscaping() // 防止对“=”转换出现乱码
                            .create();

                    instance = new Retrofit.Builder()
                            .baseUrl(Urls.HOST)
                            .client(client)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }
        return instance;
    }

}