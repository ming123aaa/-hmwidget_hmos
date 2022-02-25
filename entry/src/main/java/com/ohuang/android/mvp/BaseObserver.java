package com.ohuang.android.mvp;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<T> {


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    
}
