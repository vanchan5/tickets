package com.track.data.mapper.ticket;

import com.track.data.domain.po.ticket.OmTicketGradePo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.applet.ticket.TicketGradeBaseVo;

import java.util.List;

/**
 * <p>
 * 门票档次信息表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface OmTicketGradeMapper extends IBaseMapper<OmTicketGradePo> {

    /**
     * @Author yeJH
     * @Date 2019/10/28 23:44
     * @Description 获取门票下的所有档次id
     *
     * @Update yeJH
     *
     * @param  ticketId 门票id
     * @return java.util.List<java.lang.Long>
     **/
    List<Long> getGradeIdsByTicketId(Long ticketId);

    /**
     * @Author yeJH
     * @Date 2019/10/30 14:17
     * @Description 小程序根据门票id获取对应的档次信息
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return java.util.List<com.track.data.vo.applet.ticket.TicketGradeBaseVo>
     **/
    List<TicketGradeBaseVo> getTicketGradeBase(Long ticketId);
}
