package com.track.data.bo.user.permission;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-19 13:36
 *
 * 继承系统用户实体类，用于给实现了UserDetails接口的SecurityUserDetails
 * 有参构造函数传参
 */
@Data
@Accessors(chain = true)
public class UserInfoBo extends UmUserBo{

    //用户拥有的角色
    private List<RoleBo> roles;

    //用户拥有的权限
    private List<PermissionBo> permissions;
}
