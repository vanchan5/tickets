package com.track.web.api.manage.ticket;


import com.track.ticket.service.IOmTicketGradeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 门票档次信息表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("om-ticket-grade-po")
@Api(tags = "门票档次信息表")
public class OmTicketGradeApi extends BaseWeb {

    @Autowired
    private IOmTicketGradeService service;


}
