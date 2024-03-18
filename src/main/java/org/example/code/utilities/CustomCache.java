package org.example.code.utilities;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomCache {
    private final Map<String, Object> cacheMap = new HashMap<>();

    public void addToCache(String key, Object value) {
        cacheMap.put(key, value);
    }

    public void removeFromCache(String key) {
        cacheMap.remove(key);
    }

    public Object getFromCache(String key) {
        return cacheMap.get(key);
    }

    public boolean containsKey(String key) {
        return cacheMap.containsKey(key);
    }
}
