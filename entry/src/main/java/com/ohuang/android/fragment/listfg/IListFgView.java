package com.ohuang.android.fragment.listfg;


import com.ohuang.android.bean.WYNewsBean;
import com.ohuang.android.mvp.BaseView;

public interface IListFgView extends BaseView {

     void LoadData(WYNewsBean wyNewsBean);

     void ShowToast(String s);

     void loadFail();



}
