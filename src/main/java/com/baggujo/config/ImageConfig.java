package com.baggujo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Value(value = "${com.baggujo.file.prefix}" + "${com.baggujo.upload.path}")
    private String realPath;
    @Value(value="${com.baggujo.upload.virtual}")
    private String virtualPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(virtualPath)
                .addResourceLocations(realPath + File.separator);
    }
}
