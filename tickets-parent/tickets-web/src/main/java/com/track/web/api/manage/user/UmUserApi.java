package com.track.web.api.manage.user;


import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.manage.user.SaveUserDto;
import com.track.user.service.IUmUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import com.track.web.base.BaseWeb;

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

    /**
     * @Author chauncy
     * @Date 2019-10-17 09:14
     * @Description //保存用户信息
     *
     * @Update chauncy
     *
     * @param  userDto
     * @return com.track.core.interaction.JsonViewData
     **/
    @PostMapping("/saveUser")
    @ApiOperation(value = "保存用户信息")
    public JsonViewData saveUser(@RequestBody @ApiParam(required = true,name = "SaveUserDto",value = "添加用户Dto")
                                @Validated SaveUserDto userDto){
        service.saveUser(userDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");

    }

}
