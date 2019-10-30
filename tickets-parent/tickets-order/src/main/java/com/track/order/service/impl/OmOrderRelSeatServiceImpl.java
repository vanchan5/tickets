package com.track.order.service.impl;

import com.track.data.domain.po.order.OmOrderRelSeatPo;
import com.track.data.mapper.order.OmOrderRelSeatMapper;
import com.track.order.service.IOmOrderRelSeatService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单关联座位表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmOrderRelSeatServiceImpl extends AbstractService<OmOrderRelSeatMapper,OmOrderRelSeatPo> implements IOmOrderRelSeatService {

 @Autowired
 private OmOrderRelSeatMapper mapper;

}
