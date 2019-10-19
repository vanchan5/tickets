package com.track.permission.impl;

import com.track.data.domain.po.permission.SysRoleUserPo;
import com.track.data.mapper.permission.SysRoleUserMapper;
import com.track.permission.ISysRoleUserService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户与角色关系表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRoleUserServiceImpl extends AbstractService<SysRoleUserMapper, SysRoleUserPo> implements ISysRoleUserService {

    @Autowired
    private SysRoleUserMapper mapper;

}
