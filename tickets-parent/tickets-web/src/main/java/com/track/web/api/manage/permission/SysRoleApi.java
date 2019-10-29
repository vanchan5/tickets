package com.track.web.api.manage.permission;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.manage.permission.save.EditDefaultRoleDto;
import com.track.data.dto.manage.permission.save.SaveRoleDto;
import com.track.data.dto.manage.permission.save.SaveRolePermissionsDto;
import com.track.data.vo.permission.role.GetRolePermissionVo;
import com.track.data.dto.manage.permission.search.SearchRoleDto;
import com.track.data.vo.permission.role.SearchRoleVo;
import com.track.permission.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.track.web.base.BaseWeb;

import java.util.List;

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

    /**
     * @Author chauncy
     * @Date 2019-10-22 21:42
     * @Description //保存角色信息
     *
     * @Update chauncy
     *
     * @param  saveRoleDto
     * @return void
     **/
    @PostMapping("/saveRole")
    @ApiOperation("保存角色信息")
    public JsonViewData saveRole(@RequestBody @ApiParam(required = true,name = "saveRoleDto",value = "保存角色Dto")
                         @Validated SaveRoleDto saveRoleDto){

        service.saveRole(saveRoleDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功!");
    }

    /**
     * @Author chauncy
     * @Date 2019-10-23 14:02
     * @Description //分页查询角色信息
     *
     * @Update chauncy
     *
     * @param  searchRoleDto
     * @return com.track.core.interaction.JsonViewData<com.track.data.vo.permission.role.SearchRoleVo>
     **/
    @PostMapping("/searchRole")
    @ApiOperation("分页查询角色信息")
    public JsonViewData<PageInfo<SearchRoleVo>> searchRole(@RequestBody @ApiParam(required = true,name = "searchRoleVo",value = "分页查询角色信息")
                                                 @Validated SearchRoleDto searchRoleDto){

        return setJsonViewData(service.searchRole(searchRoleDto));

    }

    /**
     * @Author chauncy
     * @Date 2019-10-28 21:47
     * @Description //设置默认角色
     *
     * @Update chauncy
     *
     * @param  editDefaultRoleDto
     * @return com.track.core.interaction.JsonViewData
     **/
    @PostMapping("/setDefaultRole")
    @ApiOperation("设置默认角色")
    public JsonViewData setDefaultRole(@RequestBody @ApiParam(required = true,name = "setDefaultRoleDto",value = "设置默认角色")
                               @Validated EditDefaultRoleDto editDefaultRoleDto){

        service.setDefaultRole(editDefaultRoleDto);
        return setJsonViewData(ResultCode.SUCCESS,"设置默认角色成功");
    }

    /**
     * @Author chauncy
     * @Date 2019-10-29 12:24
     * @Description //添加/编辑角色分配菜单权限
     *
     * @Update chauncy
     *
     * @param  saveRolePermissionsDto
     * @return com.track.core.interaction.JsonViewData
     **/
    @PostMapping("/saveRolePermissions")
    @ApiOperation("添加/编辑角色分配菜单权限")
    public JsonViewData saveRolePermissions(@RequestBody @ApiParam(required = true,name = "saveRolePermissionsDto",value = "添加/编辑角色分配菜单权限")
                                            @Validated SaveRolePermissionsDto saveRolePermissionsDto){
        service.saveRolePermissions(saveRolePermissionsDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存角色分配的权限成功");
    }
    /**
     * @Author chauncy
     * @Date 2019-10-28 21:57
     * @Description //获取菜单权限
     *
     * @Update chauncy
     *
     * @param  roleId
     * @return com.track.core.interaction.JsonViewData<List<GetPermissionVo>>
     **/
    @GetMapping("/getPermission/{roleId}")
    @ApiOperation("获取角色对应的权限(页面+操作)")
    public JsonViewData<List<GetRolePermissionVo>> getPermission(@ApiParam(required = true,name = "roleId",value = "角色ID")
                                                             @PathVariable String roleId){

        return setJsonViewData(service.getPermission(roleId));
    }
    /**
     * @Author chauncy
     * @Date 2019-10-29 21:55
     * @Description //批量删除角色信息
     *
     * @Update chauncy
     *
     * @param  ids
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/delByIds/{ids}")
    @ApiOperation("批量删除角色信息")
    public JsonViewData delByIds(@PathVariable List<Long> ids){

        service.delByIds(ids);
        return setJsonViewData(ResultCode.SUCCESS,"删除成功!");
    }

}
