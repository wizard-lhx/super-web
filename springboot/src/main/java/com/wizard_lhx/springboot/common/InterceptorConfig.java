package com.wizard_lhx.springboot.common;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/18 11:22
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())  // 配置 jwt 拦截规则
                .addPathPatterns("/**")            // 拦截所有请求路径
                .excludePathPatterns("/login");
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}


