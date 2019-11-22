package com.track.order.service;

import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.core.base.service.Service;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.applet.order.OrderSettlementDto;
import com.track.data.dto.applet.order.OrderSubmitDto;
import com.track.data.dto.applet.order.SearchMyOrderDto;
import com.track.data.dto.manage.order.search.OrderRefundDto;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.vo.applet.order.MyOrderDetailVo;
import com.track.data.vo.applet.order.MyOrderListVo;
import com.track.data.vo.applet.order.OrderSettlementVo;
import com.track.data.vo.manage.order.ManageOrderListVo;

import java.util.Map;

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
     * @Description 查询平台订单列表
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

    /**
     * @Author yeJH
     * @Date 2019/10/30 22:13
     * @Description 查询我的订单列表
     *
     * @Update yeJH
     *
     * @param  searchMyOrderDto  查询条件
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.applet.order.MyOrderListVo>
     **/
    PageInfo<MyOrderListVo> searchMyOrder(SearchMyOrderDto searchMyOrderDto);

    /**
     * @Author yeJH
     * @Date 2019/10/31 10:00
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
     * @Date 2019/10/31 12:04
     * @Description 订单下单提交
     *
     * @Update yeJH
     *
     * @param  orderSubmitDto 档次 场次  取票人  手机尾号  购票数量
     * @param  umUserPo 下单用户
     * @return java.lang.Long   订单id
     **/
    Long submit(OrderSubmitDto orderSubmitDto, UmUserPo umUserPo);

    /**
     * @Author yeJH
     * @Date 2019/11/2 11:28
     * @Description 删除用户订单
     *
     * @Update yeJH
     *
     * @param  orderId
     * @param  umUserPo
     * @return void
     **/
    void deleteMyOrder(Long orderId, UmUserPo umUserPo);

    /**
     * @Author yeJH
     * @Date 2019/11/2 11:28
     * @Description 用户取消待支付订单
     *
     * @Update yeJH
     *
     * @param  orderId
     * @param  umUserPo
     * @return void
     **/
    void cancelMyOrder(Long orderId, UmUserPo umUserPo);

    /**
     * @Author yeJH
     * @Date 2019/11/2 17:39
     * @Description 用户下单 在规定时间内没有完成支付，关闭订单
     *
     * @Update yeJH
     *
     * @param  orderId
     * @return void
     **/
    void closeOrder(Long orderId);

    /**
     * @Author yeJH
     * @Date 2019/11/20 11:05
     * @Description 支付回调通知 修改订单 安排座位
     *
     * @Update yeJH
     *
     * @param  omOrderPo
     * @param  response
     * @return void
     **/
    void wxPayNotify(OmOrderPo omOrderPo, Map<String, String> response);

    /**
     * @Author yeJH
     * @Date 2019/11/20 21:30
     * @Description 根据订单号，单个或者批量退款，也可根据场次将所有订单退款
     *
     * @Update yeJH
     *
     * @param  orderRefundDto
     * @return void
     **/
    void orderRefund(OrderRefundDto orderRefundDto);

    /**
     * @Author yeJH
     * @Date 2019/11/21 22:10
     * @Description 申请退款之后15天完成退款操作
     *
     * @Update yeJH
     *
     * @param  orderRefundDto
     * @return void
     **/
    void achieveRefund(OrderRefundDto orderRefundDto);
}
