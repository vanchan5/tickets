package com.track.data.mapper.ticket;

import com.track.data.domain.po.ticket.OmTicketPo;
import com.track.data.dto.applet.ticket.SearchTicketDto;
import com.track.data.dto.manage.ticket.search.SearchManageTicketDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.applet.ticket.TicketDetailVo;
import com.track.data.vo.applet.ticket.TicketListVo;
import com.track.data.vo.manage.ticket.ManageTicketInfoVo;
import com.track.data.vo.manage.ticket.ManageTicketListVo;
import com.track.data.vo.manage.ticket.TicketGradeInfoVo;
import org.apache.ibatis.annotations.Param;

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
     * @param  searchManageTicketDto
     * @return List<ManageTicketListVo>
     **/
    List<ManageTicketListVo> searchManageTicketList(SearchManageTicketDto searchManageTicketDto);

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
    ManageTicketInfoVo getTicketInfo(Long ticketId);

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

    /**
     * @Author yeJH
     * @Date 2019/10/29 20:45
     * @Description 小程序条件查询门票列表
     *
     * @Update yeJH
     *
     * @param  searchTicketDto
     * @return java.util.List<com.track.data.vo.applet.ticket.TicketListVo>
     **/
    List<TicketListVo> searchTicketList(SearchTicketDto searchTicketDto);

    /**
     * @Author yeJH
     * @Date 2019/10/30 10:54
     * @Description 小程序根据门票id获取演出详情
     *
     * @Update yeJH
     *
     * @param  ticketId 门票id
     * @return com.track.data.vo.applet.ticket.TicketDetailVo
     **/
    TicketDetailVo getTicketDetail(@Param("ticketId") Long ticketId);
}
