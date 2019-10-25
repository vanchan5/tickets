package com.track.order.service.impl;

import com.track.data.domain.po.order.OmTicketPo;
import com.track.data.mapper.order.OmTicketMapper;
import com.track.order.service.IOmTicketService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
public class OmTicketServiceImpl extends AbstractService<OmTicketMapper,OmTicketPo> implements IOmTicketService {

 @Autowired
 private OmTicketMapper mapper;

}
