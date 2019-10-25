package com.track.order.service.impl;

import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.order.service.IOmOrderService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmOrderServiceImpl extends AbstractService<OmOrderMapper,OmOrderPo> implements IOmOrderService {

 @Autowired
 private OmOrderMapper mapper;

}
