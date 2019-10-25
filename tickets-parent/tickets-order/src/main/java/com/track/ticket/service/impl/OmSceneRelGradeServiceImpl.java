package com.track.ticket.service.impl;

import com.track.data.domain.po.ticket.OmSceneRelGradePo;
import com.track.data.mapper.ticket.OmSceneRelGradeMapper;
import com.track.ticket.service.IOmSceneRelGradeService;
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
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmSceneRelGradeServiceImpl extends AbstractService<OmSceneRelGradeMapper,OmSceneRelGradePo> implements IOmSceneRelGradeService {

 @Autowired
 private OmSceneRelGradeMapper mapper;

}
