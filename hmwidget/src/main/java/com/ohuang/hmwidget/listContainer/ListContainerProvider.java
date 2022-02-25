package com.ohuang.hmwidget.listContainer;

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;

/**
 * 请使用getViewType() 代替了 getItemComponentType()方法
 * 若不想代替请使用 ListContainerProvider2
 */
public abstract class ListContainerProvider extends BaseItemProvider {


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public final Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        Component cpt = null;
        ViewHolder viewHolder = null;
        if (component != null) {
            TypeHolder typeHolder = (TypeHolder) component.getTag();
            if (typeHolder.viewTpe == getViewType(i)) {
                cpt = component;
                viewHolder=typeHolder.viewHolder;
            }
        }
        if (cpt == null) {
            viewHolder = onCreateViewHolder(componentContainer, getViewType(i), i);
            if (viewHolder != null) {
                cpt = viewHolder.itemComponent;
                if (cpt != null) {
                    TypeHolder typeHolder = new TypeHolder(getViewType(i), viewHolder);
                    cpt.setTag(typeHolder);
                }
            }
        }
        onBindViewHolder(viewHolder, i);
        return cpt;
    }


    public abstract ViewHolder onCreateViewHolder(ComponentContainer parent, int viewType, int position);


    public abstract void onBindViewHolder(ViewHolder holder, int position);

    protected int getViewType(int position) {
        return 0;
    }


    public static class ViewHolder {
        Component itemComponent;

        public ViewHolder(Component component) {
            if (component == null) {
                throw new IllegalArgumentException("itemComponent may not be null");
            }
            this.itemComponent = component;
        }
    }


    static class TypeHolder {
        ViewHolder viewHolder;
        int viewTpe;

        public TypeHolder(int viewTpe, ViewHolder v) {
            this.viewHolder = v;
            this.viewTpe = viewTpe;
        }
    }
}
