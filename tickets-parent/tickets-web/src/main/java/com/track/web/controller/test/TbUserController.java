package com.track.web.controller.test;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.test.save.SaveUsersDto;
import com.track.data.dto.test.select.SearchUsersDto;
import com.track.data.vo.test.SearchUsersVo;
import com.track.test.user.ITbUserService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-09-01
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试模块")
public class TbUserController extends BaseWeb {

    @Autowired
    private ITbUserService service;

    /**
     * 测试获取数据
     *
     * @param username
     * @return
     */
    @GetMapping("/findByUserName")
    @ApiOperation("框架相关测试")
    public JsonViewData<Map<String, Object>> findByUserName(String username) {

        //测试baseService使用
        Map<String, Object> result = service.findByUserName(username);

        //测试业务模块使用
        service.test(username);

        return setJsonViewData(result);
    }

    /**
     * 测试分页查询、数据校验(数据库是否存在、枚举校验)
     *
     * @param searchUsersDto
     * @return
     */
    @PostMapping("/searchUsers")
    @ApiOperation("分页条件查询用户列表")
    public JsonViewData<PageInfo<SearchUsersVo>> searchUsers(@RequestBody @ApiParam(required =true,name = "searchUsersDto", value = "分页条件查询用户列表")
                                                                 @Validated SearchUsersDto searchUsersDto){

        return setJsonViewData(service.searchUsers(searchUsersDto));
    }

    /**
     * 保存用户--测试事务和异常处理
     *
     * @param saveUsersDto
     * @return
     */
    @PostMapping("/saveUser")
    @ApiOperation("保存用户--测试事务和异常处理")
    public JsonViewData saveUser(@RequestBody @ApiParam(required = true,name = "",value = "")
                                 @Validated SaveUsersDto saveUsersDto){

        service.saveUsers(saveUsersDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }

}
