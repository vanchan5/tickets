package com.track.web.api.manage.ticket;


import com.track.ticket.service.IOmSceneRelGradeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

/**
 * <p>
 * 档次跟场次关联表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@RestController
@RequestMapping("om-scene-rel-grade-po")
@Api(tags = "档次跟场次关联表")
public class OmSceneRelGradeApi extends BaseWeb {

    @Autowired
    private IOmSceneRelGradeService service;


}
