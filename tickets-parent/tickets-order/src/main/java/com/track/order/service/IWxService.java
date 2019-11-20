package com.track.order.service;

import com.track.data.domain.po.user.UmUserPo;
import com.track.data.vo.applet.order.UnifiedOrderVo;

/**
 * @author yeJH
 * @since 2019/7/5 16:22
 */
public interface IWxService {

    /**
     * 调用官方SDK 获取前端调起支付接口的参数
     * @return
     * @throws Exception
     */
    UnifiedOrderVo unifiedOrder(Long orderId, String ipAddr, UmUserPo umUsrePo) throws Exception;
    /**
     * 微信支付结果通知
     * @param notifyData 异步通知后的XML数据
     * @return
     */
    String payBack(String notifyData) throws Exception;
    /**
     *   微信查询订单接口  订单未操作的做业务更新
     *   官方文档 ：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_2&index=4
     */
    void orderQuery(Long payOrderId) throws Exception;
    /**
     * @Author yeJH
     * @Date 2019/10/29 10:24
     * @Description 调用官方SDK申请退款
     *
     * @Update yeJH
     *
     * @param  orderId 订单id
     * @return String
     **/
    String refund(Long orderId) ;
}
