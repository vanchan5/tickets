package com.track.web.api.manage.order;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 门票信息表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("om-ticket-po")
@Api(tags = "门票信息表")
public class OmTicketApi extends BaseWeb {

 @Autowired
 private IOmTicketService service;


}
