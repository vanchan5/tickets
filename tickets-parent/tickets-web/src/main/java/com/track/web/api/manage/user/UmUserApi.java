package com.track.web.api.manage.user;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.base.EditEnabledDto;
import com.track.data.dto.manage.user.edit.EditPasswordDto;
import com.track.data.dto.manage.user.save.SaveUserDto;
import com.track.data.dto.manage.user.search.SearchAppletUsersDto;
import com.track.data.dto.manage.user.search.SearchUsersDto;
import com.track.data.vo.user.SearchAppletUsersVo;
import com.track.data.vo.user.SearchUsersVo;
import com.track.security.util.SecurityUtil;
import com.track.user.service.IUmUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import com.track.web.base.BaseWeb;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-16
 */
@RestController
@RequestMapping("/manage/user")
@Api(tags = "后台_用户管理")
public class UmUserApi extends BaseWeb {

    @Autowired
    private IUmUserService service;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @param userDto
     * @return com.track.core.interaction.JsonViewData
     * @Author chauncy
     * @Date 2019-10-17 09:14
     * @Description //保存用户信息
     * @Update chauncy
     **/
    @PostMapping("/saveUser")
    @ApiOperation(value = "保存用户信息")
    public JsonViewData saveUser(@RequestBody @ApiParam(required = true, name = "saveUserDto", value = "添加用户Dto")
                                 @Validated SaveUserDto userDto) {
        service.saveUser(userDto);
        return setJsonViewData(ResultCode.SUCCESS, "保存成功");

    }

    /**
     * @param searchUsersDto
     * @return com.track.core.interaction.JsonViewData<com.track.data.vo.user.SearchUsersVo>
     * @Author chauncy
     * @Date 2019-10-23 15:38
     * @Description //条件分页查询用户信息
     * @Update chauncy
     **/
    @PostMapping("/searchUsers")
    @ApiOperation(value = "条件分页查询用户信息")
    public JsonViewData<PageInfo<SearchUsersVo>> searchUsers(@RequestBody @ApiParam(required = true, name = "serachUsersVo", value = "分页获取用户信息")
                                                             @Validated SearchUsersDto searchUsersDto) {

        return setJsonViewData(service.searchUsers(searchUsersDto));
    }

    /**
     * @param editEnabledDto
     * @return com.track.core.interaction.JsonViewData
     * @Author chauncy
     * @Date 2019-10-23 23:41
     * @Description //启用/禁用用户
     * @Update chauncy
     **/
    @PostMapping("/editEnabledUser")
    @ApiOperation("启用/禁用用户")
    public JsonViewData editEnabledUser(@RequestBody @ApiParam(required = true, name = "editEnabledDto", value = "启用/禁用用户")
                                        @Validated EditEnabledDto editEnabledDto) {

        service.editEnabledBatch(editEnabledDto);
        return setJsonViewData(ResultCode.SUCCESS, "操作成功");
    }

    /**
     * @param ids
     * @return com.track.core.interaction.JsonViewData
     * @Author chauncy
     * @Date 2019-10-23 23:46
     * @Description //批量删除用户信息
     * @Update chauncy
     **/
    @GetMapping("/delUsersByIds/{ids}")
    @ApiOperation("批量删除用户信息")
    public JsonViewData delUsersByIds(@PathVariable List<Long> ids) {

        service.delUsersByIds(ids);
        return setJsonViewData(ResultCode.SUCCESS, "删除成功!");
    }

    /**
     * @param editPasswordDto
     * @return com.track.core.interaction.JsonViewData
     * @Author chauncy
     * @Date 2019-10-25 17:34
     * @Description //修改密码
     * @Update chauncy
     **/
    @PostMapping("/editPassword")
    @ApiOperation("修改密码")
    public JsonViewData editPassword(@RequestBody @ApiParam(required = true, name = "editPasswordDto", value = "修改用户密码")
                                     @Validated EditPasswordDto editPasswordDto) {
        service.editPassword(editPasswordDto);
        return setJsonViewData(ResultCode.SUCCESS, "修改密码成功!");
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public JsonViewData<UmUserPo> getUserInfo() {

        UmUserPo urrUser =securityUtil.getSysCurrUser();
        return setJsonViewData(urrUser);
    }

    /**
     * @Author chauncy
     * @Date 2019-11-12 19:58
     * @Description //条件分页查询applet用户信息
     *
     * @Update chauncy
     *
     * @param  searchAppletUsersDto
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<com.track.data.vo.user.SearchAppletUsersVo>>
     **/
    @PostMapping("/searchAppletUsers")
    @ApiOperation("条件分页查询applet用户信息")
    public JsonViewData<PageInfo<SearchAppletUsersVo>> searchAppletUsers(@RequestBody @ApiParam(required = true,
            name = "searchAppletUsersDto",value = "条件分页查询applet用户信息") @Validated SearchAppletUsersDto searchAppletUsersDto){

        return setJsonViewData(service.searchAppletUsers(searchAppletUsersDto));
    }






























}
