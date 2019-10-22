package com.track.security.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.ListUtil;
import com.track.core.exception.ServiceException;
import com.track.data.bo.user.permission.PermissionBo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.mapper.permission.SysRolePermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SecurityUtil {

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
        if (userPo == null){
            throw new ServiceException(ResultCode.NO_EXISTS,"该用户已被删除");
        }else if (userPo.getStatus()==-1){
            throw new ServiceException(ResultCode.LOCKED,"账户被禁用，请联系管理员");
        }
        List<PermissionBo> permissions = rolePermissionMapper.findPermissionByUserId(userPo.getId());
        if (!ListUtil.isListNullAndEmpty(permissions)){
            for (PermissionBo permission: permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getTitle()));
            }
        }

        return authorities;
    }
}
