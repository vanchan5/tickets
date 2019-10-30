package com.track.web.api.manage.ticket;


import com.track.ticket.service.IBasicSettingService;
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
@RestController
@RequestMapping("basic-setting-po")
@Api(tags = "平台基本设置")
public class BasicSettingApi extends BaseWeb {

    @Autowired
    private IBasicSettingService service;


}
