package com.pots.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 指定されたフロントエンドのドメインを開発環境でのみ使用(「*」を使用せず。そうしないと、allowCredentials と競合します)。
        config.setAllowedOriginPatterns(List.of("http://localhost:3030", "http://localhost:5173","http://127.0.0.1:5173"));
        
        config.addAllowedMethod("*"); // 允许所有请求方法
        config.addAllowedHeader("*"); // 允许所有请求头
        config.setAllowCredentials(true); // 允许携带 Cookie 和认证信息
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
