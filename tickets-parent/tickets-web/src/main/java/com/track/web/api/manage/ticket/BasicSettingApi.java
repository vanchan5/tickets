package com.track.web.api.manage.ticket;


import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.ticket.service.IBasicSettingService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 平台基本设置 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Api(tags = "客服管理")
@RestController
@RequestMapping("/customer/service")
@Slf4j
public class BasicSettingApi extends BaseWeb {

    @Autowired
    private IBasicSettingService service;

    /**
     * @Author chauncy
     * @Date 2019-10-31 10:51
     * @Description //保存客服电话
     *
     * @Update chauncy
     *
     * @param  phone
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/saveCustomerService/{phone}")
    @ApiOperation(value = "保存客服电话")
    public JsonViewData saveCustomerService(@PathVariable String phone){

        service.saveCustomerService(phone);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * @Author chauncy
     * @Date 2019-10-31 10:58
     * @Description //获取客服电话
     *
     * @Update chauncy
     *
     * @param
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/getCustomerService")
    @ApiOperation("获取客服电话")
    public JsonViewData<String> getCustomerService(){

        return setJsonViewData(service.getCustomerService());
    }

}
