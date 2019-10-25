package com.track.ticket.service.impl;

import com.track.data.domain.po.ticket.OmTicketSeatPo;
import com.track.data.mapper.ticket.OmTicketSeatMapper;
import com.track.ticket.service.IOmTicketSeatService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 门票座位信息表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmTicketSeatServiceImpl extends AbstractService<OmTicketSeatMapper,OmTicketSeatPo> implements IOmTicketSeatService {

 @Autowired
 private OmTicketSeatMapper mapper;

}
