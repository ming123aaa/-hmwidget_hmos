package com.ohuang.hmwidget.fragment.pageSlider;


import com.ohuang.hmwidget.fragment.Fragment;
import com.ohuang.hmwidget.fragment.FragmentManager;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PageSliderProvider;

import java.util.ArrayList;
import java.util.List;


public abstract class FragmentPagerProvider2 extends PageSliderProvider {

    protected FragmentManager fragmentManager;
    private List<Integer> needRemoveFragments = new ArrayList<>();


    public FragmentPagerProvider2(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }


    public abstract Fragment getFragment(int i);

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        Fragment fg = getFragment(i);
        if (fg.getComponent() == null) {
            fragmentManager.beginTransaction().attach(fg).commitNow();
        }

        Component component = fg.getComponent();
        componentContainer.addComponent(component);
        return component;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent((Component) o);
        destroyComponent(fragmentManager, i, o);
    }


    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        return component == o;
    }

    protected void destroyComponent(FragmentManager fragmentManager, int i, Object o) {
        getFragment(i).setUserVisibleChange(false);
        needRemoveFragments.add(i);

    }

    /**
     * 设置选中的page  会根据缓存的大小清除不需要缓存fragment
     *
     * @param select    选中fragment索引
     * @param cacheSize 左右两边缓存大小
     */
    public void selectAndRemove(int select, int cacheSize) {
        if (getFragment(select).getComponent()!=null) {
            getFragment(select).setUserVisibleChange(true);
        }

        int min = select - cacheSize;
        int max = select + cacheSize;
        int left=0,right=0;
        if (min < 0) {
            left=-min;
        }
        if (max >= getCount()) {
            right=max-getCount()+1;
        }
        min=min-right;
        max=max+left;
        if (min<0){
            min=0;
        }
        if (max>=getCount()){
            max=getCount()-1;
        }

        for (int i = min; i <= max; i++) {
            if (i != select) {
                getFragment(i).setUserVisibleChange(false);
            }
        }
        int size=needRemoveFragments.size();
        for (int i = 0; i < size; i++) {
            int j=needRemoveFragments.get(i);
            if (max<j||min>j){
                fragmentManager.beginTransaction().remove(getFragment(j)).commitNow();

            }
        }
        needRemoveFragments.clear();

    }


}
