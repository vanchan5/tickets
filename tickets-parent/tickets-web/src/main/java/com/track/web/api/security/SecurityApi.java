package com.track.web.api.security;

import com.track.common.enums.system.ResultCode;
import com.track.common.utils.RedisUtil;
import com.track.core.interaction.JsonViewData;
import com.track.security.util.SecurityUtil;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-10-17 15:34
 */
@Slf4j
@RestController
@Api(tags = "Security相关接口")
@RequestMapping("/security")
@Transactional
public class SecurityApi extends BaseWeb {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "/needLogin")
    @ApiOperation(value = "没有登陆处理接口")
    public JsonViewData needLogin(){

        log.error("未登陆跳转的接口");
        return setJsonViewData(ResultCode.NO_LOGIN,"您还未登陆");
    }

    @GetMapping("/logout")
    @ApiOperation ("退出登录")
    public JsonViewData logout(){

        return setJsonViewData(ResultCode.SUCCESS);
    }

}
