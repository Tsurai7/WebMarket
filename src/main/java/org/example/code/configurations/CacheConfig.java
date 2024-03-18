package org.example.code.configurations;

import org.example.code.utilities.CustomCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Bean
    public CustomCache customCache() {
        return new CustomCache();
    }
}