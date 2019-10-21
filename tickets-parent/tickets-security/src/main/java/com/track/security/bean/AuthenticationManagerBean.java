package com.track.security.bean;

import lombok.Data;

/**
 * @Author cheng
 * @create 2019-10-20 23:27
 *
 * 重写认证过滤器是需要指定AuthenticationManager
 */
@Data
public class AuthenticationManagerBean {

    private String username;

    private String password;
}
