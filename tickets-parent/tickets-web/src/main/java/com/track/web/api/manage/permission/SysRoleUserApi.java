package com.track.web.api.manage.permission;


import com.track.permission.ISysRoleUserService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户与角色关系表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@RestController
@RequestMapping("sys-role-user-po")
@Api(tags = "用户与角色关系表")
public class SysRoleUserApi extends BaseWeb {

    @Autowired
    private ISysRoleUserService service;


}
