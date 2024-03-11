package org.example.code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CacheConfig {
    @Bean
    public CustomCache customCache() {
        return new CustomCache();
    }

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
}
