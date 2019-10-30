package com.track.web.api.manage.order;


import com.track.order.service.IOmFeedBackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 意见反馈表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("om-feed-back-po")
@Api(tags = "意见反馈表")
public class OmFeedBackApi extends BaseWeb {

    @Autowired
    private IOmFeedBackService service;


}
