package com.track.data.bo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-17 21:34
 *
 * 以token为key,UserTokenBo为value保存到redis
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class TokenUserBo implements Serializable {

    private String username;

    //加密后的password，若需要明文再改
    private String password;

    private List<String> permissions;

    private Boolean saveLogin;

    //登录方式，拦截请求的时候需要用到查找用户信息
    private String loginType;
}
