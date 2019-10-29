package com.track.data.mapper.ticket;

import com.track.data.domain.po.ticket.OmTicketPo;
import com.track.data.dto.manage.ticket.search.SearchTicketDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.manage.ticket.ManageTicketInfoVo;
import com.track.data.vo.manage.ticket.ManageTicketListVo;
import com.track.data.vo.manage.ticket.TicketGradeInfoVo;

import java.util.List;

/**
 * <p>
 * 门票信息表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface OmTicketMapper extends IBaseMapper<OmTicketPo> {

    /**
     * @Author yeJH
     * @Date 2019/10/25 11:33
     * @Description 查询演唱会门票列表
     *
     * @Update yeJH
     *
     * @param  searchTicketDto
     * @return List<ManageTicketListVo>
     **/
    List<ManageTicketListVo> searchTicketList(SearchTicketDto searchTicketDto);

    /**
     * @Author yeJH
     * @Date 2019/10/26 21:47
     * @Description 根据门票id获取门票详情
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return com.track.data.vo.manage.ticket.ManageTicketInfoVo
     **/
    ManageTicketInfoVo getTicketDetail(Long ticketId);

    /**
     * @Author yeJH
     * @Date 2019/10/28 13:15
     * @Description 根据门票id获取档次，座位区信息
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return java.util.List<com.track.data.vo.manage.ticket.TicketGradeInfoVo>
     **/
    List<TicketGradeInfoVo> getTicketGradeInfo(Long ticketId);
}
