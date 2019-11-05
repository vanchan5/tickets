package com.track.ticket.service.impl;

import com.track.data.domain.po.ticket.OmSceneGradeRelSeatPo;
import com.track.data.mapper.ticket.OmSceneGradeRelSeatMapper;
import com.track.ticket.service.IOmSceneGradeRelSeatService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 档次跟场次关联表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmSceneGradeRelSeatServiceImpl extends AbstractService<OmSceneGradeRelSeatMapper,OmSceneGradeRelSeatPo> implements IOmSceneGradeRelSeatService {

 @Autowired
 private OmSceneGradeRelSeatMapper mapper;

}
