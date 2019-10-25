package com.track.web.api.manage.order;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("area-region-po")
@Api(tags = "")
public class AreaRegionApi extends BaseWeb {

    @Autowired
    private IAreaRegionService service;


}
