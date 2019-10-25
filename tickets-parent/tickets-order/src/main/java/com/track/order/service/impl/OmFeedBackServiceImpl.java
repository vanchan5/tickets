package com.track.order.service.impl;

import com.track.data.domain.po.order.OmFeedBackPo;
import com.track.data.mapper.order.OmFeedBackMapper;
import com.track.order.service.IOmFeedBackService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 意见反馈表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmFeedBackServiceImpl extends AbstractService<OmFeedBackMapper,OmFeedBackPo> implements IOmFeedBackService {

 @Autowired
 private OmFeedBackMapper mapper;

}
