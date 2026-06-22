package com.oracle.comventory.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${com.oracle.oBootPersonal01.upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(uploadPath)
                .toAbsolutePath()
                .normalize();

        registry.addResourceHandler("/upload/**")
                .addResourceLocations(uploadDir.toUri().toString());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/logout",
                        "/css/**",
                        "/js/**",
                        "/upload/**",
                        "/favicon.ico"
                );
        registry.addInterceptor(new ExternalUserAccessInterceptor())
		        .addPathPatterns("/**")
		        .excludePathPatterns(
		                "/login",
		                "/logout",
		                "/css/**",
		                "/js/**",
		                "/upload/**",
		                "/favicon.ico",
		                "/accessDenied"
		        );
        registry.addInterceptor(new HrAccessInterceptor())
		        .addPathPatterns(
		                "/emp/**",
		                "/dept/**"
		        );
        registry.addInterceptor(new InventoryAccessInterceptor())
		        .addPathPatterns(
		                "/inventory/currentStockView",
		                "/inventory/stockListView",
		                "/inventory/physicalInventoryView",
		                "/inventory/inventoryAdjustmentView"
		        );
    }
}
