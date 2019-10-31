package com.track.web.api.manage.ticket;


import com.track.core.interaction.JsonViewData;
import com.track.data.vo.manage.ticket.AreaCityVo;
import com.track.data.vo.manage.ticket.AreaVo;
import com.track.ticket.service.IAreaRegionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.track.web.base.BaseWeb;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Api(tags = "通用_省市区街道")
@RestController
@RequestMapping("/common/area")
@Slf4j
public class AreaRegionApi extends BaseWeb {

    @Autowired
    private IAreaRegionService service;

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:15
     * @Description //获取省市区
     *
     * @Update chauncy
     *
     * @param
     * @return com.track.core.interaction.JsonViewData<java.util.List<com.track.data.vo.manage.ticket.AreaVo>>
     **/
    @GetMapping("/searchList")
    @ApiOperation("获取省市区地址")
    public JsonViewData<List<AreaVo>> searchList(){

        return setJsonViewData(service.searchList());
    }

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:15
     * @Description //获取省市
     *
     * @Update chauncy
     *
     * @param
     * @return com.track.core.interaction.JsonViewData<java.util.List<com.track.data.vo.manage.ticket.AreaCityVo>>
     **/
    @GetMapping("/search")
    @ApiOperation("获取省市地址")
    public JsonViewData<List<AreaCityVo>> search(){

        return setJsonViewData(service.search());
    }

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:15
     * @Description //根据区县编号获取街道信息
     *
     * @Update chauncy
     *
     * @param  parentCode
     * @return com.track.core.interaction.JsonViewData<java.util.List<com.track.data.vo.manage.ticket.AreaVo>>
     **/
    @GetMapping("/findStreet/{parentCode}")
    @ApiOperation("根据区县编号获取街道信息")
    public JsonViewData<List<AreaVo>> findStreet(@ApiParam(required = true,name = "parentCode",value = "区县编号")
                                                 @PathVariable("parentCode") String parentCode){
        return setJsonViewData(service.findStreet(parentCode));
    }
}
