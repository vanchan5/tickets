package com.track.security.details.authentication;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cheng
 * @create 2019-10-17 13:07
 *
 * 该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充，
 * 接口AuthenticationDetailsSource的
 * 默认实现是WebAuthenticationDetailsSource，生成默认的WebAuthenticationDetails实现类，而
 *
 *
 * 覆盖默认方法如下:
 *
 * 自定义类MyWebAuthenticationDetailsSource来实现AuthenticationDetailsSource，
 * 用于生成自定义的MyWebAuthenticationDetails。
 *
 */

@Component
public class MyWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MyWebAuthenticationDetails(context);
    }
}
