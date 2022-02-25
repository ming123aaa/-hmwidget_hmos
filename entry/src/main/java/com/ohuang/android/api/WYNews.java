package com.ohuang.android.api;



import com.ohuang.android.bean.WYNewsBean;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WYNews {

    @GET("getWangYiNews")
    Observable<WYNewsBean> getWyNews(@Query("page")String page, @Query("count")String count);
}
