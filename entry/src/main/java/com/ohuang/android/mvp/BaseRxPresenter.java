package com.ohuang.android.mvp;


import com.ohuang.android.rxHarmoney.HmSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseRxPresenter<V extends BaseView> extends BasePresenter<V> {

    protected CompositeDisposable compositeDisposable;

    /**
     * 解析实体类
     *
     * @param observable
     * @param observer
     */
    public <T> void addDisposable(Observable<T> observable, BaseObserver<T> observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(HmSchedulers.mainThread())
                .subscribeWith(observer));
    }

    /**
     * 解析实体类
     *
     * @param observable
     * @param observer
     */
    public <T> void addDisposable(Observable<T> observable, DisposableObserver<T> observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(HmSchedulers.mainThread())
                .subscribeWith(observer));
    }

    @Override
    public void detachView() {
        super.detachView();
        removeDisposable();
    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
