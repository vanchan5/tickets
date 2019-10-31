package com.track.ticket.service;

import com.track.data.domain.po.ticket.AreaRegionPo;
import com.track.core.base.service.Service;
import com.track.data.vo.manage.ticket.AreaCityVo;
import com.track.data.vo.manage.ticket.AreaVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface IAreaRegionService extends Service<AreaRegionPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:16
     * @Description //获取省市区
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.manage.ticket.AreaVo>
     **/
    List<AreaVo> searchList();

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:16
     * @Description //获取省市
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.manage.ticket.AreaCityVo>
     **/
    List<AreaCityVo> search();

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:16
     * @Description //根据区县编号获取街道信息
     *
     * @Update chauncy
     *
     * @param  parentCode
     * @return java.util.List<com.track.data.vo.manage.ticket.AreaVo>
     **/
    List<AreaVo> findStreet(String parentCode);

}
