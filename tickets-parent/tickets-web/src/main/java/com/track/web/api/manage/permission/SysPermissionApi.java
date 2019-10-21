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
@RequestMapping("/manage/permission")
@Api(tags = "后台_系统管理_菜单权限管理")
public class SysPermissionApi extends BaseWeb {

    @Autowired
    private ISysPermissionService service;

}
