//package com.track.core.bean.interceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @Author cheng
// * @create 2019-11-20 13:38
// *
// * 自定义限流拦截器配置
// * 注册拦截器
// */
//@Configuration
//public class InterceptorConfiguration extends WebMvcConfigurationSupport {
//
//    @Autowired
//    private IgnoredUrlsPropery ignoredUrlsProperties;
//
//    @Autowired
//    private LimitRaterInterceptor limitRaterInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        // 注册拦截器
//        InterceptorRegistration ir = registry.addInterceptor(limitRaterInterceptor);
//        // 配置拦截的路径
//        ir.addPathPatterns("/**");
//        // 配置不拦截的路径
////        ir.excludePathPatterns(ignoredUrlsProperties.getUrls());
//    }
//}
