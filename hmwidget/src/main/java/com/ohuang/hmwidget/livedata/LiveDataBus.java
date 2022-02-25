package com.ohuang.hmwidget.livedata;

import java.util.HashMap;
import java.util.Map;

public class LiveDataBus {
    private final Map<String, LiveData<Object>> map;

    private LiveDataBus() {
        map = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public <T> LiveData<T> with(String key, Class<T> type) {
        if (!map.containsKey(key)) {
            map.put(key, new LiveData<>());
        }
        return (LiveData<T>) map.get(key);
    }

    public LiveData<Object> with(String key) {
        return with(key, Object.class);
    }

    public LiveData<Object> removeKey(String key) {
        return map.remove(key);
    }


}
