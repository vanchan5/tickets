package com.track.web.api.manage.order;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 订单快照信息 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("om-ticket-temp-po")
@Api(tags = "订单快照信息")
public class OmTicketTempApi extends BaseWeb {

 @Autowired
 private IOmTicketTempService service;


}
