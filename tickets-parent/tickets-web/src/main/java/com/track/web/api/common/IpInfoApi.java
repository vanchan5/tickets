package com.track.web.api.common;

import com.track.common.utils.IpInfoUtil;
import com.track.core.interaction.JsonViewData;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cheng
 * @create 2019-10-25 22:40
 *
 * IP及天气相关信息
 */
@Slf4j
@RestController
@Api(tags = "通用_IP及天气相关信息")
@RequestMapping("/common/ip")
public class IpInfoApi extends BaseWeb {

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @GetMapping("/info")
    @ApiOperation("IP及天气相关信息")
    public JsonViewData<String> getIpInfo(HttpServletRequest request){

        String result = ipInfoUtil.getIpWeatherInfo(ipInfoUtil.getIpAddr(request));
        return setJsonViewData(result);
    }
}
