package com.track.web.api.manage.order;


import com.track.order.service.IOmOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 支付订单 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("om-order-po")
@Api(tags = "支付订单")
public class OmOrderApi extends BaseWeb {

    @Autowired
    private IOmOrderService service;


}
