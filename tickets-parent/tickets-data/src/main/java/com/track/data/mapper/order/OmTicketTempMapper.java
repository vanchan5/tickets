package com.track.data.mapper.order;

import com.track.data.domain.po.order.OmTicketTempPo;
import com.track.data.dto.applet.order.OrderSubmitDto;
import com.track.data.mapper.base.IBaseMapper;

/**
 * <p>
 * 订单快照信息 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface OmTicketTempMapper extends IBaseMapper<OmTicketTempPo> {

    /**
     * @Author yeJH
     * @Date 2019/10/31 16:05
     * @Description 根据下单信息生成快照信息
     *
     * @Update yeJH
     *
     * @param  orderSubmitDto
     * @return com.track.data.domain.po.order.OmTicketTempPo
     **/
    OmTicketTempPo getTempByOrder(OrderSubmitDto orderSubmitDto);
}
