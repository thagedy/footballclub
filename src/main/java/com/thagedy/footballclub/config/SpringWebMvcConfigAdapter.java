package com.thagedy.footballclub.config;

import com.thagedy.footballclub.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Tianqi Cui on 2016/9/9.
 */
@Configuration
public class SpringWebMvcConfigAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/wx/**")
                .excludePathPatterns("/aj/login");
    }
}
