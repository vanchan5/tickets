package com.track.login.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.common.utils.ListUtil;
import com.track.data.bo.user.permission.PermissionBo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.mapper.permission.SysRolePermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class SecurityUtils {

    @Autowired
    private IBaseMapper<UmUserPo> userPoIBaseMapper;

    @Autowired
    private IBaseMapper<SysRolePermissionMapper> rolePermissionMapper;

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
        return userPoIBaseMapper.selectOne(new QueryWrapper<UmUserPo>().lambda().eq(UmUserPo::getUsername,userDetails.getUsername()));
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
        UmUserPo userPo = userPoIBaseMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                .eq(UmUserPo::getUsername,username));

        List<PermissionBo> permissions = rolePermissionMapper.findPermissionByUserId(userPo.getId());
        if (!ListUtil.isListNullAndEmpty(permissions)){
            for (PermissionBo permission: permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getTitle()));
            }
        }

        return authorities;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 09:42
     * @Description //根据用户名判断用户是否存在
     *
     * @Update chauncy
     *
     * @param  username
     * @return boolean
     **/
    public boolean userIsExit(String username){

        UmUserPo userPo = userPoIBaseMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                .eq(UmUserPo::getUsername,username));

        if (userPo == null){
            throw new DisabledException(String.format("该用户[%s]已被删除",username));
        }else if (!userPo.getEnabled()){
            throw new LockedException(String.format("账户[%s]被禁用，请联系管理员",username));
        }

        return userPo == null ? false : true;
    }
}
