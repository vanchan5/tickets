package com.track.data.mapper.order;

import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.mapper.base.IBaseMapper;
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
     * @Description 查询订单列表
     *
     * @Update yeJH
     *
     * @param  searchOrderDto  查询条件
     * @return java.util.List<com.track.data.vo.manage.order.ManageOrderListVo>
     **/
    List<ManageOrderListVo> searchOrderList(SearchOrderDto searchOrderDto);
}
