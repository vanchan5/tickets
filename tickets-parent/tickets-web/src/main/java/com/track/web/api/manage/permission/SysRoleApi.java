package com.track.web.api.manage.permission;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.manage.permission.save.SaveRoleDto;
import com.track.data.dto.manage.permission.search.SearchRoleDto;
import com.track.data.vo.permission.role.SearchRoleVo;
import com.track.permission.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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

}
