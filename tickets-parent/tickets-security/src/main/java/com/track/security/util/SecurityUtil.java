package com.track.security.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.core.base.service.Service;
import com.track.data.domain.po.user.UmUserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-17 13:39
 *
 * 安全认证工具类
 *
 * 获取用户、角色、权限相关信息
 *
 */
@Component
@Slf4j
public class SecurityUtil {

    @Autowired
    private Service<UmUserPo> service;

    /**
     * @Author chauncy
     * @Date 2019-10-17 13:48
     * @Description //获取当前系统用户
     *
     * @Update chauncy
     *
     * @param
     * @return com.track.data.domain.po.user.UmUserPo
     **/
    public UmUserPo getSysCurrUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getOne(new QueryWrapper<UmUserPo>().lambda().eq(UmUserPo::getUsername,userDetails.getUsername()));
    }

    /**
     * @Author chauncy
     * @Date 2019-10-18 10:58
     * @Description //通过用户名获取用户拥有权限
     *
     * @Update chauncy
     *
     * @param  username
     * @return java.util.List<org.springframework.security.core.GrantedAuthority>
     **/
    public List<GrantedAuthority> getSysCurrUserPerms(String username){

        List<GrantedAuthority> authorities = new ArrayList<>();

        //处理根据用户账号获取权限操作

        return authorities;
    }
}
