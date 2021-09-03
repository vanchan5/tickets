package com.track.login.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author cheng
 * @create 2021/9/3 10:13
 *
 * Json登录
 *
 * 继承默认的登录方案，用户密码的提取在UsernamePasswordAuthenticationFilter
 * 过滤器中的attemptAuthentication（）,需要重写这个方法改变登录方式并且必须生成authentication证书
 *
 * 这一步完成会报Error creating bean with name 'myUsernamePasswordAuthenticationFilter'
 * IllegalArgumentException: authenticationManager must be specified
 *
 * AuthenticationManager需要被指定：思路是
 *
 * 1:自定义实现AuthenticationManager
 * 2:重用WebSecurityConfigurerAdapter配置的AuthenticationManager
 *
 */
@Slf4j
public class MyUsernamePasswordAuthenticationFilters extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //判断是否是post请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported只支持Post请求: " + request.getMethod());
        }

        //判断是否是json方式登录，使用security的HTTP规范中定义的参数常量
        //使用输入流获取json数据并使用fastJson的ObjectMapper(快)
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            UsernamePasswordAuthenticationToken authentication = null;
            authentication = new UsernamePasswordAuthenticationToken("","");
            setDetails(request,authentication);
            return this.getAuthenticationManager().authenticate(authentication);
        }
        //get请求，直接使用默认接口实现类
        else {
            return super.attemptAuthentication(request, response);
        }
    }

}
