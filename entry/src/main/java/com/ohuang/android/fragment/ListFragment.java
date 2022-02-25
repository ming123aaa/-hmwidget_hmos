package com.ohuang.android.fragment;

import com.ohuang.android.ResourceTable;
import com.ohuang.android.base.BaseFragment;
import com.ohuang.android.bean.WYNewsBean;
import com.ohuang.android.fragment.listfg.IListFgView;
import com.ohuang.android.fragment.listfg.ListFgPresenter;
import com.ohuang.android.fragment.listfg.NewsProvider;
import com.ohuang.android.mvp.BaseMvpFragment;
import ohos.agp.components.ListContainer;

import java.util.LinkedList;
import java.util.List;

public class ListFragment extends BaseMvpFragment<ListFgPresenter> implements IListFgView {
    @Override
    public int getXmlId() {
        return ResourceTable.Layout_fragment_list;
    }

    private ListContainer listContainer;
    private NewsProvider listProvider;
    private List<WYNewsBean.ResultBean> list=new LinkedList<>();
    int page=1;
    private boolean lastVisible=false;
    private boolean isLoad=false;
    @Override
    protected void initComponent() {
        listContainer=  findComponentById(ResourceTable.Id_list_view);
        mPresenter=new ListFgPresenter();
        mPresenter.attachView(this);
        listProvider=new NewsProvider(list,getContext());
        listContainer.setItemProvider(listProvider);


    }


    //fragment 可见性状态改变
    @Override
    public void userVisibleChange(boolean isVisible) {
        if (!lastVisible&isVisible){
            loadData();//数据懒加载
        }

        lastVisible=isVisible;
    }

     //实现懒加载
    private void loadData(){
        if (!isLoad){
            mPresenter.LoadData(page++);
            isLoad=true;
        }
    }

    @Override
    public void LoadData(WYNewsBean wyNewsBean) {
        list.addAll(0,wyNewsBean.getResult());
        listProvider.notifyDataChanged();
    }

    @Override
    public void ShowToast(String s) {

    }

    @Override
    public void loadFail() {
        isLoad=false;
    }
}
