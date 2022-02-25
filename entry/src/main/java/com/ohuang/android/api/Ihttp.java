package com.ohuang.android.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ihttp {

    public static String baseUrl = "https://api.apiopen.top/";

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }

    private static Retrofit retrofit_gson;

    public static Retrofit getGsonRetrofit() {
        if (retrofit_gson == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit_gson = new Retrofit.Builder()
                    //设置数据解析器
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)

                    //设置网络请求的Url地址
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit_gson;
    }

    public  static WYNews getWYNes() {
        Retrofit retrofit = getGsonRetrofit();
        WYNews wyNews = retrofit.create(WYNews.class);
        return wyNews;
    }
}
