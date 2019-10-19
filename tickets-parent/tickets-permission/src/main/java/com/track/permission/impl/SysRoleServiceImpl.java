package com.track.permission.impl;

import com.track.data.domain.po.permission.SysRolePo;
import com.track.data.mapper.permission.SysRoleMapper;
import com.track.permission.ISysRoleService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRoleServiceImpl extends AbstractService<SysRoleMapper, SysRolePo> implements ISysRoleService {

    @Autowired
    private SysRoleMapper mapper;

}
