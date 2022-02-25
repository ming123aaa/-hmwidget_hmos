package com.ohuang.android.fragment.listfg;


import com.ohuang.android.bean.WYNewsBean;
import com.ohuang.android.mvp.BaseObserver;
import com.ohuang.android.mvp.BaseRxPresenter;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import org.jetbrains.annotations.NotNull;

public class ListFgPresenter extends BaseRxPresenter<IListFgView> {
    ListFgModel listFgModel = new ListFgModel();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(IListFgView view) {
        super.attachView(view);

    }

    public void LoadData(int page) {
        addDisposable(listFgModel.loadData(page + "", "10"), new BaseObserver<WYNewsBean>() {
            @Override
            public void onNext(@NotNull WYNewsBean wyNewsBean) {
                if (wyNewsBean.getCode() == 200) {
                    mView.LoadData(wyNewsBean);
                } else {
                    mView.ShowToast(wyNewsBean.getMessage());
                    mView.loadFail();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                super.onError(e);
                mView.ShowToast(e.getMessage());
                mView.loadFail();
            }
        });
    }


}
