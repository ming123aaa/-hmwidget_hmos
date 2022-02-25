package com.ohuang.hmwidget.fragment.pageSlider;


import com.ohuang.hmwidget.fragment.Fragment;
import com.ohuang.hmwidget.fragment.FragmentManager;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PageSliderProvider;

public abstract class FragmentPagerProvider extends PageSliderProvider {

    protected FragmentManager fragmentManager;

    public FragmentPagerProvider(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public abstract Fragment getFragment(int i);

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        Fragment fg = getFragment(i);
        fragmentManager.beginTransaction().attach(fg).commitNow();
        Component component = fg.getComponent();
        componentContainer.addComponent(component);

        return component;
    }


    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent((Component) o);
        destroyComponent(componentContainer, i, o);
    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        return component == o;
    }

    protected void destroyComponent(ComponentContainer componentContainer, int i, Object o) {
        fragmentManager.beginTransaction().remove(getFragment(i)).commit();
    }

}
