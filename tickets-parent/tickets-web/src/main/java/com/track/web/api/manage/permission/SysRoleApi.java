package com.track.web.api.manage.permission;


import com.track.permission.ISysRoleService;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@RestController
@RequestMapping("/manage/role")
@Api(tags = "后台_系统管理_角色管理")
public class SysRoleApi extends BaseWeb {

    @Autowired
    private ISysRoleService service;

//    public void save(@RequestBody @ApiParam())

}
