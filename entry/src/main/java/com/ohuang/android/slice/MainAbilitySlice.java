package com.ohuang.android.slice;

import com.ohuang.android.ResourceTable;

import com.ohuang.android.fragment.ListFragment;
import com.ohuang.android.fragment.PageSliceFragment;
import com.ohuang.hmwidget.fragment.Fragment;
import com.ohuang.hmwidget.fragment.FragmentAbilitySlice;
import com.ohuang.hmwidget.fragment.FragmentFraction;
import com.ohuang.hmwidget.navigation.NavigationBuilder;
import com.ohuang.hmwidget.navigation.NavigationTab;
import com.ohuang.hmwidget.navigation.NavigationView;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ComponentContainer;
import ohos.agp.utils.Color;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends FragmentAbilitySlice {
    private ComponentContainer stackLayout;

    private Fragment thisFragment;
    private List<Fragment> list = new ArrayList<>();
    private NavigationView navigationButton;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        stackLayout = (ComponentContainer) findComponentById(ResourceTable.Id_stackLayout);
        navigationButton = (NavigationView) findComponentById(ResourceTable.Id_navigationView);


        initNavgation();

        list.add(new PageSliceFragment());
        list.add(new ListFragment());

        thisFragment = list.get(0);
        getFragmentManager().beginTransaction().add(ResourceTable.Id_stackLayout, thisFragment).commit();
        navigationButton.setOnSelectListener(new NavigationView.OnSelectListener() {
            @Override
            public boolean onSelect(NavigationView navigationView, int index) {
                showFragment(list.get(index));
                return false;

            }
        });

    }

    private void initNavgation() {
        NavigationBuilder navigationBuilder = navigationButton.getNavigationBuilder().
                setTextColor(new Color(Color.rgb(0, 0, 0)))
                .setSelectTextColor(new Color(Color.rgb(255, 0, 0)))
                .setDefaultDrawableId(ResourceTable.Media_icon)
                .setSelectDrawableId(ResourceTable.Media_icon_hj)
                .setSpaceTop(10)
                .setSpaceBottom(10)
                .setSpace(5)
                .setText("pageSlice");
        NavigationTab navigationTab = navigationBuilder.build();
        NavigationTab navigationTab1 = navigationBuilder.setText("list").build();

        List<NavigationTab> navigationTabs = new ArrayList<>();
        navigationTabs.add(navigationTab);
        navigationTabs.add(navigationTab1);

        navigationButton.setTabList(navigationTabs);
    }

    public void replaceFragment(Fragment fg){
        if (fg != thisFragment) {
            getFragmentManager().beginTransaction()
                    .replace(ResourceTable.Id_stackLayout,fg)
                    .commit();
            thisFragment=fg;
        }
    }

    public void showFragment(Fragment fg) {
        if (fg != thisFragment) {
            if (fg.isAdd()) {
                getFragmentManager().beginTransaction()
                        .hide(thisFragment)
                        .show(fg)
                        .commit();
            } else {
                getFragmentManager().beginTransaction()
                        .hide(thisFragment)
                        .add(ResourceTable.Id_stackLayout, fg)
                        .commit();
            }
            thisFragment = fg;
        }
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
