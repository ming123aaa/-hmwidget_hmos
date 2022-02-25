package com.ohuang.android.mvp;


import com.ohuang.android.base.BaseFragment;
import com.ohuang.hmwidget.fragment.Fragment;

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment {
    protected T mPresenter;

    @Override
    protected void onDestroyComponent() {
        super.onDestroyComponent();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
