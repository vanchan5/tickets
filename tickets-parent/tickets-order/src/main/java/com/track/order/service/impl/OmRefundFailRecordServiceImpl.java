package com.track.order.service.impl;

import com.track.data.domain.po.order.OmRefundFailRecordPo;
import com.track.data.mapper.order.OmRefundFailRecordMapper;
import com.track.order.service.IOmRefundFailRecordService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单退款失败记录 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmRefundFailRecordServiceImpl extends AbstractService<OmRefundFailRecordMapper,OmRefundFailRecordPo> implements IOmRefundFailRecordService {

 @Autowired
 private OmRefundFailRecordMapper mapper;

}
