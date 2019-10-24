package com.track.permission;

import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.data.domain.po.permission.SysRolePo;
import com.track.core.base.service.Service;
import com.track.data.dto.manage.permission.save.SaveRoleDto;
import com.track.data.dto.manage.permission.search.SearchRoleDto;
import com.track.data.vo.permission.role.SearchRoleVo;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
public interface ISysRoleService extends Service<SysRolePo> {

    /**
     * @Author chauncy
     * @Date 2019-10-22 21:50
     * @Description //保存角色信息
     *
     * @Update chauncy
     *
     * @param  saveRoleDto
     * @return void
     **/
    void saveRole(SaveRoleDto saveRoleDto);

    /**
     * @Author chauncy
     * @Date 2019-10-23 14:03
     * @Description //分页查询角色信息
     *
     * @Update chauncy
     *
     * @param  searchRoleDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.permission.role.SearchRoleVo>
     **/
    PageInfo<SearchRoleVo> searchRole(SearchRoleDto searchRoleDto);
}
