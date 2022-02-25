package com.ohuang.android.fragment;

import com.ohuang.android.ResourceTable;
import com.ohuang.android.base.BaseFragment;

import com.ohuang.hmwidget.fragment.Fragment;
import com.ohuang.hmwidget.fragment.pageSlider.FragmentPagerProvider2;
import com.ohuang.hmwidget.livedata.LiveData;
import com.ohuang.hmwidget.livedata.LiveDataBus;
import com.ohuang.hmwidget.livedata.Observer;
import ohos.agp.components.PageSlider;
import ohos.agp.components.TabList;

import java.util.ArrayList;
import java.util.List;

public class PageSliceFragment extends BaseFragment {
    private TabList tabList;
    private PageSlider pageSlider;

    private List<Fragment> fragmentList;
    private FragmentPagerProvider2 pageSliderProvider;

    @Override
    public int getXmlId() {
        return ResourceTable.Layout_fragment_pageslice;
    }

    @Override
    protected void initComponent() {
        pageSlider = findComponentById(ResourceTable.Id_page_slider);
        tabList = findComponentById(ResourceTable.Id_tab_list);
        addFragment();


        initTabList();

        initPageSlice();
    }

    private void initPageSlice() {
        pageSliderProvider = new FragmentPagerProvider2(getChildFragmentManager()) {
            @Override
            public Fragment getFragment(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        pageSlider.setProvider(pageSliderProvider);

        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int i, float v, int i1) {

            }

            @Override
            public void onPageSlideStateChanged(int i) {

            }

            @Override
            public void onPageChosen(int i) {
                tabList.selectTabAt(i);
                pageSliderProvider.selectAndRemove(i, 1); //用于改变fragment状态
            }
        });
    }

    private void initTabList() {
        for (int i = 0; i < 5; i++) {
            TabList.Tab tab1 = tabList.new Tab(getContext());
            tab1.setText("item" + i);
            tabList.addTab(tab1);
        }
        tabList.setTabLength(200);
        tabList.setTabMargin(20);
        tabList.selectTab(tabList.getTabAt(0));
        tabList.setFixedMode(false);
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                for (int i = 0; i < tabList.getTabTextSize(); i++) {
                    if (tabList.getTabAt(i) == tab) {
                        pageSlider.setCurrentPage(i); //pageSlice跳转
                        pageSliderProvider.selectAndRemove(i, pageSlider.getCachedPagesLimit()); //改变fragment状态
                        break;
                    }
                }
            }

            @Override
            public void onUnselected(TabList.Tab tab) {

            }

            @Override
            public void onReselected(TabList.Tab tab) {

            }
        });
    }

    private void addFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ImgFragment());
        fragmentList.add(new ListFragment());
        fragmentList.add(new ImgFragment());
        fragmentList.add(new ListFragment());
        fragmentList.add(new ImgFragment());

    }
}
