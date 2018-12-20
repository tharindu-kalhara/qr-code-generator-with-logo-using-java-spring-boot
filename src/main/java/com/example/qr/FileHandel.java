package com.example.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author kalhara
 * @version 1.0
 * @since 2018-12-21
 */
@Component
public class FileHandel implements WebMvcConfigurer {

    @Autowired
    private DeleteImageInterceptor deleteImageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deleteImageInterceptor).addPathPatterns("/qr/login/dynamic.png");
    }
}
