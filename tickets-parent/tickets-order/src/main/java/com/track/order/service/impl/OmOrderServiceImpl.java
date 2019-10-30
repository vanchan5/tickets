package com.track.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.BigDecimalUtil;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.dto.applet.order.OrderSettlementDto;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.data.vo.applet.order.OrderSettlementVo;
import com.track.data.vo.manage.order.ManageOrderListVo;
import com.track.order.service.IOmOrderService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

    /**
     * @Author yeJH
     * @Date 2019/10/30 17:52
     * @Description 订单结算
     *
     *
     * @Update yeJH
     *
     * @param  orderSettlementDto 选择门票场次，档次，添加数量去结算
     * @return com.track.data.vo.applet.order.OrderSettlementVo
     **/
    @Override
    public OrderSettlementVo settlement(OrderSettlementDto orderSettlementDto) {

        //结算信息
        OrderSettlementVo orderSettlementVo = mapper.settlement(orderSettlementDto);
        if(null == orderSettlementVo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "不存在该数据");
        }
        //购买票数
        orderSettlementVo.setOrderNum(orderSettlementDto.getOrderNum());
        //总计金额  购买票数 * 单价
        BigDecimal payAmount = BigDecimalUtil.safeMultiply(orderSettlementDto.getOrderNum(),
                orderSettlementVo.getSellPrice());

        return orderSettlementVo;
    }
}
