package com.ohuang.android.rxHarmoney;

import io.reactivex.rxjava3.core.Scheduler;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

public class HmSchedulers {
    private static final class MainHolder {
        static final Scheduler DEFAULT
                = new EventHandlerScheduler(new EventHandler(EventRunner.getMainEventRunner()));
    }

    public static Scheduler mainThread(){
        return MainHolder.DEFAULT;
    }

}
