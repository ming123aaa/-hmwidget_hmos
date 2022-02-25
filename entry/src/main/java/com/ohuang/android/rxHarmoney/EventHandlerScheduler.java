package com.ohuang.android.rxHarmoney;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import ohos.eventhandler.EventHandler;

import java.util.concurrent.TimeUnit;

public class EventHandlerScheduler extends Scheduler {
   private EventHandler eventHandler;

    public EventHandlerScheduler(EventHandler eventHandler) {
        this.eventHandler=eventHandler;
    }

    @Override
    public @NonNull Worker createWorker() {
        return new HmWork(eventHandler);
    }



    class HmWork extends Worker{
        private EventHandler eventHandler;
        private volatile boolean disposed;

        public HmWork(EventHandler eventHandler) {
            this.eventHandler = eventHandler;
        }



        @Override
        public @NonNull Disposable schedule(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
            if (run == null) throw new NullPointerException("run == null");
            if (unit == null) throw new NullPointerException("unit == null");
            if (disposed) {
                return Disposable.disposed();
            }
            ScheduledRunnable scheduledRunnable=new ScheduledRunnable(eventHandler,run);
            eventHandler.postTask(scheduledRunnable,unit.toMillis(delay));
            if (disposed) {
                return Disposable.disposed();
            }
            return scheduledRunnable;
        }

        @Override
        public void dispose() {
            disposed = true;
            eventHandler.removeAllEvent();

        }

        @Override
        public boolean isDisposed() {
            return false;
        }
    }

    private static final class ScheduledRunnable implements Runnable, Disposable {
        private final EventHandler handler;
        private final Runnable delegate;

        private volatile boolean disposed; // Tracked solely for isDisposed().

        ScheduledRunnable(EventHandler handler, Runnable delegate) {
            this.handler = handler;
            this.delegate = delegate;
        }

        @Override
        public void run() {
            try {
                delegate.run();
            } catch (Throwable t) {
                RxJavaPlugins.onError(t);
            }
        }

        @Override
        public void dispose() {
            handler.removeTask(this);
            disposed = true;
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }
}
