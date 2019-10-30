package com.track.data.mapper.ticket;

import com.track.data.domain.po.ticket.OmTicketScenePo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.applet.ticket.TicketSceneBaseVo;

import java.util.List;

/**
 * <p>
 * 门票场次信息表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface OmTicketSceneMapper extends IBaseMapper<OmTicketScenePo> {

    /**
     * @Author yeJH
     * @Date 2019/10/30 13:56
     * @Description 小程序根据门票id获取对应的场次信息
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return java.util.List<com.track.data.vo.applet.ticket.TicketSceneBaseVo>
     **/
    List<TicketSceneBaseVo> getTicketSceneBase(Long ticketId);
}
