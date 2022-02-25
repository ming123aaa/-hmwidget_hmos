package com.ohuang.android.fragment.listfg;


import com.ohuang.android.api.Ihttp;
import com.ohuang.android.bean.WYNewsBean;
import io.reactivex.rxjava3.core.Observable;

public class ListFgModel {

    public Observable<WYNewsBean> loadData(String page, String count){
        return Ihttp.getWYNes().getWyNews(page, count);
    }

}
