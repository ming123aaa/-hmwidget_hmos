package com.ohuang.hmwidget.livedata;


import ohos.aafwk.ability.ILifecycle;
import ohos.aafwk.ability.Lifecycle;
import ohos.aafwk.ability.LifecycleObserver;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class LiveData<T> {
    volatile T data;
    Set<Observer<T>> set = new HashSet<>();
    HashMap<Observer<T>, LiveDataLifecycleObserver> map = new HashMap<>();
    volatile long version = 0;
    volatile EventHandler eventHandler;
    final Object object=new Object();


    public void setData(T t) {
        if(EventRunner.current()==null||EventRunner.current()!=EventRunner.getMainEventRunner()){
           throw new RuntimeException(getClass().getName()+" setData() method must running main Thread");
        }
        synchronized (object) {
            data = t;
            version++;
            for (Observer<T> o : set) {
                o.onchange(t);
            }
            map.forEach(new BiConsumer<Observer<T>, LiveDataLifecycleObserver>() {
                @Override
                public void accept(Observer<T> observer, LiveDataLifecycleObserver liveDataLifecycleObserver) {
                    liveDataLifecycleObserver.dispatchObserver();
                }
            });
        }
    }

    public void postData(T t){
        if(EventRunner.current()!=null&&EventRunner.current()==EventRunner.getMainEventRunner()){
           setData(t);
        }else {
            if (eventHandler==null){
                synchronized (object) {
                    if (eventHandler==null) {
                        eventHandler = new EventHandler(EventRunner.getMainEventRunner());
                    }
                }
            }
            eventHandler.postTask(new Runnable() {
                @Override
                public void run() {
                    setData(t);
                }
            });
        }
    }

    public T getData() {
        return data;
    }

    public void addObserver(Observer<T> observer) {
        set.add(observer);
    }

    public void addObserver(ILifecycle iLifecycle, Observer<T> observer) {

        if (!map.containsKey(observer)) {
            Lifecycle lifecycle = iLifecycle.getLifecycle();
            if (lifecycle==null){
                throw new RuntimeException("lifecycle is Null");
            }
            LiveDataLifecycleObserver liveDataLifecycleObserver = new LiveDataLifecycleObserver(lifecycle, observer);
            lifecycle.addObserver(liveDataLifecycleObserver);
            map.put(observer, liveDataLifecycleObserver);
        } else {
            throw new RuntimeException("observer  already Add in LiveData<" + data.getClass().getName() + ">");
        }
    }

    public void addObserverForSticky(Observer<T> observer) {
        set.add(observer);
        observer.onchange(data);
    }

    public void addObserverForSticky(ILifecycle iLifecycle, Observer<T> observer) {
        if (!map.containsKey(observer)) {
            Lifecycle lifecycle = iLifecycle.getLifecycle();
            if (lifecycle==null){
                throw new RuntimeException("lifecycle is Null");
            }
            LiveDataLifecycleObserver liveDataLifecycleObserver = new LiveDataLifecycleObserver(lifecycle, observer, true);
            lifecycle.addObserver(liveDataLifecycleObserver);
            liveDataLifecycleObserver.dispatchObserver();
            map.put(observer, liveDataLifecycleObserver);
        } else {
            throw new RuntimeException("observer  already Add in LiveData<" + data.getClass().getName() + ">");
        }
    }

    public void removeObserver(Observer<T> observer) {
        synchronized (object) {
            set.remove(observer);
            LiveDataLifecycleObserver liveDataLifecycleObserver = map.remove(observer);
            if (liveDataLifecycleObserver != null) {
                liveDataLifecycleObserver.remove();
            }
        }
    }

    public void removeAllObserver() {
        synchronized (object) {
            set.clear();
            map.forEach(new BiConsumer<Observer<T>, LiveDataLifecycleObserver>() {
                @Override
                public void accept(Observer<T> observer, LiveDataLifecycleObserver liveDataLifecycleObserver) {
                    map.remove(observer);
                    liveDataLifecycleObserver.remove();
                }
            });
            map.clear();
        }
    }


    private class LiveDataLifecycleObserver extends LifecycleObserver {
        long l_version = 0;
        Lifecycle lifecycle;
        Observer<T> observer;
        volatile boolean isLive = false;

        public LiveDataLifecycleObserver(Lifecycle lifecycle, Observer<T> o) {
            this(lifecycle, o, false);
        }

        public LiveDataLifecycleObserver(Lifecycle lifecycle, Observer<T> o, boolean sticky) {
            if (sticky) {
                l_version = version - 1;
            } else {
                l_version = version;
            }
            this.lifecycle = lifecycle;
            this.observer = o;
            if (lifecycle != null) {
                isLive = lifecycle.getLifecycleState().equals(Lifecycle.Event.ON_ACTIVE);
            }
        }


        @Override
        public void onActive() {
            super.onActive();
            isLive = true;
            dispatchObserver();
        }

        public void dispatchObserver() {
            if (isLive) {
                if (version > l_version) {
                    if (observer != null) {
                        observer.onchange(data);
                    }
                    l_version = version;
                }
            }
        }

        @Override
        public void onBackground() {
            super.onBackground();
            isLive = false;
        }
        @Override
        public void onStop() {
            super.onStop();
            isLive = false;
            if (observer!=null) {
                map.remove(observer);
            }
            remove();
        }

        public void remove() {
            if (lifecycle != null) {
                lifecycle.removeObserver(this);
            }
            observer=null;
            lifecycle = null;
        }
    }

}
