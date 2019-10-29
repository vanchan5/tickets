package com.track.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.data.vo.manage.order.ManageOrderListVo;
import com.track.order.service.IOmOrderService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmOrderServiceImpl extends AbstractService<OmOrderMapper, OmOrderPo> implements IOmOrderService {

    @Autowired
    private OmOrderMapper mapper;

    /**
     * @Author yeJH
     * @Date 2019/10/29 16:08
     * @Description 查询订单列表
     *
     * @Update yeJH
     *
     * @param  searchOrderDto  查询条件
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.ManageOrderListVo>
     **/
    @Override
    public PageInfo<ManageOrderListVo> searchOrderList(SearchOrderDto searchOrderDto) {

        Integer pageNo = searchOrderDto.getPageNo()==null ? defaultPageNo : searchOrderDto.getPageNo();
        Integer pageSize = searchOrderDto.getPageSize()==null ? defaultPageSize : searchOrderDto.getPageSize();

        PageInfo<ManageOrderListVo> manageOrderListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchOrderList(searchOrderDto));

        return manageOrderListVoPageInfo;
    }
}
