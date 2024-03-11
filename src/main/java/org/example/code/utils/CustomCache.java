package org.example.code.utils;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomCache {
    private final Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    public void addToCache(String key, Object value) {
        cacheMap.put(key, value);
    }

    public Object getFromCache(String key) {
        return cacheMap.get(key);
    }

    public boolean containsKey(String key) {
        return cacheMap.containsKey(key);
    }
}
