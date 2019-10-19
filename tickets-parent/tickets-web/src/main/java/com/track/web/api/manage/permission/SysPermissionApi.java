package com.track.web.api.manage.permission;


import com.track.permission.ISysPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@RestController
@RequestMapping("sys-permission-po")
@Api(tags = "菜单权限表")
public class SysPermissionApi extends BaseWeb {

    @Autowired
    private ISysPermissionService service;

}
