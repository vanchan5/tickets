package com.track.permission;

import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.data.domain.po.permission.SysPermissionPo;
import com.track.core.base.service.Service;
import com.track.data.dto.manage.permission.save.SavePermissionDto;
import com.track.data.vo.permission.permission.GetAllPermissionsVo;
import com.track.data.vo.permission.permission.GetUserMenuListVo;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
public interface ISysPermissionService extends Service<SysPermissionPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-28 23:36
     * @Description //保存权限
     *
     * @Update chauncy
     *
     * @param  savePermissionDto
     * @return void
     **/
    void savePermission(SavePermissionDto savePermissionDto);

    /**
     * @Author chauncy
     * @Date 2019-10-29 10:53
     * @Description //获取全部权限列表
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.permission.permission.GetAllPermissionsVo>
     **/
    List<GetAllPermissionsVo> getAllPermissions();

    /**
     * @Author chauncy
     * @Date 2019-10-29 12:16
     * @Description //获取用户页面菜单数据
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.track.data.vo.permission.permission.GetUserMenuListVo>
     **/
    List<GetUserMenuListVo> getUserMenuList();

    /**
     * @Author chauncy
     * @Date 2019-10-29 21:29
     * @Description //批量删除菜单权限
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    void delByIds(List<Long> ids);
}
