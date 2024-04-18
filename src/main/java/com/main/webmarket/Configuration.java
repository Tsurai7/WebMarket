package com.main.webmarket;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.context.annotation.Configuration
public class Configuration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Путь, для которого применяется CORS
                .allowedOrigins("*") // Разрешить доступ со всех источников
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Разрешенные HTTP методы
    }
}
