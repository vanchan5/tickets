package com.track.web.api.manage.permission;


import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.manage.permission.save.SavePermissionDto;
import com.track.data.vo.permission.permission.GetAllPermissionsVo;
import com.track.data.vo.permission.permission.GetUserMenuListVo;
import com.track.permission.ISysPermissionService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Api(tags = "后台_系统管理_权限管理")
public class SysPermissionApi extends BaseWeb {

    @Autowired
    private ISysPermissionService service;

    /**
     * @Author chauncy
     * @Date 2019-10-28 23:29
     * @Description //保存权限
     *
     * @Update chauncy
     *
     * @param  savePermissionDto
     * @return com.track.core.interaction.JsonViewData
     **/
    @PostMapping("/savePermission")
    @ApiOperation("保存权限")
    public JsonViewData savePermission(@RequestBody @ApiParam(required = true,value = "savePermissionDto",name = "保存菜单权限")
                                       @Validated SavePermissionDto savePermissionDto){

        service.savePermission(savePermissionDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 10:41
     * @Description //获取全部权限列表
     *
     * @Update chauncy
     *
     * @param
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<getAllPermissionsVo>>
     **/
    @GetMapping("/getAllPermissions")
    @ApiOperation("获取全部权限列表(树形结构)")
    public JsonViewData<List<GetAllPermissionsVo>> getAllPermissions(){

        return setJsonViewData(service.getAllPermissions());
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 12:06
     * @Description //获取用户页面菜单数据
     *
     * @Update chauncy
     *
     * @param
     * @return com.track.core.interaction.JsonViewData<java.util.List<GetUserMenuListVo>>
     **/
    @GetMapping("/getUserMenuList")
    @ApiOperation("获取用户页面菜单数据")
    public JsonViewData<List<GetUserMenuListVo>> getUserMenuList(){

        return setJsonViewData(service.getUserMenuList());
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 21:29
     * @Description //批量删除菜单权限
     *
     * @Update chauncy
     *
     * @param  ids
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/delByIds/{ids}")
    @ApiOperation("批量删除菜单权限")
    public JsonViewData delByIds(@PathVariable List<Long> ids){

        service.delByIds(ids);
        return setJsonViewData(ResultCode.SUCCESS,"删除成功");
    }
}
