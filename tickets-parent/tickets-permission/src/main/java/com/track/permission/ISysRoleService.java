package com.track.permission;

import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.permission.SysRolePo;
import com.track.core.base.service.Service;
import com.track.data.dto.manage.permission.save.EditDefaultRoleDto;
import com.track.data.dto.manage.permission.save.SaveRoleDto;
import com.track.data.dto.manage.permission.save.SaveRolePermissionsDto;
import com.track.data.vo.permission.role.GetRolePermissionVo;
import com.track.data.dto.manage.permission.search.SearchRoleDto;
import com.track.data.vo.permission.role.SearchRoleVo;

import java.util.List;

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

    /**
     * @Author chauncy
     * @Date 2019-10-28 21:47
     * @Description //设置默认角色
     *
     * @Update chauncy
     *
     * @param  editDefaultRoleDto
     * @return void
     **/
    void setDefaultRole(EditDefaultRoleDto editDefaultRoleDto);

    /**
     * @Author chauncy
     * @Date 2019-10-28 22:00
     * @Description //获取菜单权限
     *
     * @Update chauncy
     *
     * @param  roleId
     * @return java.util.List<com.track.data.vo.permission.role.GetRolePermissionVo>
     **/
    List<GetRolePermissionVo> getPermission(String roleId);

    /**
     * @Author chauncy
     * @Date 2019-10-29 12:27
     * @Description //添加/编辑角色分配菜单权限
     *
     * @Update chauncy
     *
     * @param  saveRolePermissionsDto
     * @return void
     **/
    void saveRolePermissions(SaveRolePermissionsDto saveRolePermissionsDto);

    /*
     * @Author chauncy
     * @Date 2019-10-29 21:56
     * @Description //批量删除角色信息
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    void delByIds(List<Long> ids);
}
