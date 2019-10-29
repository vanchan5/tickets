package com.track.ticket.service;

import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.ticket.OmTicketPo;
import com.track.core.base.service.Service;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.applet.ticket.SearchTicketDto;
import com.track.data.dto.base.EditEnabledDto;
import com.track.data.dto.manage.ticket.save.SaveTicketDto;
import com.track.data.dto.manage.ticket.search.SearchManageTicketDto;
import com.track.data.vo.applet.ticket.TicketListVo;
import com.track.data.vo.manage.ticket.ManageTicketInfoVo;
import com.track.data.vo.manage.ticket.ManageTicketListVo;

/**
 * <p>
 * 门票信息表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface IOmTicketService extends Service<OmTicketPo> {

    /**
     * @Author yeJH
     * @Date 2019/10/25 11:29
     * @Description 平台查询演唱会门票列表
     *
     * @Update yeJH
     *
     * @param  searchManageTicketDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.ticket.ManageTicketListVo>
     **/
    PageInfo<ManageTicketListVo> searchManageTicketList(SearchManageTicketDto searchManageTicketDto);

    /**
     * @Author yeJH
     * @Date 2019/10/25 12:39
     * @Description 演唱会门票上下架
     *
     * @Update yeJH
     *
     * @param  editEnableDto
     * @return void
     **/
    void publicState(EditEnabledDto editEnableDto);

    /**
     * @Author yeJH
     * @Date 2019/10/25 16:18
     * @Description 编辑时根据门票id获取门票详情
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return com.track.data.vo.manage.ticket.ManageTicketInfoVo
     **/
    ManageTicketInfoVo getTicket(Long ticketId);

    /**
     * @Author yeJH
     * @Date 2019/10/28 18:07
     * @Description 新增/编辑演唱会门票
     *
     * @Update yeJH
     *
     * @param  saveTicketDto  新增/编辑演唱会门票参数
     * @param  operator  操作用户
     * @return void
     **/
    void saveTicket(SaveTicketDto saveTicketDto, UmUserPo operator);

    /**
     * @Author yeJH
     * @Date 2019/10/29 20:42
     * @Description 小程序条件查询门票列表
     *
     * @Update yeJH
     *
     * @param  searchTicketDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.applet.ticket.TicketListVo>
     **/
    PageInfo<TicketListVo> searchTicketList(SearchTicketDto searchTicketDto);
}
