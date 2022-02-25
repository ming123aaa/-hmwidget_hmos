package com.ohuang.hmwidget.navigation;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.app.Context;

import java.util.ArrayList;
import java.util.List;

public class NavigationView extends ComponentContainer implements Component.ClickedListener, ComponentContainer.ArrangeListener {
    private Context context;
    private List<NavigationTab> tabList;
    private OnClickNavigationListener onClickNavigationListener;
    private OnSelectListener onSelectListener;
    private onUnselectListener onUnselectListener;
    private int selectPosition = 0;

    public NavigationView(Context context) {
        super(context);
        initView(context);
    }

    public NavigationView(Context context, AttrSet attrSet) {
        super(context, attrSet);
        initView(context);
    }

    public NavigationView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        setArrangeListener(this);
    }


    public List<NavigationTab> getTabList() {
        return tabList;
    }

    /**
     * 添加List<NavigationTab>   NavigationTab使用NavigationBuilder创建
     *
     * @param tabList
     */
    public void setTabList(List<NavigationTab> tabList) {
        this.tabList = tabList;
        removeAllComponents();
        for (NavigationTab navigationTab : tabList) {
            NavigationButton navigationButton = navigationTab.create(context);
            navigationButton.setClickedListener(this);
            addComponent(navigationButton);
        }
        selectButton(selectPosition);
    }

    /**
     * 添加NavigationTab   NavigationTab使用NavigationBuilder创建
     *
     * @param navigationTab
     * @return
     */
    public NavigationView addTab(NavigationTab navigationTab) {
        if (tabList == null) {
            tabList = new ArrayList<>();
        }
        if (navigationTab != null) {
            tabList.add(navigationTab);
            NavigationButton navigationButton = navigationTab.create(context);
            navigationButton.setClickedListener(this);
            addComponent(navigationButton);
            if (selectPosition == tabList.size() - 1) {
                selectButton(selectPosition);
            }
        }
        return this;
    }


    public int getSelectPosition() {
        return selectPosition;
    }

    /**
     * 设置选中的按钮
     *
     * @param p
     */
    public void setSelect(int p) {
        cancelSelect(selectPosition);
        this.selectPosition = p;
        selectButton(selectPosition);
    }

    public OnClickNavigationListener getOnClickNavigationListener() {
        return onClickNavigationListener;
    }

    /**
     * 按钮点击监听事件
     *
     * @param onClickNavigationListener
     */
    public void setOnClickNavigationListener(OnClickNavigationListener onClickNavigationListener) {
        this.onClickNavigationListener = onClickNavigationListener;
    }

    public NavigationView.onUnselectListener getOnUnselectListener() {
        return onUnselectListener;
    }

    public void setOnUnselectListener(NavigationView.onUnselectListener onUnselectListener) {
        this.onUnselectListener = onUnselectListener;
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    /**
     * 选中监听事件
     *
     * @param onSelectListener
     * return false 拦截可选中事件
     */
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public NavigationBuilder getNavigationBuilder() {
        return new NavigationBuilder();
    }

    @Override
    public void onClick(Component component) {
        for (int i = 0; i < getChildCount(); i++) {
            if (component == getComponentAt(i)) {
                if (onClickNavigationListener != null) {
                    onClickNavigationListener.onClick(this, i);
                }
                if (selectPosition != i) {
                    if (onSelectListener == null || !onSelectListener.onSelect(this, i)){
                        cancelSelect(selectPosition);
                        if (onUnselectListener!=null){
                            onUnselectListener.onUnselect(this,selectPosition);
                        }
                        selectButton(i);
                        selectPosition = i;
                    }
                }
                break;
            }
        }
    }




    private void cancelSelect(int position) {
        if (tabList == null) {
            return;
        }
        if (position > -1 && position < tabList.size()) {
            NavigationButton navigationButton = (NavigationButton) getComponentAt(position);
            navigationButton.setTextColor(tabList.get(position).getTextColor());
            navigationButton.setImage(tabList.get(position).getDefaultDrawableId());
        }
    }

    private void selectButton(int position) {
        if (tabList == null) {
            return;
        }
        if (position > -1 && position < tabList.size()) {
            NavigationButton navigationButton = (NavigationButton) getComponentAt(position);
            navigationButton.setTextColor(tabList.get(position).getSelectTextColor());
            navigationButton.setImage(tabList.get(position).getSelectDrawableId());
        }
    }

    @Override
    public boolean onArrange(int i, int i1, int i2, int i3) {
        if (tabList != null) {
            int with = i2 ;
            int height = i3 ;
            int count = tabList.size();
            for (int j = 0; j < count; j++) {
                NavigationButton navigationButton = (NavigationButton) getComponentAt(j);
                navigationButton.arrange((int) (j * (1.0f * with / count)), 0, with/count, height);
            }
            return true;
        }
        return false;
    }

    public interface OnClickNavigationListener {
        void onClick(NavigationView navigationView, int index);
    }

    public interface OnSelectListener {
        /**
         *按钮选中事件
         * @param navigationView
         * @param index  选中按钮的位置
         * @return  true 可拦截选中事件
         */
        boolean onSelect(NavigationView navigationView, int index);
    }

    public interface onUnselectListener{
         void onUnselect(NavigationView navigationView, int index);
    }
}
