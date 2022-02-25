package com.ohuang.hmwidget.listContainer;

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;

public abstract class ListContainerProvider2 extends BaseItemProvider {


    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        Component cpt = null;
        ListContainerProvider.ViewHolder viewHolder = null;
        if (component != null) {
            cpt = component;
            viewHolder = (ListContainerProvider.ViewHolder) component.getTag();
        }

        if (cpt == null) {
            viewHolder = onCreateViewHolder(componentContainer, getItemComponentType(i), i);
            cpt = viewHolder.itemComponent;
            cpt.setTag(viewHolder);
        }

        onBindViewHolder(viewHolder, i);
        return cpt;
    }


    public abstract ListContainerProvider.ViewHolder onCreateViewHolder(ComponentContainer parent, int viewType, int position);


    public abstract void onBindViewHolder(ListContainerProvider.ViewHolder holder, int position);
}
