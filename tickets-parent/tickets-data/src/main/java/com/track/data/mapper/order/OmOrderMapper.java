package com.track.data.mapper.order;

import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.dto.applet.order.OrderSettlementDto;
import com.track.data.dto.applet.order.SearchMyOrderDto;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.applet.order.MyOrderDetailVo;
import com.track.data.vo.applet.order.MyOrderListVo;
import com.track.data.vo.applet.order.OrderSettlementVo;
import com.track.data.vo.manage.order.ManageOrderListVo;

import java.util.List;

/**
 * <p>
 * 支付订单 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface OmOrderMapper extends IBaseMapper<OmOrderPo> {

    /**
     * @Author yeJH
     * @Date 2019/10/28 22:39
     * @Description 判断演唱会门票是否已经有人下单了
     *
     * @Update yeJH
     *
     * @param  ticketId 门票id
     * @return boolean
     **/
    boolean isOrderByTicketId(Long ticketId);

    /**
     * @Author yeJH
     * @Date 2019/10/29 16:07
     * @Description 查询平台订单列表
     *
     * @Update yeJH
     *
     * @param  searchOrderDto  查询条件
     * @return java.util.List<com.track.data.vo.manage.order.ManageOrderListVo>
     **/
    List<ManageOrderListVo> searchOrderList(SearchOrderDto searchOrderDto);

    /**
     * @Author yeJH
     * @Date 2019/10/30 18:17
     * @Description 订单结算
     *
     * @Update yeJH
     *
     * @param  orderSettlementDto 选择门票场次，档次，添加数量去结算
     * @return com.track.data.vo.applet.order.OrderSettlementVo
     **/
    OrderSettlementVo settlement(OrderSettlementDto orderSettlementDto);

    /**
     * @Author yeJH
     * @Date 2019/10/30 22:39
     * @Description 查询我的订单列表
     *
     * @Update yeJH
     *
     * @param  searchMyOrderDto  查询条件
     * @return java.util.List<MyOrderListVo>
     **/
    List<MyOrderListVo> searchMyOrder(SearchMyOrderDto searchMyOrderDto);

    /**
     * @Author yeJH
     * @Date 2019/10/31 10:18
     * @Description 根据订单id获取订单详情
     *
     * @Update yeJH
     *
     * @param  orderId 订单id
     * @return com.track.data.vo.applet.order.MyOrderDetailVo
     **/
    MyOrderDetailVo getOrderDetail(Long orderId);

    /**
     * @Author yeJH
     * @Date 2019/11/3 21:02
     * @Description 根据场次id获取 用户下单该场次的订单并且状态全部置为已消费
     *
     * @Update yeJH
     *
     * @param  omTicketSceneId
     * @return void
     **/
    void updateOrderByScene(Long omTicketSceneId);
}
