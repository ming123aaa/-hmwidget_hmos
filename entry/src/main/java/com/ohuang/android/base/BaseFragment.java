package com.ohuang.android.base;

import com.ohuang.hmwidget.fragment.Fragment;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;

public abstract class BaseFragment extends Fragment {
    @Override
    protected Component onCreateComponent(LayoutScatter scatter, ComponentContainer container) {

        return scatter.parse(getXmlId(),container,false);
    }
    public abstract int getXmlId();

}
