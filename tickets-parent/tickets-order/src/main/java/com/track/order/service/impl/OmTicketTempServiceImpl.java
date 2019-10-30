package com.track.order.service.impl;

import com.track.data.domain.po.order.OmTicketTempPo;
import com.track.data.mapper.order.OmTicketTempMapper;
import com.track.order.service.IOmTicketTempService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单快照信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmTicketTempServiceImpl extends AbstractService<OmTicketTempMapper,OmTicketTempPo> implements IOmTicketTempService {

 @Autowired
 private OmTicketTempMapper mapper;

}
