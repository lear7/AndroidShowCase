package com.lear7.showcase.net.helper;

import com.lear7.showcase.beans.WeatherBean;
import com.lear7.showcase.constants.Urls;
import com.lear7.showcase.events.BaseEvent;
import com.lear7.showcase.net.api.ApiService;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager {

    public static String getWeatherByRetrofit(String header) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.HOST)
                // Retrofit2之后返回的response是ResponseBody，需要增加转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<WeatherBean> response = service.getWeatherInfo("101280101", "19251592", "5cUief7V");
        response.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, retrofit2.Response<WeatherBean> response) {
                String data = header + "\n" + response.body().getCity();
                EventBus.getDefault().post(new BaseEvent(data));
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {

            }
        });
        return "";
    }


    public static void getWeatherByRxRetrofit(String header) {
        RxRetrofit.getInstance()
                .create(ApiService.class)
                .getWeatherInfoRx("101280101", "19251592", "5cUief7V")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WeatherBean>() {

                    @Override
                    public void onNext(WeatherBean o) {
                        String data = header + "\n" + o.getCity();
                        EventBus.getDefault().post(new BaseEvent(data));
                    }
                });
    }

    //使用okhttp访问网上提供的接口，由于是同步get请求，需要在子线程进行
    public static final String getWeatherByOkHttp(String header) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Urls.Weather_URL)
                .build();
        Response response;

        try {
            response = client.newCall(request).execute();
            return header + "\n" + response.body().string();
        } catch (IOException e) {
            return "error";
        }
    }
}
