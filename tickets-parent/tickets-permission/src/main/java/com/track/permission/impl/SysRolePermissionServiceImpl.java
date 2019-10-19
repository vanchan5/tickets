package com.track.permission.impl;

import com.track.data.domain.po.permission.SysRolePermissionPo;
import com.track.data.mapper.permission.SysRolePermissionMapper;
import com.track.permission.ISysRolePermissionService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRolePermissionServiceImpl extends AbstractService<SysRolePermissionMapper, SysRolePermissionPo> implements ISysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper mapper;

}
