package com.ohuang.hmwidget.fragment;

import java.util.Iterator;
import java.util.function.Consumer;

class FragmentLifecycleDispatcher {
    private FragmentManager fragmentManager;
    FragmentLifecycleDispatcher(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }

     void dispatchOnActive(){
        synchronized (this) {
            Iterator<Fragment> iterator = fragmentManager.fragmentList.fragments.iterator();
            iterator.forEachRemaining(new Consumer<Fragment>() {
                @Override
                public void accept(Fragment fragment) {
                    fragment.onFragmentActive();
                }
            });
        }
    }


     void dispatchOnInactive(){
        synchronized (this) {
            Iterator<Fragment> iterator = fragmentManager.fragmentList.fragments.iterator();
            iterator.forEachRemaining(new Consumer<Fragment>() {
                @Override
                public void accept(Fragment fragment) {
                    fragment.onFragmentInactive();
                }
            });
        }
    }

     void dispatchOnBackground(){
        synchronized (this) {
            Iterator<Fragment> iterator = fragmentManager.fragmentList.fragments.iterator();
            iterator.forEachRemaining(new Consumer<Fragment>() {
                @Override
                public void accept(Fragment fragment) {
                    fragment.onFragmentBackground();
                }
            });
        }
    }

     void dispatchOnForeground(){
        synchronized (this) {
            Iterator<Fragment> iterator = fragmentManager.fragmentList.fragments.iterator();
            iterator.forEachRemaining(new Consumer<Fragment>() {
                @Override
                public void accept(Fragment fragment) {
                    fragment.onFragmentForeground();
                }
            });
        }
    }

    void dispatchUserVisible(boolean isVisible){
        Iterator<Fragment> iterator = fragmentManager.fragmentList.fragments.iterator();
        iterator.forEachRemaining(new Consumer<Fragment>() {
            @Override
            public void accept(Fragment fragment) {
                if(fragment.userVisible){
                    fragment.dispatchUserVisible(isVisible);
                }
            }
        });
    }




}
