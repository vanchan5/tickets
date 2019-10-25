package com.track.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.ticket.OmTicketPo;
import com.track.data.dto.base.EditEnabledDto;
import com.track.data.dto.manage.ticket.search.SearchTicketDto;
import com.track.data.mapper.ticket.OmTicketMapper;
import com.track.data.vo.manage.ticket.ManageTicketInfoVo;
import com.track.data.vo.manage.ticket.ManageTicketListVo;
import com.track.ticket.service.IOmTicketService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * <p>
 * 门票信息表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmTicketServiceImpl extends AbstractService<OmTicketMapper, OmTicketPo> implements IOmTicketService {

    @Autowired
    private OmTicketMapper mapper;

    /**
     * @Author yeJH
     * @Date 2019/10/25 11:30
     * @Description 查询演唱会门票列表
     *
     * @Update yeJH
     *
     * @param  searchTicketDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.ticket.ManageTicketListVo>
     **/
    @Override
    public PageInfo<ManageTicketListVo> searchTicketList(SearchTicketDto searchTicketDto) {

        Integer pageNo = searchTicketDto.getPageNo()==null ? defaultPageNo : searchTicketDto.getPageNo();
        Integer pageSize = searchTicketDto.getPageSize()==null ? defaultPageSize : searchTicketDto.getPageSize();

        PageInfo<ManageTicketListVo> manageTicketListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchTicketList(searchTicketDto));

        return manageTicketListVoPageInfo;
    }

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
    @Override
    public void publicState(EditEnabledDto editEnableDto) {
        UpdateWrapper<OmTicketPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().in(OmTicketPo::getId, Arrays.asList(editEnableDto.getIds()))
                .set(OmTicketPo::getPublishState, editEnableDto.getEnabled());

        this.update(updateWrapper);
    }

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
    @Override
    public ManageTicketInfoVo getTicket(Long ticketId) {

        OmTicketPo omTicketPo = mapper.selectById(ticketId);
        if(null == omTicketPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "门票记录不存在");
        }



        return null;

    }
}
