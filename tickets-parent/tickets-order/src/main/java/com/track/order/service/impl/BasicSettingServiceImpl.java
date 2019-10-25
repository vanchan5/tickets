package com.track.order.service.impl;

import com.track.data.domain.po.order.BasicSettingPo;
import com.track.data.mapper.order.BasicSettingMapper;
import com.track.order.service.IBasicSettingService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 平台基本设置 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BasicSettingServiceImpl extends AbstractService<BasicSettingMapper,BasicSettingPo> implements IBasicSettingService {

 @Autowired
 private BasicSettingMapper mapper;

}
