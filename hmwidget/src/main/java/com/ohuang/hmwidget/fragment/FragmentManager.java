package com.ohuang.hmwidget.fragment;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.app.Context;

public class FragmentManager {
    Ability ability;
    AbilitySlice abilitySlice;
    Fragment parentFragment;

    Fraction fraction;


    FragmentLifecycleDispatcher fragmentLifecycleDispatcher = new FragmentLifecycleDispatcher(this);
    FragmentList fragmentList = new FragmentList(this);

    final int UI_ABILITY = 0;
    final int UI_SLICE = 1;
    final int UI_FRAGMENT = 2;
    final int UI_FRACTION = 3;

    int ui_state = 0;//Ui界面类型

    FragmentManager(Ability ability) {
        this.ability = ability;
        this.ui_state = UI_ABILITY;
    }

    FragmentManager(AbilitySlice abilitySlice) {
        this.abilitySlice = abilitySlice;
        this.ui_state = UI_SLICE;
    }

    FragmentManager(Fraction fraction) {
        this.fraction = fraction;
        this.ui_state = UI_FRACTION;
    }

    FragmentManager(FragmentManager fragmentManager, Fragment fragment) {
        this.parentFragment = fragment;
        this.ability =fragmentManager.ability;
        this.abilitySlice = fragmentManager.abilitySlice;
        this.fraction = fragmentManager.fraction;
        this.ui_state = UI_FRAGMENT;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }


    public Context getContext() {
        if (abilitySlice != null) {
            return abilitySlice;
        } else if (fraction != null) {
            return fraction.getFractionAbility();
        } else {
            return ability;
        }
    }


    public Fraction getFraction() {
        return fraction;
    }

    public Ability getAbility() {
        if (abilitySlice != null) {
            return abilitySlice.getAbility();
        } else if (fraction != null) {
            return fraction.getFractionAbility();
        } else {
            return ability;
        }
    }

    public Fragment getParentFragment() {
        if (parentFragment == null) {
            throw new IllegalStateException("parentFragment is Null");
        }
        return parentFragment;
    }

    FragmentLifecycleDispatcher getFragmentLifecycleDispatcher() {
        return fragmentLifecycleDispatcher;
    }

    void addFragment(Fragment fragment) {
        fragmentList.addFragment(fragment);
    }

    void attachFragment(Fragment fragment) {
        fragmentList.attachFragment(fragment);
    }


    boolean fragmentIsAdd(Fragment fragment) {
        return fragmentList.fragmentIsAdd(fragment);
    }


    void showFragment(Fragment fragment) {
        fragmentList.showFragment(fragment);
    }

    void hideFragment(Fragment fragment) {
        fragmentList.hideFragment(fragment);
    }


    void replaceFragment(Fragment fragment) {
        fragmentList.replaceFragment(fragment);
    }

    void removeFragment(Fragment fragment) {
        fragmentList.removeFragment(fragment);
    }

    void removeAllFragment() {
        fragmentList.removeAllFragment();
    }

    public Fragment findFragmentById(int id) {
        return fragmentList.findFragmentById(id);
    }

    public Fragment findFragmentByTag(String tag) {
        return fragmentList.findFragmentByTag(tag);
    }

    public boolean findFragment(Fragment fragment) {
        return fragmentList.findFragment(fragment);
    }

}
