package com.pots.common.config;

import com.pots.admin.interceptor.AdminTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private final AdminTokenInterceptor adminTokenInterceptor;
    
    public WebConfig(AdminTokenInterceptor adminTokenInterceptor) {
        this.adminTokenInterceptor = adminTokenInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                // インターセプターの指定
                .addInterceptor(adminTokenInterceptor)
                // 処理対象のリクエスト設定
                .addPathPatterns("/api/admin/**")
                // 処理しないリクエスト設定
                .excludePathPatterns("/api/admin/login");
    }
    
}
