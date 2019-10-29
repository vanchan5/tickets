package com.track.data.mapper.order;

import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.mapper.base.IBaseMapper;

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
}
