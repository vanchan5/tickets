package com.track.web.api.manage.permission;


import com.track.permission.ISysRolePermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 角色权限关系表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@RestController
@RequestMapping("sys-role-permission-po")
@Api(tags = "角色权限关系表")
public class SysRolePermissionApi extends BaseWeb {

    @Autowired
    private ISysRolePermissionService service;


}
