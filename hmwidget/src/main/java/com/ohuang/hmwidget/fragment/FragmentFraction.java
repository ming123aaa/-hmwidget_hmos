package com.ohuang.hmwidget.fragment;

import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;

public class FragmentFraction extends Fraction {

    private FragmentManager fragmentManager=new FragmentManager(this);

    public FragmentManager getFragmentManager(){
        return  fragmentManager;
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        fragmentManager.getFragmentLifecycleDispatcher().dispatchOnInactive();
    }

    @Override
    protected void onActive() {
        super.onActive();
        fragmentManager.getFragmentLifecycleDispatcher().dispatchOnActive();
    }

    @Override
    protected void onBackground() {
        super.onBackground();
        fragmentManager.getFragmentLifecycleDispatcher().dispatchOnBackground();
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        fragmentManager.getFragmentLifecycleDispatcher().dispatchOnForeground();
    }


    @Override
    protected void onComponentDetach() {
        fragmentManager.removeAllFragment();
        super.onComponentDetach();
    }
}
