package com.track.permission.impl;

import com.track.data.domain.po.permission.SysPermissionPo;
import com.track.data.mapper.permission.SysPermissionMapper;
import com.track.permission.ISysPermissionService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysPermissionServiceImpl extends AbstractService<SysPermissionMapper, SysPermissionPo> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper mapper;

}