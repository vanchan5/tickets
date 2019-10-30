package com.track.web.api.manage.order;


import com.track.order.service.IOmOrderRelSeatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 订单关联座位表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("om-order-rel-seat-po")
@Api(tags = "订单关联座位表")
public class OmOrderRelSeatApi extends BaseWeb {

    @Autowired
    private IOmOrderRelSeatService service;


}
