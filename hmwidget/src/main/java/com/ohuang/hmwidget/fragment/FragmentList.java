package com.ohuang.hmwidget.fragment;


import com.ohuang.hmwidget.TextUtils;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;

import java.util.LinkedList;
import java.util.List;

class FragmentList {
    FragmentManager fragmentManager;

    public FragmentList(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    List<Fragment> fragments = new LinkedList<>();

    void addFragment(Fragment fragment) {
        ComponentContainer componentContainer = findContainerById(fragment.mId);
        if (componentContainer == null) {
            throw new IllegalStateException("添加Fragment失败  检查容器的Id是否正确");
        }
        if (fragment.root != null) {
            throw new RuntimeException("Fragment视图已经被创建了:" + fragment.getClass().getName());
        }
        fragment.onAttach(fragmentManager.getContext());

        fragment.CreateView(LayoutScatter.getInstance(fragmentManager.getContext()), componentContainer);


        if (fragment.root == null) {
            throw new IllegalStateException("Fragment.onCreateComponent() 返回值不能为空");
        }

        componentContainer.addComponent(fragment.root);
        fragment.isAdd = true;
        fragment.setFragmentManager(fragmentManager);
        fragment.onCreate();
        fragment.visibleChange(true);
        fragments.add(fragment);

    }

    void attachFragment(Fragment fragment) {

        if (fragment.root != null) {
            if (fragmentIsAdd(fragment)) {
                return;
            }
        }
        fragment.onAttach(fragmentManager.getContext());
        fragment.CreateView(LayoutScatter.getInstance(fragmentManager.getContext()), null);


        if (fragment.root == null) {
            throw new IllegalStateException("Fragment.onCreateComponent() 返回值不能为空");
        }

        fragment.setFragmentManager(fragmentManager);
        fragment.onCreate();
        fragments.add(fragment);

    }


    boolean fragmentIsAdd(Fragment fragment) {
        for (Fragment fragment1 : fragments) {
            if (fragment1 == fragment) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param id
     * @return
     */
    private ComponentContainer findContainerById(int id) {
        ComponentContainer componentContainer = null;
        if (fragmentManager.ui_state == fragmentManager.UI_FRAGMENT) {
            if (fragmentManager.parentFragment.root == null) {
                throw new RuntimeException("Fragment还没有创建完成 不能添加子fragment" +
                        ",建议在onCreate()方法中执行添加子fragment的操作," +
                        ":" + fragmentManager.parentFragment.getClass().getName());
            }
            componentContainer = (ComponentContainer) fragmentManager.parentFragment.root.findComponentById(id);

        } else if (fragmentManager.ui_state == fragmentManager.UI_SLICE) {
            componentContainer = (ComponentContainer) fragmentManager.abilitySlice.findComponentById(id);
        } else if (fragmentManager.ui_state == fragmentManager.UI_FRACTION) {
            componentContainer = (ComponentContainer) fragmentManager.fraction.getComponent().findComponentById(id);
        }else if (fragmentManager.ui_state == fragmentManager.UI_ABILITY){
            componentContainer = (ComponentContainer) fragmentManager.ability.findComponentById(id);
        }

        return componentContainer;
    }


    void showFragment(Fragment fragment) {
        if (fragment.root != null) {
            fragment.root.setVisibility(Component.VISIBLE);
            fragment.visibleChange(true);

        }
    }

    void hideFragment(Fragment fragment) {
        if (fragment.root != null) {
            fragment.root.setVisibility(Component.HIDE);
            fragment.visibleChange(false);

        }
    }


    void replaceFragment(Fragment fragment) {
        Fragment fragment1 = findFragmentById(fragment.mId);
        while (fragment1 != null) {
            if (fragment == fragment1) {
                return;
            }
            removeFragment(fragment1);
            fragment1 = findFragmentById(fragment.mId);
        }
        addFragment(fragment);
    }

    void removeFragment(Fragment fragment) {
        for (Fragment fg : fragments) {
            if (fg == fragment) {
                fg.onDestroy();
                ComponentContainer componentContainer = findContainerById(fg.mId);
                if (componentContainer != null) {
                    componentContainer.removeComponent(fg.root);
                }
                fg.visibleChange(false);
                fg.Clear();
                fragments.remove(fg);
                break;
            }
        }
    }

    void removeAllFragment() {
        synchronized (this) {
            for (Fragment fg : fragments) {
                fg.onDestroy();
                ComponentContainer componentContainer = findContainerById(fg.mId);
                if (componentContainer != null) {
                    componentContainer.removeComponent(fg.root);
                }
                fg.visibleChange(false);
                fg.Clear();
            }
            fragments.clear();
        }
    }

    public Fragment findFragmentById(int id) {
        for (int i = fragments.size() - 1; i > -1; i--) {
            if (fragments.get(i).mId == id) {
                return fragments.get(i);
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String tag) {
        for (int i = fragments.size() - 1; i > -1; i--) {
            if (TextUtils.equals(fragments.get(i).mTag, tag)) {
                return fragments.get(i);
            }
        }
        return null;
    }

    public boolean findFragment(Fragment fragment) {
        for (int i = fragments.size() - 1; i > -1; i--) {
            if (fragment == fragments.get(i)) {
                return true;
            }
        }
        return false;
    }
}
