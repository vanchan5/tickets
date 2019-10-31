package com.track.ticket.service.impl;

import com.track.common.utils.TreeUtil;
import com.track.data.domain.po.ticket.AreaRegionPo;
import com.track.data.mapper.ticket.AreaRegionMapper;
import com.track.data.vo.manage.ticket.AreaCityVo;
import com.track.data.vo.manage.ticket.AreaVo;
import com.track.ticket.service.IAreaRegionService;
import com.track.core.base.service.AbstractService;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AreaRegionServiceImpl extends AbstractService<AreaRegionMapper, AreaRegionPo> implements IAreaRegionService {

    @Autowired
    private AreaRegionMapper mapper;

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:17
     * @Description //获取省市区
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.manage.ticket.AreaVo>
     **/
    @Override
    public List<AreaVo> searchList() {
        List<AreaVo> city = mapper.searchList();
        List<AreaVo> areaVoList = Lists.newArrayList();
        try {
            areaVoList = TreeUtil.getTree(city,"cityCode","parentCode","children");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaVoList;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:18
     * @Description //获取省市
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.manage.ticket.AreaCityVo>
     **/
    @Override
    public List<AreaCityVo> search() {
        List<AreaCityVo> city = mapper.search();
        List<AreaCityVo> areaCityVos = Lists.newArrayList();
        try {
            areaCityVos = TreeUtil.getTree(city,"cityCode","parentCode","children");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaCityVos;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-31 11:19
     * @Description //根据区县编号获取街道信息
     *
     * @Update chauncy
     *
     * @param  parentCode
     * @return java.util.List<com.track.data.vo.manage.ticket.AreaVo>
     **/
    @Override
    public List<AreaVo> findStreet(String parentCode) {

        return mapper.findStreet(parentCode);
    }
}
