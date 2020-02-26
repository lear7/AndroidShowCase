package com.lear7.showcase.network.api;

import com.lear7.showcase.model.WeatherBean;
import com.lear7.showcase.network.Urls;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    // https://www.tianqiapi.com/api/?version=v1&cityid=101110101&appid=19251592&appsecret=5cUief7V

    // 给Retrofit用的
    @GET(Urls.HOST)
    Call<WeatherBean> getWeatherInfo(@Query("cityid") String cityid, @Query("appid") String appid, @Query("appsecret") String appsecret);

    // 给RxJava用的
    @GET(Urls.HOST)
    Observable<WeatherBean> getWeatherInfoRx(@Query("cityid") String cityid, @Query("appid") String appid, @Query("appsecret") String appsecret);

    @GET(Urls.HOST_2 + "/{fileName}")  //{fileName}是动态码
    //GET下载文件必须结合@Streaming使用
    @Streaming
    Observable<ResponseBody> downloadImg(@Path("fileName") String fileName);

    @GET
    @Streaming
    Observable<ResponseBody> downloadImg2(@Url String imgUrl);
}
