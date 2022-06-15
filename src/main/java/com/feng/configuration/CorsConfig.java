package com.feng.configuration;

import com.feng.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fhn
 * @create 2021/7/31
 * @software idea
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 匹配任何路径
                .allowedOriginPatterns("*")  // 允许跨域访问的源
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS") //  允许访问的方法
                .maxAge(16800)  // 预检间隔时间
                .allowedHeaders("*")  // 允许头部设置
                .allowCredentials(true);  // 是否发送cookie
    }

    @Bean
    public MyInterceptor interceptor() {
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
    }
}
