package com.ohuang.android.mvp;

import ohos.aafwk.ability.AbilitySlice;

public class BaseMvpSlice<T extends BasePresenter> extends AbilitySlice {
    protected T mPresenter;

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
