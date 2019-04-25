package com.tang.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.io.File;


@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    //跨域处理器
    @Bean
    public CrosFilter crosFilter(){
        return CrosFilter.getInstance();
    }

    //登录处理
    //@Bean
    //public AuthorizedInterceptor authorizedInterceptor() {
    //    return AuthorizedInterceptor.getInstance();
    //}
    @Bean
    //统一异常处理器
    public SimpleMappingExceptionResolver smer(){
        return new SimpleMappingExceptionResolver();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用于排除拦截路径
        registry.addInterceptor(crosFilter()).addPathPatterns("/**").excludePathPatterns("/image/**")
        .excludePathPatterns("/static/**").excludePathPatterns("/login").excludePathPatterns("/logout")
        //registry.addInterceptor(authorizedInterceptor()).addPathPatterns("/**").excludePathPatterns("/test/*")
        .excludePathPatterns("/error");
        //.excludePathPatterns("/login")
        //.excludePathPatterns("/login2");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

    //文件磁盘图片url 映射
    //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/image/**").addResourceLocations("file:///F:\\image\\");
        super.addResourceHandlers(registry);

        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
