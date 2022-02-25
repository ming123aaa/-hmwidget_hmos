package com.ohuang.hmwidget.fragment;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.ILifecycle;
import ohos.aafwk.ability.Lifecycle;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;

public abstract class Fragment implements ILifecycle {
    private FragmentManager fragmentManager;
    private FragmentManager childFragmentManager;
    boolean isAdd = false;
    boolean isVisible = false;
    boolean userVisible = false;
    Component root;
    String mTag;
    int mId;

    Lifecycle lifecycle;


    void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.childFragmentManager = new FragmentManager(fragmentManager, this);
        lifecycle = new Lifecycle();

    }

    protected void onAttach(Context context) {

    }

    void CreateView(LayoutScatter scatter, ComponentContainer container) {
        root = onCreateComponent(scatter, container);
    }

    protected abstract Component onCreateComponent(LayoutScatter scatter, ComponentContainer container);


    protected void onDestroyComponent() {

    }


    public <T extends Component> T findComponentById(int id) {
        if (root != null) {
            return (T) root.findComponentById(id);
        }
        return null;
    }

    /**
     * Component  可见性改变   调用ohShow  onHide add remove时调用
     *
     * @param visible
     */
    void visibleChange(boolean visible) {
        isVisible = visible;
        if (visible) {
            onShow();
        } else {
            onHide();
        }
        setUserVisibleChange(visible);

    }

    protected void onShow() {

    }

    protected void onHide() {

    }

    /**
     * 用户可见性改变
     * 并向 子fragment分发
     * 用于设置用户可见性
     * @param isVisible
     */
    public final void setUserVisibleChange(boolean isVisible) {
        userVisible = isVisible;
        dispatchUserVisible(isVisible);
    }

    /**
     * 分发 用户可见改变
     * @param isVisible
     */
    public final void  dispatchUserVisible(boolean isVisible){
        userVisibleChange(isVisible);
        childFragmentManager.fragmentLifecycleDispatcher.dispatchUserVisible(isVisible);
    }

    /**
     * 重写改方法 监听用户可见性变化
     * 父亲可见性变化会调用该方法  而不会调用setUserVisibleChange
     * @param isVisible
     */
    protected void userVisibleChange(boolean isVisible) {

    }

    /**
     * 获取用户可见性  用户可见性可以由setUserVisibleChange方法设置
     * @return
     */
    public boolean getUserVisible(){
        return userVisible;
    }

    /**
     * 获取视图可见性
     *
     * @return
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 是否已添加到FragmentManager
     *
     * @return
     */
    public boolean isAdd() {
        return isAdd;
    }


    public Ability getAbility() {
        return fragmentManager.getAbility();
    }

    public Context getContext() {
        return fragmentManager.getContext();
    }

    /**
     * 获取父亲fragment  可能为空
     *
     * @return
     */
    public Fragment getParentFragment() {
        return fragmentManager.getParentFragment();
    }

    public FragmentManager getParentFragmentManager() {
        return fragmentManager;
    }


    public FragmentManager getChildFragmentManager() {
        return childFragmentManager;
    }

    public Component getComponent() {
        return root;
    }

    void onCreate() {
        initComponent();
        lifecycle.dispatchLifecycle(getAbility().getLifecycle().getLifecycleState(), null);
    }

    protected abstract void initComponent();

    void onFragmentActive() {
        onActive();
        lifecycle.dispatchLifecycle(Lifecycle.Event.ON_ACTIVE, null);
        getChildFragmentManager().getFragmentLifecycleDispatcher().dispatchOnActive();
    }

    protected void onActive() {
    }

    void onFragmentInactive() {
        onInactive();
        lifecycle.dispatchLifecycle(Lifecycle.Event.ON_INACTIVE, null);
        getChildFragmentManager().getFragmentLifecycleDispatcher().dispatchOnInactive();
    }

    protected void onInactive() {
    }

    void onFragmentBackground() {
        onBackground();
        lifecycle.dispatchLifecycle(Lifecycle.Event.ON_BACKGROUND, null);
        getChildFragmentManager().getFragmentLifecycleDispatcher().dispatchOnBackground();
    }

    protected void onBackground() {
    }

    void onFragmentForeground() {
        onForeground();
        lifecycle.dispatchLifecycle(Lifecycle.Event.ON_FOREGROUND, null);
        getChildFragmentManager().getFragmentLifecycleDispatcher().dispatchOnForeground();
    }

    protected void onForeground() {

    }


    protected void onStop() {

    }

    void onDestroy() {
        onStop();
        lifecycle.dispatchLifecycle(Lifecycle.Event.ON_STOP, null);
        getChildFragmentManager().removeAllFragment();
    }

    public void finish() {
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }

    void Clear() {
        onDestroyComponent();
        fragmentManager = null;
        childFragmentManager = null;
        root = null;
        mTag = null;
        isAdd = false;
        isVisible = false;
        lifecycle = null;

    }

    public Lifecycle getLifecycle() {
        return lifecycle;
    }

}
