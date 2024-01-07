package com.example.java_spring_boot.config;

import com.example.java_spring_boot.filter.LogProcessTimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean logProcessTimeFilter() {
        FilterRegistrationBean<LogProcessTimeFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogProcessTimeFilter());
        bean.addUrlPatterns("/*");
        bean.setName("logProcessTimeFilter");

        return bean;
    }
}
