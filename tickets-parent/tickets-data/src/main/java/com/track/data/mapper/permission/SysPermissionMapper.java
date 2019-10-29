package com.track.data.mapper.permission;

import com.track.data.domain.po.permission.SysPermissionPo;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.permission.permission.GetAllPermissionsVo;
import com.track.data.vo.permission.permission.GetUserMenuListVo;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
public interface SysPermissionMapper extends IBaseMapper<SysPermissionPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-29 10:59
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
     * @Date 2019-10-29 17:52
     * @Description //获取用户页面菜单数据
     *
     * @Update chauncy
     *
     * @param  userId
     * @return java.util.List<com.track.data.vo.permission.permission.GetUserMenuListVo>
     **/
    List<GetUserMenuListVo> selectByUserId(Long userId);
}
