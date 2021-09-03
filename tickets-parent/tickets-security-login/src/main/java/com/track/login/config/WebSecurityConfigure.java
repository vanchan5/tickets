package com.track.login.config;

import com.track.login.authentication.MyAuthenticationFilter;
import com.track.login.token.AuthenticationFailHandlers;
import com.track.login.token.MyUsernamePasswordAuthenticationFilters;
import com.track.login.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cheng
 * @create 2021/9/3 10:13
 * 配置拦截器保护请求
 * Security 核心配置类
 * 开启控制权限至Controller
 *
 * 用户登录会经过UsernamePasswordAuthenticationFilter过滤器，该过滤器默认使用post请求以及form表单
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法权限控制
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Value("${tickets.token.redis}")
    private Boolean tokenRedis;

    @Value("${tickets.tokenExpireTime}")
    private Integer tokenExpireTime;

    @Value("${tickets.token.storePerms}")
    private Boolean storePerms;

    @Autowired
    private IgnoredUrlsProperty ignoredUrlsProperty;

    //依赖注入认证详情资源(获取附带增加额外的数据，如验证码、用户类型、手机号码)
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    //认证提供者
    @Autowired
    private AuthenticationProvider provider;

    //认证成功处理器
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    //认证失败处理器
    @Autowired
    private AuthenticationFailHandlers failHandler;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        //除配置文件忽略路径其它所有请求都需经过认证和授权
        for (String url : ignoredUrlsProperty.getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        //配置json登录,
        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(myUsernamePasswordAuthenticationFilters(), UsernamePasswordAuthenticationFilter.class);

        registry.and()
                //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
                //表单登录方式
                .formLogin()
                //增加手机号  验证码等字段
                .authenticationDetailsSource(authenticationDetailsSource)
                //请求时未登录跳转接口
                .loginPage("/security/needLogin")
                //登录请求url
                .loginProcessingUrl("/login")
                .permitAll()
                //成功处理类
                .successHandler(successHandler)
                //失败
                .failureHandler(failHandler)
                .and()
                //允许网页iframe
                .headers().frameOptions().disable()
                .and()
                .logout()//退出登录相关配置
//                    .logoutUrl("/signOut")  //自定义退出登录页面
                .logoutSuccessUrl("/security/logout") //退出成功后跳转的页面
                .permitAll()
                .and()
                .authorizeRequests()
                //任何请求
                .anyRequest()
                //需要身份认证
                .authenticated()
                .and()
                //关闭跨站请求防护
                .csrf().disable()
                //前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //添加网络请求过滤器 除已配置的其它请求都需经过此过滤器
                .addFilter(new MyAuthenticationFilter(authenticationManager(), tokenRedis, tokenExpireTime, storePerms,
                        redisTemplate, securityUtils));

    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    //认证(登录)过滤器，添加json+Post请求登录方式
    @Bean
    MyUsernamePasswordAuthenticationFilters myUsernamePasswordAuthenticationFilters() throws Exception{
        MyUsernamePasswordAuthenticationFilters filter = new MyUsernamePasswordAuthenticationFilters();
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failHandler);
        filter.setFilterProcessesUrl("/login/self");
        //自定义认证信息详情
        filter.setAuthenticationDetailsSource(authenticationDetailsSource);
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean()); //走了AuthenticationManager
        return filter;
    }
}
