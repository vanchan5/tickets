package com.track.order.service;

import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.core.base.service.Service;
import com.track.data.dto.applet.order.OrderSettlementDto;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.vo.applet.order.OrderSettlementVo;
import com.track.data.vo.manage.order.ManageOrderListVo;

/**
 * <p>
 * 支付订单 服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface IOmOrderService extends Service<OmOrderPo> {

    /**
     * @Author yeJH
     * @Date 2019/10/29 16:05
     * @Description 查询订单列表
     *
     * @Update yeJH
     *
     * @param  searchOrderDto  查询条件
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.ManageOrderListVo>
     **/
    PageInfo<ManageOrderListVo> searchOrderList(SearchOrderDto searchOrderDto);

    /**
     * @Author yeJH
     * @Date 2019/10/30 17:51
     * @Description 订单结算
     *              选择门票场次，档次，添加数量去结算
     *
     * @Update yeJH
     *
     * @param  orderSettlementDto
     * @return com.track.data.vo.applet.order.OrderSettlementVo
     **/
    OrderSettlementVo settlement(OrderSettlementDto orderSettlementDto);
}
